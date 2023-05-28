package service;

import com.mysql.cj.conf.ConnectionUrlParser;
import model.chirias.Antecedent;
import model.chirias.Chirias;
import model.chirias.Contract;
import model.cladire.*;
import model.factura.*;
import model.persoana.Angajat;
import model.persoana.Persoana;
import model.persoana.PersoanaContact;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Service {
    private Scanner scanner;
    private Map<String, String> users;
    private ArrayList<Cladire> cladiri;
    private ArrayList<Chirias> chiriasi;
    private ArrayList<Angajat> angajati;
    private Map<Integer, SpatiuInchiriat> spatiiInchiriate;
    private Map<Integer, Consumabil> consumabile;
    public Map<Integer, Contract> contracte;
    public Map<Integer[], Factura> facturi;
    public Map<Integer, Antecedent> antecedente = new HashMap<>();
    private static Service instanta = null;
    private static final String DB_URL = "jdbc:mysql://sql8.freemysqlhosting.net:3306/sql8621755";
    private static final String USERNAME = "sql8621755";
    private static final String PASSWORD = "fQT6UYrabi";
    public static Connection connection;
    public Date today = new Date(2023, 5, 28);
    public Date fin = new Date(2024, 5, 28);

    private static void Con() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    private Service(){
        users = new HashMap<>();
        // Add some sample users and their passwords
        users.put("admin", "admin123");
        users.put("chirias", "chirias123");
        users.put("sef", "sef123");
        try{
            Con();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            //MethodDrop();
            initializeDatabase();
            //RutinaInsert1();
            GetData();
        }
    }

    public static Service getInstance() {
        if (instanta == null) {
            instanta = new Service();
        }
        return instanta;
    }

    public Object login(String username, String password, Integer id) throws IndexOutOfBoundsException{
        String storedPassword = users.get(username);
        if (storedPassword != null && storedPassword.equals(password))
            if (username.equals("admin")) {
                return adminMethod(id);
            } else if (username.equals("chirias")) {
                return chiriasMethod(id);
            } else if (username.equals("sef")) {
                return sefMethod();
            }
        return null;
    }

    public Object adminMethod(Integer id) throws IndexOutOfBoundsException {
        System.out.println("This is the admin method.");
        return new Admin(cladiri.get(id));
    }

    public Object chiriasMethod(Integer id) throws IndexOutOfBoundsException {
        System.out.println("This is the chirias method.");
        return new Chir(chiriasi.get(id));
    }

    public Object sefMethod() {
        System.out.println("This is the sef method.");
        return Sef.getInstance(cladiri, angajati);
    }

    public synchronized void initializeDatabase() {
        try {
            // Create a connection to the database
            ///Con();

            // Verify if the tables already exist
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "antecedente", null);

            if (!resultSet.next()) {
                // Create tables if they don't exist
                Audit.logAction("Creare tabele");

                // Chirias
                String chiriasQuery = "CREATE TABLE chiriasi (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume_afacere VARCHAR(255), " +
                        "iban VARCHAR(255))";
                Statement chiriasStatement = connection.createStatement();
                chiriasStatement.executeUpdate(chiriasQuery);

                // Antecedent
                String antecedentQuery = "CREATE TABLE antecedente (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "descriere VARCHAR(255), " +
                        "data DATE, " +
                        "chirias_id INT, " +
                        "FOREIGN KEY (chirias_id) REFERENCES chiriasi(id))";
                Statement antecedentStatement = connection.createStatement();
                antecedentStatement.executeUpdate(antecedentQuery);

                // Oras
                String orasQuery = "CREATE TABLE orase (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume_oras VARCHAR(255), " +
                        "numar_cladiri INT, " +
                        "nume_tara VARCHAR(255))";
                Statement orasStatement = connection.createStatement();
                orasStatement.executeUpdate(orasQuery);

                // Cladire
                String cladireQuery = "CREATE TABLE cladiri (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume_cladire VARCHAR(255), " +
                        "oras_id INT, " +
                        "numar_spatii INT, " +
                        "suprafata DOUBLE, " +
                        "FOREIGN KEY (oras_id) REFERENCES orase(id))";
                Statement cladireStatement = connection.createStatement();
                cladireStatement.executeUpdate(cladireQuery);

                // SpatiuComun
                String spatiuComunQuery = "CREATE TABLE spatii_comune (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "suprafata DOUBLE, " +
                        "cladire_id INT, " +
                        "tip_spatiu VARCHAR(255), " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id)) ";
                        
                Statement spatiuComunStatement = connection.createStatement();
                spatiuComunStatement.executeUpdate(spatiuComunQuery);

                // SpatiuInchiriat
                String spatiuInchiriatQuery = "CREATE TABLE spatii_inchiriate (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "suprafata DOUBLE, " +
                        "cladire_id INT, " +
                        "ocupat BOOLEAN, " +
                        "contracte_id INT, " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id)) ";
                Statement spatiuInchiriatStatement = connection.createStatement();
                spatiuInchiriatStatement.executeUpdate(spatiuInchiriatQuery);

                // Contract
                String contractQuery = "CREATE TABLE contracte (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "data_semnare DATE, " +
                        "data_finalizare DATE, " +
                        "pret_mp DOUBLE, " +
                        "rata_penalizare DOUBLE, " +
                        "spatiu_inchiriat_id INT, " +
                        "chirias_id INT, " +
                        "FOREIGN KEY (spatiu_inchiriat_id) REFERENCES spatii_inchiriate(id), " +
                        "FOREIGN KEY (chirias_id) REFERENCES chiriasi(id))";
                Statement contractStatement = connection.createStatement();
                contractStatement.executeUpdate(contractQuery);

                // Consumabil
                String consumabilQuery = "CREATE TABLE consumabile (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume_consumabil VARCHAR(255), " +
                        "pret DOUBLE)";
                Statement consumabilStatement = connection.createStatement();
                consumabilStatement.executeUpdate(consumabilQuery);

                // FacturaConsumabil
                String facturaConsumabilQuery = "CREATE TABLE facturi_consumabile (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "data_emitere DATE, " +
                        "pret DOUBLE, " +
                        "platit BOOLEAN, " +
                        "cladire_id INT, " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id))";
                Statement facturaConsumabilStatement = connection.createStatement();
                facturaConsumabilStatement.executeUpdate(facturaConsumabilQuery);

                String legConsumabilQuery = "CREATE TABLE consumabil_factura (id INT PRIMARY KEY AUTO_INCREMENT," +
                        "factura_id INT, " +
                        "consumabil_id INT, " +
                        "FOREIGN KEY (consumabil_id) REFERENCES consumabile(id), " +
                        "FOREIGN KEY (factura_id) REFERENCES facturi_consumabile(id))";
                Statement legConsumabilStatement = connection.createStatement();
                legConsumabilStatement.executeUpdate(legConsumabilQuery);

                // FacturaReparatie
                String facturaReparatieQuery = "CREATE TABLE facturi_reparatii (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "data_emitere DATE, " +
                        "pret DOUBLE, " +
                        "platit BOOLEAN, " +
                        "cladire_id INT, " +
                        "tip_reparatie VARCHAR(255), " +
                        "spatiu_id INT, " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id), " +
                        "FOREIGN KEY (spatiu_id) REFERENCES spatii_inchiriate(id))";
                Statement facturaReparatieStatement = connection.createStatement();
                facturaReparatieStatement.executeUpdate(facturaReparatieQuery);

                // FacturaUtilitate
                String facturaUtilitateQuery = "CREATE TABLE facturi_utilitati (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "data_emitere DATE, " +
                        "pret DOUBLE, " +
                        "platit BOOLEAN, " +
                        "cladire_id INT, " +
                        "data_scadenta DATE, " +
                        "tip_utilitate VARCHAR(255), " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id)) ";
                Statement facturaUtilitateStatement = connection.createStatement();
                facturaUtilitateStatement.executeUpdate(facturaUtilitateQuery);

                // Angajat
                String angajatQuery = "CREATE TABLE angajati (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume VARCHAR(255), " +
                        "varsta INT, " +
                        "email VARCHAR(255), " +
                        "numar_telefon VARCHAR(255), " +
                        "tip VARCHAR(255), " +
                        "salariu DOUBLE, " +
                        "adresa VARCHAR(255), " +
                        "data_angajare DATE, " +
                        "data_demisie DATE, " +
                        "cladire_id INT, " +
                        "FOREIGN KEY (cladire_id) REFERENCES cladiri(id))";
                Statement angajatStatement = connection.createStatement();
                angajatStatement.executeUpdate(angajatQuery);

                // PersoanaContact
                String persoanaContactQuery = "CREATE TABLE persoane_contact (id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nume VARCHAR(255), " +
                        "varsta INT, " +
                        "email VARCHAR(255), " +
                        "numar_telefon VARCHAR(255), " +
                        "oras_id INT, " +
                        "chirias_id INT, " +
                        "FOREIGN KEY (oras_id) REFERENCES orase(id)," +
                        "FOREIGN KEY (chirias_id) REFERENCES chiriasi(id))";
                Statement persoanaContactStatement = connection.createStatement();
                persoanaContactStatement.executeUpdate(persoanaContactQuery);
                System.out.println("Tables created successfully!");
            } else {
                System.out.println("Tables already exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Eroare fisier Audit");
        }
    }

    public synchronized void GetData() {
        try {
            spatiiInchiriate = new HashMap<>();
            angajati = new ArrayList<>();

            String consumabileQuery = "SELECT * FROM consumabile";
            Statement consumabilStatement = connection.createStatement();
            ResultSet consumabilResult = consumabilStatement.executeQuery(consumabileQuery);
            consumabile = new HashMap<>();
            while(consumabilResult.next()) {
                int id = consumabilResult.getInt("id");
                Consumabil c = new Consumabil(
                        consumabilResult.getString("nume_consumabil"),
                        consumabilResult.getDouble("pret")
                );
                consumabile.put(id, c);
            }


            String contractQuery = "SELECT * FROM contracte";
            Statement contractStatement = connection.createStatement();
            ResultSet contractResult = contractStatement.executeQuery(contractQuery);
            contracte = new HashMap<>();
            while(contractResult.next()) {
                Contract c = new Contract(
                        contractResult.getDate("data_semnare"),
                        contractResult.getDate("data_finalizare"),
                        contractResult.getDouble("pret_mp"),
                        contractResult.getDouble("rata_penalizare")
                );
                contracte.put(contractResult.getInt("id"), c);
            }

            String facturiQuery_1 = "SELECT * FROM facturi_utilitati";
            Statement facturiStatement_1 = connection.createStatement();
            ResultSet facturiResult_1 = facturiStatement_1.executeQuery(facturiQuery_1);
            String facturiQuery_2 = "SELECT * FROM facturi_reparatii";
            Statement facturiStatement_2 = connection.createStatement();
            ResultSet facturiResult_2 = facturiStatement_2.executeQuery(facturiQuery_2);
            String facturiQuery_3 = "SELECT * FROM facturi_consumabile";
            Statement facturiStatement_3 = connection.createStatement();
            ResultSet facturiResult_3 = facturiStatement_3.executeQuery(facturiQuery_3);
            facturi = new HashMap<>();
            
            while(facturiResult_1.next()) {
                int id = facturiResult_1.getInt("id");
                Factura f = new FacturaUtilitate(
                        facturiResult_1.getDate("data_emitere"),
                        facturiResult_1.getDouble("pret"),
                        facturiResult_1.getBoolean("platit"),
                        facturiResult_1.getDate("data_scadenta"),
                        facturiResult_1.getString("tip_utilitate")
                );
                facturi.put(new Integer[]{1, id}, f);
            }

            while(facturiResult_2.next()) {
                int id = facturiResult_2.getInt("id");
                Factura f = new FacturaReparatie(
                        facturiResult_2.getDate("data_emitere"),
                        facturiResult_2.getDouble("pret"),
                        facturiResult_2.getBoolean("platit"),
                        facturiResult_2.getString("tip_reparatie")
                );
                facturi.put(new Integer[]{2, id}, f);
            }

            while(facturiResult_3.next()) {
                int id = facturiResult_3.getInt("id");

                String consumabileQuery_1 = "SELECT * FROM consumabil_factura WHERE factura_id = " + id;
                Statement consumabilStatement_1 = connection.createStatement();
                ResultSet consumabilResult_1 = consumabilStatement_1.executeQuery(consumabileQuery_1);
                ArrayList<Consumabil> consumabile_1 = new ArrayList<>();
                while(consumabilResult_1.next()) {
                    int id_1 = consumabilResult_1.getInt("id");
                    consumabile_1.add(consumabile.get(id_1));
                }

                Factura f = new FacturaConsumabil(
                        facturiResult_3.getDate("data_emitere"),
                        facturiResult_3.getDouble("pret"),
                        facturiResult_3.getBoolean("platit"),
                        consumabile_1
                );
                facturi.put(new Integer[]{3, id}, f);
            }

            
            
            String chiriasQuery = "SELECT * FROM chiriasi";
            Statement chiriasStatement = connection.createStatement();
            ResultSet chiriasResult = chiriasStatement.executeQuery(chiriasQuery);
            chiriasi = new ArrayList<>();
            while (chiriasResult.next()) {
                int chiriasId = chiriasResult.getInt("id");
                String numeAfacere = chiriasResult.getString("nume_afacere");
                String iban = chiriasResult.getString("iban");
                
                Chirias c = new Chirias(
                        numeAfacere, 
                        iban
                );
                
                ArrayList<PersoanaContact> persoaneContact = new ArrayList<PersoanaContact>();
                String persoaneContactQuery = "SELECT * FROM persoane_contact WHERE chirias_id = " + chiriasId;
                Statement persoaneContactStatement = connection.createStatement();
                ResultSet persoaneContactResult = persoaneContactStatement.executeQuery(persoaneContactQuery);
                while (persoaneContactResult.next()) {
                    int persoanaContactId = persoaneContactResult.getInt("id");
                    String nume = persoaneContactResult.getString("nume");
                    String telefon = persoaneContactResult.getString("telefon");

                    String orasQuery = "SELECT * FROM orase WHERE id = " + persoaneContactResult.getInt("oras_id");
                    Statement orasStatement = connection.createStatement();
                    ResultSet orasResult = orasStatement.executeQuery(orasQuery);
                    Oras oras = new Oras(orasResult.getString("nume_oras"), orasResult.getInt("numar_cladiri"), orasResult.getString("nume_tara"));


                    PersoanaContact persoanaContact = new PersoanaContact(
                            nume,
                            persoaneContactResult.getInt("varsta"),
                            persoaneContactResult.getString("email"),
                            telefon,
                            oras
                    );
                    persoaneContact.add(persoanaContact);
                }

                ArrayList<Contract> contracte1 = new ArrayList<>();
                String contracteQuery = "SELECT * FROM contracte WHERE chirias_id = " + chiriasId;
                Statement contracteStatement = connection.createStatement();
                ResultSet contracteResult = contracteStatement.executeQuery(contracteQuery);
                while (contracteResult.next()) {
                    Contract contract = contracte.get(contracteResult.getInt("id"));
                    contract.setChirias(c);
                    contracte1.add(contract);
                }

                ArrayList<Antecedent> antecedente1 = new ArrayList<>();
                String antecedenteQuery = "SELECT * FROM antecedente WHERE chirias_id = " + chiriasId;
                Statement antecedenteStatement = connection.createStatement();
                ResultSet antecedenteResult = antecedenteStatement.executeQuery(antecedenteQuery);
                while (antecedenteResult.next()) {
                    String descriere = antecedenteResult.getString("descriere");
                    Antecedent antecedent = new Antecedent(descriere,
                            antecedenteResult.getDate("data")
                            );
                    antecedente1.add(antecedent);
                    antecedente.put(chiriasId, antecedent);
                }
                c.setAntecedente(antecedente1);
                c.setContracte(contracte1);
                c.setPersoaneContact(persoaneContact);
                chiriasi.add(c);
            }

            String cladireQuery = "SELECT * FROM cladiri";
            Statement cladireStatement = connection.createStatement();
            ResultSet cladireResult = cladireStatement.executeQuery(cladireQuery);
            cladiri = new ArrayList<>();
            while (cladireResult.next()) {
                int cladireId = cladireResult.getInt("id");
                String numeCladire = cladireResult.getString("nume_cladire");
                String orasQuery = "SELECT * FROM orase WHERE id = " + cladireResult.getInt("oras_id");
                Statement orasStatement = connection.createStatement();
                ResultSet orasResult = orasStatement.executeQuery(orasQuery);
                Oras oras = null;
                if(orasResult.next()) {
                    oras = new Oras(orasResult.getString("nume_oras"), orasResult.getInt("numar_cladiri"), orasResult.getString("nume_tara"));
                }
                Cladire cladire = new Cladire(numeCladire, oras, cladireResult.getInt("numar_spatii"), cladireResult.getDouble("suprafata"));

                String angajatiQuery = "SELECT * FROM angajati WHERE cladire_id = " + cladireId;
                Statement angajatiStatement = connection.createStatement();
                ResultSet angajatiResult = angajatiStatement.executeQuery(angajatiQuery);
                ArrayList<Angajat> angajati_1 = new ArrayList<>();
                while(angajatiResult.next()) {
                    Angajat angajat = new Angajat(
                            angajatiResult.getString("nume"),
                            angajatiResult.getInt("varsta"),
                            angajatiResult.getString("email"),
                            angajatiResult.getString("numar_telefon"),
                            angajatiResult.getString("tip"),
                            angajatiResult.getDouble("salariu"),
                            angajatiResult.getString("adresa"),
                            angajatiResult.getDate("data_angajare"),
                            angajatiResult.getDate("data_demisie"),
                            cladire
                            );
                    angajati_1.add(angajat);
                    angajati.add(angajat);
                }

                String spatiuComunQuery = "SELECT * FROM spatii_comune WHERE cladire_id = " + cladireId;
                Statement spatiuComunStatement = connection.createStatement();
                ResultSet spatiuComunResult = spatiuComunStatement.executeQuery(spatiuComunQuery);
                String spatiuInchiriatQuery = "SELECT * FROM spatii_inchiriate WHERE cladire_id = " + cladireId;
                Statement spatiuInchiriatStatement = connection.createStatement();
                ResultSet spatiuInchiriatResult = spatiuInchiriatStatement.executeQuery(spatiuInchiriatQuery);
                ArrayList<Spatiu> spatii = new ArrayList<>();

                while(spatiuComunResult.next()) {
                    Spatiu s = new SpatiuComun(
                            spatiuComunResult.getDouble("suprafata"),
                            cladire,
                            spatiuComunResult.getString("tip_spatiu")
                    );
                    spatii.add(s);
                }

                ArrayList<Contract> contracte_1 = new ArrayList<>();
                while(spatiuInchiriatResult.next()) {
                    int id = spatiuInchiriatResult.getInt("id");
                    boolean ocupat = spatiuInchiriatResult.getBoolean("ocupat");
                    int cont = spatiuInchiriatResult.getInt("contracte_id");
                    if(ocupat & cont != -1) {

                        SpatiuInchiriat s = new SpatiuInchiriat(
                                spatiuInchiriatResult.getDouble("suprafata"),
                                cladire,
                                spatiuInchiriatResult.getBoolean("ocupat"),
                                contracte.get(spatiuInchiriatResult.getInt("contracte_id"))
                        );
                        spatii.add(s);
                        spatiiInchiriate.put(id, s);
                        contracte.get(spatiuInchiriatResult.getInt("contracte_id")).setSpatiu(s);
                        contracte_1.add(contracte.get(spatiuInchiriatResult.getInt("contracte_id")));
                    }
                    else{
                        SpatiuInchiriat s = new SpatiuInchiriat(
                                spatiuInchiriatResult.getDouble("suprafata"),
                                cladire,
                                spatiuInchiriatResult.getBoolean("ocupat")
                        );
                        spatii.add(s);
                        spatiiInchiriate.put(id, s);
                    }
                }

                // Read Factura data for Cladire
                ArrayList<Factura> facturi_1 = new ArrayList<>();
                facturiQuery_1 = "SELECT * FROM facturi_utilitati WHERE cladire_id = " + cladireId;
                facturiStatement_1 = connection.createStatement();
                facturiResult_1 = facturiStatement_1.executeQuery(facturiQuery_1);
                facturiQuery_2 = "SELECT * FROM facturi_reparatii WHERE cladire_id = " + cladireId;
                facturiStatement_2 = connection.createStatement();
                facturiResult_2 = facturiStatement_2.executeQuery(facturiQuery_2);
                facturiQuery_3 = "SELECT * FROM facturi_consumabile WHERE cladire_id = " + cladireId;
                facturiStatement_3 = connection.createStatement();
                facturiResult_3 = facturiStatement_3.executeQuery(facturiQuery_3);

                while(facturiResult_1.next()) {
                    int id = facturiResult_1.getInt("id");
                    Integer[] k = new Integer[]{1, id};
                    Factura f = facturi.get(k);
                    f.setCladire(cladire);
                    facturi_1.add(f);
                }
                while(facturiResult_2.next()) {
                    int id = facturiResult_2.getInt("id");
                    Integer[] k = new Integer[]{2, id};
                    FacturaReparatie f = (FacturaReparatie) facturi.get(k);
                    f.setCladire(cladire);
                    facturi_1.add(f);
                    f.setSpatiu(spatiiInchiriate.get(facturiResult_2.getInt("spatiu_id")));
                }
                while(facturiResult_3.next()) {
                    int id = facturiResult_3.getInt("id");
                    Integer[] k = new Integer[]{3, id};
                    Factura f = facturi.get(k);
                    f.setCladire(cladire);
                    facturi_1.add(f);
                }
                cladire.setContracte(contracte_1);
                cladire.setAngajati(angajati_1);
                cladire.setFacturi(facturi_1);
                cladire.setSpatii(spatii);
                cladiri.add(cladire);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteData(){
        try {
            String deleteAntecedentsQuery = "DELETE FROM antecedente";
            String deleteChiriasiQuery = "DELETE FROM chiriasi";
            String deleteAngajatiQuery = "DELETE FROM angajati";
            String deletePCQuery = "DELETE FROM persoane_contact";
            String deleteCladiriQuery = "DELETE FROM cladiri";
            String deleteOraseQuery = "DELETE FROM orase";
            String deleteSCQuery = "DELETE FROM spatii_comune";
            String deleteSIQuery = "DELETE FROM spatii_inchiriate";
            String deleteContracteQuery = "DELETE FROM contracte";
            String deleteConsumabileQuery = "DELETE FROM consumabile";
            String deleteFacturiCQuery = "DELETE FROM facturi_consumabile";
            String deleteFacturiRQuery = "DELETE FROM facturi_reparatii";
            String deleteFacturiUtilitatiQuery = "DELETE FROM facturi_utilitati";
            String deleteFacturiCFQuery = "DELETE FROM consumabil_factura";

            Audit.logAction("Delete all");

            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteFacturiCFQuery);
            statement.executeUpdate(deleteFacturiCQuery);
            statement.executeUpdate(deleteFacturiRQuery);
            statement.executeUpdate(deleteFacturiUtilitatiQuery);
            statement.executeUpdate(deleteContracteQuery);
            statement.executeUpdate(deleteSCQuery);
            statement.executeUpdate(deleteSIQuery);
            statement.executeUpdate(deleteConsumabileQuery);
            statement.executeUpdate(deleteAngajatiQuery);
            statement.executeUpdate(deleteAntecedentsQuery);
            statement.executeUpdate(deletePCQuery);
            statement.executeUpdate(deleteChiriasiQuery);
            statement.executeUpdate(deleteCladiriQuery);
            statement.executeUpdate(deleteOraseQuery);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Eroare fisier Audit");
        }
    }
    private synchronized void MethodDrop(){
        try{
            String deleteChiriasiQuery = "DROP TABLE IF EXISTS chiriasi";
            String deleteOraseQuery = "DROP TABLE IF EXISTS orase";
            String deleteCladiriQuery = "DROP TABLE IF EXISTS cladiri";
            String deleteAntecedentsQuery = "DROP TABLE IF EXISTS antecedente";
            String deleteAngajatiQuery = "DROP TABLE IF EXISTS angajati";
            String deletePCQuery = "DROP TABLE IF EXISTS persoane_contact";
            String deleteSCQuery = "DROP TABLE IF EXISTS spatii_comune";
            String deleteSIQuery = "DROP TABLE IF EXISTS spatii_inchiriate";
            String deleteContracteQuery = "DROP TABLE IF EXISTS contracte";
            String deleteConsumabileQuery = "DROP TABLE IF EXISTS consumabile";
            String deleteFacturiCQuery = "DROP TABLE IF EXISTS facturi_consumabile";
            String deleteFacturiRQuery = "DROP TABLE IF EXISTS facturi_reparatii";
            String deleteFacturiUtilitatiQuery = "DROP TABLE IF EXISTS facturi_utilitati";
            String deleteFacturiCFQuery = "DROP TABLE IF EXISTS consumabil_factura";

            Audit.logAction("Drop all");

            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteFacturiCFQuery);
            statement.executeUpdate(deleteFacturiCQuery);
            statement.executeUpdate(deleteFacturiRQuery);
            statement.executeUpdate(deleteFacturiUtilitatiQuery);
            statement.executeUpdate(deleteContracteQuery);
            statement.executeUpdate(deleteSCQuery);
            statement.executeUpdate(deleteSIQuery);
            statement.executeUpdate(deleteConsumabileQuery);
            statement.executeUpdate(deleteAngajatiQuery);
            statement.executeUpdate(deleteAntecedentsQuery);
            statement.executeUpdate(deletePCQuery);
            statement.executeUpdate(deleteChiriasiQuery);
            statement.executeUpdate(deleteCladiriQuery);
            statement.executeUpdate(deleteOraseQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Eroare fisier Audit");
        }
    }

    private synchronized void RutinaInsert1(){
        ArrayList<Consumabil> cons = new ArrayList<>();
        cons.add(new Consumabil("matura", 10));
        cons.add(new Consumabil("mop", 20));
        cons.add(new Consumabil("bec", 5));
        for(Consumabil c:cons){
            c.Insert(connection);
        }

        ArrayList<Chirias> chir = new ArrayList<>();
        chir.add(new Chirias("Podoma", "12345"));
        chir.add(new Chirias("Salt", "123456"));
        chir.add(new Chirias("Nestea", "8789753478"));
        chir.add(new Chirias("Dorna", "42355252"));
        for(Chirias chirias:chir){
            chirias.Insert(connection);
        }

        ArrayList<Antecedent> ante1 = new ArrayList<>();
        ante1.add(new Antecedent("a lasat becul aprins", new Date(121, 8, 15)));
        ante1.add(new Antecedent("a provocat scurt circuit", new Date(2022, 8, 15)));
        chir.get(1).setAntecedente(ante1);
        for(Antecedent a:ante1){
            a.Insert(connection, chir.get(1));
        }

        ArrayList<Antecedent> ante2 = new ArrayList<>();
        ante2.add(new Antecedent("a lasat becul aprins", new Date(121, 8, 15)));
        ante2.add(new Antecedent("a provocat scurt circuit", new Date(2022, 8, 15)));
        chir.get(3).setAntecedente(ante2);
        for(Antecedent a:ante2){
            a.Insert(connection, chir.get(3));
        }

        ArrayList<Oras> orase1 = new ArrayList<>();
        orase1.add(new Oras("Bucuresti", 2, "Romania"));
        orase1.add(new Oras("Brasov", 1, "Romania"));
        orase1.add(new Oras("Roma", 1, "Italia"));

        for(Oras oras:orase1){
            oras.Insert(connection);
        }

        ArrayList<Cladire> clad = new ArrayList<>();
        clad.add(new Cladire("Soare", orase1.get(0), 20, 2000));
        clad.add(new Cladire("Elefant", orase1.get(0), 21, 2100));
        clad.add(new Cladire("Fantezia", orase1.get(1), 15, 2020));
        clad.add(new Cladire("Santal", orase1.get(2), 10, 1900));
        for(Cladire c: clad){
            c.Insert(connection);
        }

        ArrayList<Spatiu> s1 = new ArrayList<>();
        s1.add(new SpatiuInchiriat(20, clad.get(0), false));
        s1.add(new SpatiuInchiriat(20, clad.get(0), false));
        s1.add(new SpatiuComun(20, clad.get(0), "baie"));
        clad.get(0).setSpatii(s1);
        for(Spatiu s:s1){
            s.Insert(connection);
        }

        ArrayList<Spatiu> s2 = new ArrayList<>();
        s2.add(new SpatiuInchiriat(20, clad.get(1), false));
        s2.add(new SpatiuInchiriat(20, clad.get(1), false));
        s2.add(new SpatiuComun(20, clad.get(1), "hol"));
        clad.get(1).setSpatii(s2);
        for(Spatiu s:s2){
            s.Insert(connection);
        }

        ArrayList<Spatiu> s3 = new ArrayList<>();
        s3.add(new SpatiuInchiriat(20, clad.get(2), false));
        s3.add(new SpatiuInchiriat(20, clad.get(2), false));
        s3.add(new SpatiuComun(20, clad.get(2), "baie"));
        clad.get(2).setSpatii(s3);
        for(Spatiu s:s3){
            s.Insert(connection);
        }

        ArrayList<Spatiu> s4 = new ArrayList<>();
        s4.add(new SpatiuInchiriat(20, clad.get(3), false));
        s4.add(new SpatiuInchiriat(20, clad.get(3), false));
        s4.add(new SpatiuComun(20, clad.get(3), "baie"));
        clad.get(3).setSpatii(s4);
        for(Spatiu s:s4){
            s.Insert(connection);
        }

        ArrayList<Angajat> anga1 = new ArrayList<>();
        anga1.add(new Angajat("Popa Ion", 40, "popaion@gmail.com", "0731938475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(0)));
        anga1.add(new Angajat("Pop Ionut", 35, "popionut@gmail.com", "0731938475", "paza", 2500, "Buuc", new Date(2022, 10, 5), null, clad.get(0)));
        anga1.add(new Angajat("Ionescu Maria", 30, "ionescumaria@gmail.com", "0731348475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(0)));
        clad.get(0).setAngajati(anga1);
        for(Angajat a: anga1){
            a.Insert(connection);
        }

        ArrayList<Angajat> anga2 = new ArrayList<>();
        anga2.add(new Angajat("Popa Ion", 40, "popaion@gmail.com", "0731938475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(1)));
        anga2.add(new Angajat("Pop Ionut", 35, "popionut@gmail.com", "0731938475", "paza", 2500, "Buuc", new Date(2022, 10, 5), null, clad.get(1)));
        anga2.add(new Angajat("Ionescu Maria", 30, "ionescumaria@gmail.com", "0731348475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(1)));
        clad.get(1).setAngajati(anga2);
        for(Angajat a: anga2){
            a.Insert(connection);
        }

        ArrayList<Angajat> anga3 = new ArrayList<>();
        anga3.add(new Angajat("Popa Ion", 40, "popaion@gmail.com", "0731938475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(2)));
        anga3.add(new Angajat("Pop Ionut", 35, "popionut@gmail.com", "0731938475", "paza", 2500, "Buuc", new Date(2022, 10, 5), null, clad.get(2)));
        anga3.add(new Angajat("Ionescu Maria", 30, "ionescumaria@gmail.com", "0731348475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(2)));
        clad.get(2).setAngajati(anga3);
        for(Angajat a: anga3){
            a.Insert(connection);
        }

        ArrayList<Angajat> anga4 = new ArrayList<>();
        anga4.add(new Angajat("Popa Ion", 40, "popaion@gmail.com", "0731938475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(3)));
        anga4.add(new Angajat("Pop Ionut", 35, "popionut@gmail.com", "0731938475", "paza", 2500, "Buuc", new Date(2022, 10, 5), null, clad.get(3)));
        anga4.add(new Angajat("Ionescu Maria", 30, "ionescumaria@gmail.com", "0731348475", "curatenie", 2000, "Buuc", new Date(2022, 10, 5), null, clad.get(3)));
        clad.get(3).setAngajati(anga4);
        for(Angajat a: anga4){
            a.Insert(connection);
        }


    }

    public synchronized void RutinaFinal(){
        try {
            DeleteData();

            for (Consumabil c : consumabile.values()) {
                c.Insert(connection);
            }

            for (Chirias c : chiriasi) {
                c.Insert(connection);
                for (Antecedent a : c.getAntecedente()) {
                    a.Insert(connection, c);
                }
                for (PersoanaContact p : c.getPersoaneContact()) {
                    p.getOras().Insert(connection);
                    p.Insert(connection, c);
                }
            }
            for (Cladire c : cladiri) {
                c.getOras().Insert(connection);
                c.Insert(connection);
                for (Spatiu s : c.getSpatii()) {
                    s.Insert(connection);
                }
                for (Angajat a : c.getAngajati()) {
                    a.Insert(connection);
                }
                for (Factura f : c.getFacturi()) {
                    f.Insert(connection);
                }
                for (Contract contract : c.getContracte()) {
                    contract.Insert(connection);
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void RutinaPrincipala(Object ob, Scanner scanner) throws Exception {
        this.scanner = scanner;
        if(ob instanceof Admin){
            RutinaAdmin((Admin) ob);
        } else if (ob instanceof Chir) {
            RutinaChirias((Chir) ob);
        } else if (ob instanceof Sef){
            RutinaSef((Sef) ob);
        }
        else throw new Exception("Ob Invalid");
    }

    private void RutinaAdmin(Admin admin){
        while(true){
            admin.Consulta();
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if(id == 1){
                    System.out.println(admin.GetChiriasi(today));
                }
                else if(id == 2) {
                    System.out.println(admin.GetFacturi(today));
                } else if (id == 3) {
                    admin.AfisCumparaturi(today);
                } else if(id == 4) {
                    return;
                }
            }
            catch (java.lang.NumberFormatException e){
                System.out.println("Numar invalid");
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            catch(java.lang.NullPointerException e){
                System.out.println("Null");
            }
        }
    }

    private void RutinaChirias(Chir chirias){
        while(true){
            chirias.Consulta();
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if(id == 1){
                    System.out.println("Contracte:");
                    int i = 0;
                    for(Contract c:chirias.chirias.getContracte()){
                        System.out.println(i);
                        System.out.println(c);
                        i += 1;
                    }
                    int contract = Integer.parseInt(scanner.nextLine());
                    System.out.println(chirias.GetFacturaCurenta(today, chirias.chirias.getContracte().get(contract)));

                }
                else if(id == 2){
                    System.out.println(chirias.GetTotal(today));
                } else if (id == 3) {
                    System.out.println(chirias.GetReparatii(today));
                } else if (id == 4) {
                    return;
                } else{
                    System.out.println("Eroare");
                }
            }
            catch (java.lang.NumberFormatException e){
                System.out.println("Numar invalid");
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            catch(java.lang.NullPointerException e){
                System.out.println("Null");
            }
        }

    }

    private void RutinaSef(Sef sef){
        while(true){
            sef.Consulta();
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if(id == 1){
                    System.out.println(sef.GetTotal(today));
                } else if(id == 2){
                    System.out.println(sef.GetBalanta(today));
                } else if (id == 3){
                    System.out.println(sef.GetFacturi(today));
                } else if (id == 4) {
                   sef.GetAngajati(today);
                } else if (id == 5) {
                    System.out.println("Spatii:");
                    int i = 1;
                    for (Cladire c : sef.cladiri) {
                        for (Spatiu s : c.getSpatii()) {
                            if (s instanceof SpatiuInchiriat) {
                                SpatiuInchiriat t = (SpatiuInchiriat) s;
                                if (!t.isOcupat()) {
                                    System.out.println(i);
                                    System.out.println(t);
                                    i += 1;
                                }
                            }
                        }
                    }
                    int spatiu = Integer.parseInt(scanner.nextLine());
                    if (spatiu > i) {
                        System.out.println("Eroare");
                    } else {
                        SpatiuInchiriat sp = null;
                        i = 1;
                        for (Cladire c : sef.cladiri) {
                            for (Spatiu s : c.getSpatii()) {
                                if (s instanceof SpatiuInchiriat) {
                                    SpatiuInchiriat t = (SpatiuInchiriat) s;
                                    if (!t.isOcupat()) {
                                        if (i == spatiu) sp = t;
                                    }
                                }
                            }
                        }
                        i = 1;
                        System.out.println("Chiriasi:");
                        for (Chirias c : chiriasi) {
                            System.out.println(i);
                            System.out.println(c);
                            i += 1;
                        }
                        int chir = Integer.parseInt(scanner.nextLine());
                        if (spatiu > i) {
                            System.out.println("Eroare");
                        } else {
                            i = 1;
                            Chirias chirias = null;
                            System.out.println("Chiriasi:");
                            for (Chirias c : chiriasi) {
                                if (i == chir) chirias = c;
                                i += 1;
                            }
                            assert sp != null;
                            sef.InchiriereSpatiu(today, fin, chirias, sp, sp.getSuprafata(), 20, 0.1);
                        }
                    }
                }
                else if (id == 6) {
                    return;
                }
                else{
                    System.out.println("Eroare");
                }
            }
            catch (java.lang.NumberFormatException e){
                System.out.println("Numar invalid");
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            catch(java.lang.NullPointerException e){
                System.out.println("Null");
            }
        }
    }
}
