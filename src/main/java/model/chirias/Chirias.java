package model.chirias;

import model.factura.Factura;
import model.persoana.PersoanaContact;
import service.Audit;
import service.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Chirias implements Cloneable {
    private final String numeAfacere;
    private String iban;
    private Integer dbId = null;
    private ArrayList<PersoanaContact> persoaneContact;
    private ArrayList<Contract> contracte;
    private ArrayList<Antecedent> antecedente;

    public Chirias(String numeAfacere, String iban, ArrayList<PersoanaContact> persoaneContact, ArrayList<Contract> contracte, ArrayList<Antecedent> antecedente) {
        this.numeAfacere = numeAfacere;
        this.iban = iban;
        this.persoaneContact = new ArrayList<>(persoaneContact);
        this.contracte = new ArrayList<>(contracte);
        this.antecedente = new ArrayList<>(antecedente);
    }

    public Chirias(String numeAfacere, String iban) {
        this.numeAfacere = numeAfacere;
        this.iban = iban;
    }

    public String getNumeAfacere() {
        return numeAfacere;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public ArrayList<PersoanaContact> getPersoaneContact() {
        return new ArrayList<>(persoaneContact);
    }

    public void setPersoaneContact(ArrayList<PersoanaContact> persoaneContact) {
        this.persoaneContact = new ArrayList<>(persoaneContact);
    }

    public ArrayList<Contract> getContracte() {
        return new ArrayList<>(contracte);
    }

    public void setContracte(ArrayList<Contract> contracte) {
        this.contracte = new ArrayList<>(contracte);
    }

    public ArrayList<Antecedent> getAntecedente() {
        return new ArrayList<>(antecedente);
    }

    public void setAntecedente(ArrayList<Antecedent> antecedente) {
        this.antecedente = new ArrayList<>(antecedente);
    }

    @Override
    public Chirias clone() throws CloneNotSupportedException {
        return new Chirias(this.numeAfacere, this.iban, new ArrayList<>(this.persoaneContact), new ArrayList<>(this.contracte), new ArrayList<>(this.antecedente));
    }

    public void AdaugaContract(Contract cNou) throws CloneNotSupportedException {
        contracte.add(cNou.Copie());
    }

    public void AdaugaPersoanaContact(PersoanaContact pNou) {
        persoaneContact.add(pNou.clone());
    }

    public void ScoatePersoanaContact(PersoanaContact pVechi) {
        persoaneContact.remove(pVechi);
    }

    public void AdaugaAntecedent(Antecedent aNou) {
        antecedente.add(aNou.Copie());
    }

    public Integer getDbId() {
        if(dbId == null){
            Insert(Service.connection);
        }
        System.out.println(dbId);
        return dbId;
    }

    public boolean Insert(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO chiriasi (nume_afacere, " +
                    "iban) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, numeAfacere);
            insertStatement.setString(2, iban);
            insertStatement.executeUpdate();
            Statement idStatement = connection.createStatement();
            ResultSet idResultSet = idStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if (idResultSet.next()) {
                dbId = idResultSet.getInt(1);
                System.out.println("Newly inserted ID: " + dbId);
            } else {
                System.out.println("Failed to retrieve the generated ID.");
            }

            System.out.println("Success!\n");
            Audit.logAction("Insert");
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            System.out.println("Eroare Audit");
        }
        return false;
    }

    public boolean Update(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE chiriasi SET " +
                    "nume_afacere = ?, " +
                    "iban = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, numeAfacere);
            updateStatement.setString(2, iban);
            updateStatement.setInt(3, dbId);
            updateStatement.executeUpdate();
            Audit.logAction("Update");
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            System.out.println("Eroare Audit");
        }
        return false;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM chiriasi WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
            Audit.logAction("Delete");
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            System.out.println("Eroare Audit");
        }
        return true;
    }

}
