/*
  * Antecedent :
  * - descriere : tipul, gravitate
  * - data adaugare
  * Nu pot fi modificate dupa adaugare, deci nu avem setteri si toate datele sunt pe privat si final
  * Si clasa poate fi setata pe final - nu urmeaza extinderi
 */
package model.chirias;

import service.Audit;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
public final class Antecedent{
    private final String descriere;
    private final Date data;
    private Integer dbId;
    public Antecedent(String descriere, Date data) {
        this.descriere = descriere;
        this.data = data;
        dbId = null;
    }
    public String getDescriere() {
        return descriere;
    }
    public Date getData() {
        return data;
    }

    public Antecedent Copie(){
        return new Antecedent(descriere, data);
    }

    public Integer getDbId() {
        return dbId;
    }

    public boolean Insert(Connection connection, Chirias chirias){
        try{
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO antecedente (descriere, data, chirias_id) VALUES (?, ?, ?)";
            PreparedStatement antecedentInsertStatement = connection.prepareStatement(insertQuery);
            antecedentInsertStatement.setString(1, descriere);
            antecedentInsertStatement.setDate(2, (java.sql.Date) data);
            antecedentInsertStatement.setInt(3, chirias.getDbId());
            antecedentInsertStatement.executeUpdate();
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

    public boolean Update(Connection connection, Chirias chirias){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE antecedente SET descriere = ?, data = ?, chirias_id = ? WHERE id = ?";
            PreparedStatement antecedentUpdateStatement = connection.prepareStatement(updateQuery);
            antecedentUpdateStatement.setString(1, descriere);
            antecedentUpdateStatement.setDate(2, (java.sql.Date) data);
            antecedentUpdateStatement.setInt(3, chirias.getDbId());
            antecedentUpdateStatement.setInt(4, dbId);
            antecedentUpdateStatement.executeUpdate();
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
            String deleteQuery = "DELETE FROM antecedente WHERE id = ?";
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
