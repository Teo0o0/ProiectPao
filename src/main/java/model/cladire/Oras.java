/*
    * Oras:
    * - numeOras
    * - numarCladiri
    * - numeTara
    * Numai numarCladiri poate fi modificat
 */
package model.cladire;

import service.Service;

import java.sql.*;

public class Oras implements Cloneable {
    private final String numeOras;
    private int numarCladiri;
    private final String numeTara;
    private Integer dbId = null;

    public Oras(String numeOras, int numarCladiri, String numeTara) {
        this.numeOras = numeOras;
        this.numarCladiri = numarCladiri;
        this.numeTara = numeTara;
    }

    public int getNumarCladiri() {
        return numarCladiri;
    }

    public void setNumarCladiri(int numarCladiri) {
        this.numarCladiri = numarCladiri;
    }

    public String getNumeOras() {
        return numeOras;
    }

    public String getNumeTara() {
        return numeTara;
    }

    @Override
    public Oras clone() throws CloneNotSupportedException {
        return new Oras(this.numeOras, this.numarCladiri, this.numeTara);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Oras) {
            return (this.numeOras.equals(((Oras) obj).getNumeOras()) && this.numeTara.equals(((Oras) obj).getNumeTara()));
        }
        return false;
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
            String insertQuery = "INSERT INTO orase (" +
                    "nume_oras, " +
                    "numar_cladiri, " +
                    "nume_tara) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, numeOras);
            insertStatement.setInt(2, numarCladiri);
            insertStatement.setString(3, numeTara);
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
            String updateQuery = "UPDATE orase SET " +
                    "nume_oras = ?, " +
                    "numar_cladiri = ?, " +
                    "nume_tara = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, numeOras);
            updateStatement.setInt(2, numarCladiri);
            updateStatement.setString(3, numeTara);
            updateStatement.setInt(4, dbId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean Delete(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String deleteQuery = "DELETE FROM orase WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
