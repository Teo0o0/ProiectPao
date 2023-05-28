/*
    * Spatiile compune ce extind cu:
    * - tipSpatiu
 */
package model.cladire;

import model.chirias.Contract;
import service.Audit;
import service.Service;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class SpatiuComun extends Spatiu implements Cloneable{
    protected String tipSpatiu;
    private Integer dbId = null;
    boolean ValidateTipSpatiu(String tipSpatiu)
    {
        if(Objects.equals(tipSpatiu, "baie") || Objects.equals(tipSpatiu, "hol")) return true;
        return false;
    }

    public SpatiuComun(double suprafata, Cladire Cladire, String tipSpatiu) {
        super(suprafata, Cladire);
        if(!ValidateTipSpatiu(tipSpatiu)) throw new IllegalArgumentException("Tip Saptiu Invalid");
        this.tipSpatiu = tipSpatiu;
    }

    public String getTipSpatiu() {
        return tipSpatiu;
    }

    @Override
    public Object clone() {
        return new SpatiuComun(this.suprafata, this.cladire, this.tipSpatiu);
    }

    @Override
    public Contract getContract() {
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
            String insertQuery = "INSERT INTO spatii_comune (" +
                    "suprafata, " +
                    "cladire_id, " +
                    "tip_spatiu) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDouble(1, suprafata);
            insertStatement.setInt(2, cladire.getDbId());
            insertStatement.setString(3, tipSpatiu);
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
            String updateQuery = "UPDATE spatii_comune SET " +
                    "suprafata = ?, " +
                    "cladire_id = ?, " +
                    "tip_spatiu = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, suprafata);
            updateStatement.setInt(2, cladire.getDbId());
            updateStatement.setString(3, tipSpatiu);
            updateStatement.setInt(4, dbId);
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
            String deleteQuery = "DELETE FROM spatii_comune WHERE id = ?";
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
