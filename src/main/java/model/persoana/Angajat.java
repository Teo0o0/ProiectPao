/*
    * Extinde clasa Persoana, la care adauga:
    * - tip
    * - salariu
    * - adresa
    * - dataAngajare
    * - dataDemisie
    * Toate campurile, cu exceptia datei de Angajare, pot fi modificate pe parcurs
 */
package model.persoana;

import model.chirias.Chirias;
import model.cladire.Cladire;
import service.Service;

import java.sql.*;
import java.util.Date;
import java.util.Objects;

public class Angajat extends Persoana implements Cloneable {
    protected String tip;
    protected double salariu;
    protected String adresa;
    protected final Date dataAngajare;
    protected Date dataDemisie;
    protected Cladire cladire;
    private Integer dbId = null;
    private boolean ValidareTip(String tip){
        if(Objects.equals(tip, "administrator") || Objects.equals(tip, "curatenie") || Objects.equals(tip, "paza")) return true;
        return false;
    }
    public Angajat(String nume, int varsta, String email, String numarTelefon, String tip, double salariu, String adresa, Date dataAngajare, Date dataDemisie, Cladire cladire) {
        super(nume, varsta, email, numarTelefon);
        boolean ok = ValidareTip(tip);
        if(!ok) throw new IllegalArgumentException("Tip invalid");
        this.tip = tip;
        this.salariu = salariu;
        this.adresa = adresa;
        this.dataAngajare = dataAngajare;
        this.dataDemisie = dataDemisie;
        this.cladire = cladire;
    }

    public Angajat(String nume, int varsta, String email, String numarTelefon, String tip, double salariu, String adresa, Date dataAngajare, Date dataDemisie) {
        super(nume, varsta, email, numarTelefon);
        this.tip = tip;
        this.salariu = salariu;
        this.adresa = adresa;
        this.dataAngajare = dataAngajare;
        this.dataDemisie = dataDemisie;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Date getDataAngajare() {
        return dataAngajare;
    }

    public Date getDataDemisie() {
        return dataDemisie;
    }

    public void setDataDemisie(Date dataDemisie) {
        this.dataDemisie = dataDemisie;
    }

    @Override
    protected Angajat clone() throws CloneNotSupportedException {
        return new Angajat(this.nume, this.varsta, this.email, this.numarTelefon, this.tip, this.salariu, this.adresa, this.dataAngajare, this.dataDemisie, this.cladire.clone());
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
            String insertQuery = "INSERT INTO angajati (nume, " +
                    "varsta, " +
                    "email, " +
                    "numar_telefon, " +
                    "tip, " +
                    "salariu, " +
                    "adresa, " +
                    "data_angajare, " +
                    "data_demisie, " +
                    "cladire_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, nume);
            insertStatement.setInt(2, varsta);
            insertStatement.setString(3, email);
            insertStatement.setString(4, numarTelefon);
            insertStatement.setString(5, tip);
            insertStatement.setDouble(6, salariu);
            insertStatement.setString(7, adresa);
            insertStatement.setDate(8, (java.sql.Date) dataAngajare);
            insertStatement.setDate(9, (java.sql.Date) dataDemisie);
            insertStatement.setInt(10, cladire.getDbId());
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
            return false;
        }
        return true;
    }

    public boolean Update(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE angajati SET " +
                    "nume = ?, " +
                    "varsta = ?, " +
                    "email = ?, " +
                    "numar_telefon = ?, " +
                    "tip = ?, " +
                    "salariu = ?, " +
                    "adresa = ?, " +
                    "data_angajare = ?, " +
                    "data_demisie = ?, " +
                    "cladire_id = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, nume);
            updateStatement.setInt(2, varsta);
            updateStatement.setString(3, email);
            updateStatement.setString(4, numarTelefon);
            updateStatement.setString(5, tip);
            updateStatement.setDouble(6, salariu);
            updateStatement.setString(7, adresa);
            updateStatement.setDate(8, (java.sql.Date) dataAngajare);
            updateStatement.setDate(9, (java.sql.Date) dataDemisie);
            updateStatement.setInt(10, cladire.getDbId());
            updateStatement.setInt(11, dbId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean Insert(Connection connection, Chirias c) {
        return false;
    }

    @Override
    public boolean Update(Connection connection, Chirias c) {
        return false;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM angajati WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
