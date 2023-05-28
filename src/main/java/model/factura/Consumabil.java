/*
    * Produusele folosite, caracterizate de:
    * - numeConsumabil
    * - pret
    * - cantitate
    * , la momentul cumpararii
    * Acestea nu pot fi modificate
 */
package model.factura;

import service.Service;

import java.sql.*;

public class Consumabil {
    protected final String numeConsumabil;
    protected double pret;
    private Integer dbId = null;

    public Consumabil(String numeConsumabil, double pret) {
        this.numeConsumabil = numeConsumabil;
        this.pret = pret;
    }

    public String getNumeConsumabil() {
        return numeConsumabil;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
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
            String insertQuery = "INSERT INTO consumabile (" +
                    "nume_consumabil, " +
                    "pret) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDouble(2, pret);
            insertStatement.setString(1, numeConsumabil);
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
            String updateQuery = "UPDATE cunsumabile SET " +
                    "nume_consumabil = ?, " +
                    "pret = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(2, pret);
            updateStatement.setString(1, numeConsumabil);
            updateStatement.setInt(3, dbId);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM consumabile WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
