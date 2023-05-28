/*
    * Extinde clasa Factura cu:
    * - dataScadenta
    * - tipUtilitate
    * Nu pot fi modificate ulterior
 */
package model.factura;

import model.cladire.Cladire;
import service.Audit;
import service.Service;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FacturaUtilitate extends Factura{

    protected final Date dataScadenta;
    protected final String tipUtilitate;
    private Integer dbId = null;
    public FacturaUtilitate(Date dataEmitere, double pret, boolean platit, Date dataScadenta, String tipUtilitate, Cladire cladire) {
        super(dataEmitere, pret, platit, cladire);
        this.dataScadenta = dataScadenta;
        this.tipUtilitate = tipUtilitate;
    }

    public FacturaUtilitate(Date dataEmitere, double pret, boolean platit, Date dataScadenta, String tipUtilitate) {
        super(dataEmitere, pret, platit);
        this.dataScadenta = dataScadenta;
        this.tipUtilitate = tipUtilitate;
    }

    @Override
    public String toString() {
        return "FacturaUtilitate{" +
                "dataScadenta=" + dataScadenta +
                ", tipUtilitate='" + tipUtilitate + '\'' +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    public Date getDataScadenta() {
        return dataScadenta;
    }

    public String getTipUtilitate() {
        return tipUtilitate;
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
            String insertQuery = "INSERT INTO facturi_utilitati ( " +
                    "data_emitere, " +
                    "pret, " +
                    "platit, " +
                    "cladire_id, " +
                    "tip_utilitate, " +
                    "data_scadenta) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDate(1, (java.sql.Date) dataEmitere);
            insertStatement.setDouble(2, pret);
            insertStatement.setBoolean(3, platit);
            insertStatement.setInt(4, cladire.getDbId());
            insertStatement.setString(5, tipUtilitate);
            insertStatement.setDate(6, (java.sql.Date) dataScadenta);
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
        return true;
    }

    public boolean Update(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE facturi_utilitati SET " +
                    "data_emitere = ?, " +
                    "pret = ?, " +
                    "platit = ?, " +
                    "cladire_id = ?," +
                    "tip_rutilitate = ?," +
                    "data_scadenta = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDate(1, (java.sql.Date) dataEmitere);
            updateStatement.setDouble(2, pret);
            updateStatement.setBoolean(3, platit);
            updateStatement.setInt(4, cladire.getDbId());
            updateStatement.setString(5, tipUtilitate);
            updateStatement.setDate(6, (java.sql.Date) dataScadenta);
            updateStatement.setInt(7, dbId);
            updateStatement.executeUpdate();
            Audit.logAction("Update");
        } catch (SQLException e) {
            return false;
        } catch (IOException e) {
            System.out.println("Eroare Audit");
        }
        return true;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM facturi_utilitati WHERE id = ?";
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
