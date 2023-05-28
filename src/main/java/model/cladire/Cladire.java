package model.cladire;

import model.chirias.Contract;
import model.factura.Factura;
import model.persoana.Angajat;
import service.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Cladire implements Cloneable {
    protected String numeCladire;
    protected Oras oras;
    protected final int numarSpatii;
    protected final double suprafata;
    private Integer dbId = null;
    protected ArrayList<Spatiu> spatii;
    protected ArrayList<Factura> facturi;
    protected ArrayList<Contract> contracte;
    protected ArrayList<Angajat> angajati;

    public Cladire(String numeCladire, Oras oras, int numarSpatii, double suprafata, ArrayList<Spatiu> spatii, ArrayList<Factura> facturi, ArrayList<Contract> contracte, ArrayList<Angajat> angajati) {
        this.numeCladire = numeCladire;
        this.oras = oras;
        this.numarSpatii = numarSpatii;
        this.suprafata = suprafata;
        this.spatii = new ArrayList<>(spatii);
        this.facturi = new ArrayList<>(facturi);
        this.contracte = new ArrayList<>(contracte);
        this.angajati = angajati;
    }

    public Cladire(String numeCladire, Oras oras, int numarSpatii, double suprafata) {
        this.numeCladire = numeCladire;
        this.oras = oras;
        this.numarSpatii = numarSpatii;
        this.suprafata = suprafata;
    }

    public void setSpatii(ArrayList<Spatiu> spatii) {
        this.spatii = spatii;
    }

    public void setFacturi(ArrayList<Factura> facturi) {
        this.facturi = facturi;
    }

    public void setContracte(ArrayList<Contract> contracte) {
        this.contracte = contracte;
    }

    public String getNumeCladire() {
        return numeCladire;
    }

    public Oras getOras() {
        return oras;
    }

    public int getNumarSpatii() {
        return numarSpatii;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public ArrayList<Spatiu> getSpatii() {
        return new ArrayList<>(spatii);
    }

    public ArrayList<Factura> getFacturi() {
        return new ArrayList<>(facturi);
    }

    @Override
    public Cladire clone() throws CloneNotSupportedException {
        return new Cladire(this.numeCladire, this.oras.clone(), this.numarSpatii, this.suprafata, new ArrayList<>(this.spatii), new ArrayList<>(this.facturi), this.contracte, this.angajati);
    }

    public void AdaugaFactura(Factura facturaNoua) {
        facturi.add(facturaNoua);
    }

    public ArrayList<Contract> getContracte() {
        return contracte;
    }

    public ArrayList<Angajat> getAngajati() {
        return angajati;
    }

    public void setAngajati(ArrayList<Angajat> angajati) {
        this.angajati = angajati;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cladire other) {
            return this.numeCladire.equals(other.numeCladire) && this.oras.equals(other.oras);
        }
        return false;
    }

    public double GetTotalIncasat(Date azi) {
        double totalIncasat = 0.0;

        for (Contract contract : contracte) {
            if (contract.getDataSemnare().before(azi) && (contract.getDataFinalizare() == null || contract.getDataFinalizare().after(azi))) {
                SpatiuInchiriat spatiu = null;
                try {
                    spatiu = contract.getSpatiu().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                if (spatiu != null && spatiu.isOcupat()) {
                    totalIncasat += contract.getPretMP() * spatiu.getSuprafata();
                }
            }
        }

        return totalIncasat;    }

    public double GetTotalDeDat(Date azi) {
        double totalDeDat = 0.0;

        for (Factura factura : facturi) {
            if (factura.getDataEmitere().getMonth() == azi.getMonth()) {
                totalDeDat += factura.getPret();
            }
        }

        return totalDeDat;
    }

    public Integer getDbId() {
        if(dbId == null){
            Insert(Service.connection);
        }
        return dbId;
    }

    public boolean Insert(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO cladiri (nume_cladire, " +
                    "oras_id, " +
                    "numar_spatii, " +
                    "suprafata) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, numeCladire);
            insertStatement.setInt(2, oras.getDbId());
            insertStatement.setInt(3, numarSpatii);
            insertStatement.setDouble(4, suprafata);
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
        } catch (SQLException e) {
            System.out.println("Eroare\n");
            return false;
        }
        return true;
    }

    public boolean Update(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE cladiri SET " +
                    "nume_cladire = ?, " +
                    "oras_id = ?, " +
                    "numar_spatii = ?, " +
                    "suprafata = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, numeCladire);
            updateStatement.setInt(2, oras.getDbId());
            updateStatement.setInt(3, numarSpatii);
            updateStatement.setDouble(4, suprafata);
            updateStatement.setInt(5, dbId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM cladiri WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
