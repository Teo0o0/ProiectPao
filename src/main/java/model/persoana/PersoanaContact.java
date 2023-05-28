/*
    * Extinde clasa Persoana, cu:
    * - oras
    * Folosita pentru persoanele de contact date de chiriasi si reparatii
 */
package model.persoana;
import model.chirias.Chirias;
import model.cladire.Oras;
import service.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;

public class PersoanaContact extends Persoana implements Cloneable {

  protected Oras oras; // agregare
    private Integer dbId = null;

    public PersoanaContact(String nume, int varsta, String email, String numarTelefon, Oras oras) {
        super(nume, varsta, email, numarTelefon);
        this.oras = oras;
    }

    public Oras getOras() {
        return oras;
    }

    @Override
    public PersoanaContact clone() {
        Oras o = this.oras;
        PersoanaContact c = new PersoanaContact(this.nume, this.varsta, this.email, this.numarTelefon, o);
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersoanaContact){
            return this.oras.equals(((PersoanaContact) obj).oras) && this.nume.equals(((PersoanaContact) obj).getNume());
        }
        return false;
    }

    public Integer getDbId(Chirias chirias){
        if(dbId == null){
            Insert(Service.connection, chirias);
        }
        return dbId;
    }
    public Integer getDbId() {
        if(dbId == null){
            Insert(Service.connection, null);
        }
        return dbId;
    }

    @Override
    public boolean Insert(Connection connection) {
        return false;
    }

    @Override
    public boolean Update(Connection connection) {
        return false;
    }

    public boolean Insert(Connection connection, Chirias chirias){
        try{
            Integer chirias_id = 0;
            if(chirias != null) chirias_id = chirias.getDbId();
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO persoane_contact (nume, " +
                    "varsta, " +
                    "email, " +
                    "numar_telefon, " +
                    "oras_id, " +
                    "chirias_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, nume);
            insertStatement.setInt(2, varsta);
            insertStatement.setString(3, email);
            insertStatement.setString(4, numarTelefon);
            insertStatement.setInt(5, oras.getDbId());
            insertStatement.setInt(6, chirias_id);
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

    public boolean Update(Connection connection, Chirias chirias){
        try{
            Integer chirias_id = 0;
            if(chirias != null) chirias_id = chirias.getDbId();
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE persoane_contact SET " +
                    "nume = ?, " +
                    "varsta = ?, " +
                    "email = ?, " +
                    "numar_telefon = ?, " +
                    "oras_id = ?, " +
                    "chirias_id = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, nume);
            updateStatement.setInt(2, varsta);
            updateStatement.setString(3, email);
            updateStatement.setString(4, numarTelefon);
            updateStatement.setInt(5, oras.getDbId());
            updateStatement.setInt(6, chirias_id);
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
            String deleteQuery = "DELETE FROM persoane_contact WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
