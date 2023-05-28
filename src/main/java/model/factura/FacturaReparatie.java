/*
    * Extinde Factura cu:
    * - tipReparatie
    * - model.chirias.persoana
    * - spatiu
    * Campurile nu pot fi modificate ulterior
 */
package model.factura;

import model.cladire.Cladire;
import model.cladire.Spatiu;
import model.cladire.SpatiuInchiriat;
import model.persoana.PersoanaContact;
import service.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FacturaReparatie extends Factura implements Cloneable{
    protected final String tipReparatie;
    protected SpatiuInchiriat spatiu; // agregare
    private Integer dbId = null;

    public FacturaReparatie(Date dataEmitere, double pret, boolean platit, String tipReparatie) {
        super(dataEmitere, pret, platit);
        this.tipReparatie = tipReparatie;
    }

    public FacturaReparatie(Date dataEmitere, double pret, boolean platit, Cladire cladire, String tipReparatie, SpatiuInchiriat spatiu) {
        super(dataEmitere, pret, platit, cladire);
        this.tipReparatie = tipReparatie;
        this.spatiu = spatiu;
    }

    public void setSpatiu(SpatiuInchiriat spatiu) {
        this.spatiu = spatiu;
    }

    public String getTipReparatie() {
        return tipReparatie;
    }


    public Spatiu getSpatiu() throws CloneNotSupportedException {
        return spatiu;
    }

    @Override
    public String toString() {
        return "FacturaReparatie{" +
                "tipReparatie='" + tipReparatie + '\'' +
                ", spatiu=" + spatiu +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    @Override
    public FacturaReparatie clone() throws CloneNotSupportedException {
        return new FacturaReparatie(this.dataEmitere, this.pret, this.platit, this.cladire.clone(), this.tipReparatie, this.spatiu);
    }

    @Override
    public ArrayList<Consumabil> getProduse() {
        return null;
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
            String insertQuery = "INSERT INTO facturi_reparatii ( " +
                    "data_emitere, " +
                    "pret, " +
                    "platit, " +
                    "cladire_id, " +
                    "tip_reparatie, " +
                    "spatiu_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDate(1, (java.sql.Date) dataEmitere);
            insertStatement.setDouble(2, pret);
            insertStatement.setBoolean(3, platit);
            insertStatement.setInt(4, cladire.getDbId());
            insertStatement.setString(5, tipReparatie);
            insertStatement.setInt(6, spatiu.getDbId());
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
            String updateQuery = "UPDATE facturi_reparatii SET " +
                    "data_emitere = ?, " +
                    "pret = ?, " +
                    "platit = ?, " +
                    "cladire_id = ?," +
                    "tip_reparatie = ?," +
                    "spatiu_id = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDate(1, (java.sql.Date) dataEmitere);
            updateStatement.setDouble(2, pret);
            updateStatement.setBoolean(3, platit);
            updateStatement.setInt(4, cladire.getDbId());
            updateStatement.setString(5, tipReparatie);
            updateStatement.setInt(6, spatiu.getDbId());
            updateStatement.setInt(7, dbId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM facturi_reparatii WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
