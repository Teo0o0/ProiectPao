/*
    * Spatiile care pot fi inchiriate, adauga:
    * - ocupat
 */
package model.cladire;

import model.chirias.Contract;
import service.Audit;
import service.Service;

import java.io.IOException;
import java.sql.*;

public class SpatiuInchiriat extends Spatiu implements Cloneable {

    protected boolean ocupat;
    protected Contract contract;
    private Integer dbId = null;

    public SpatiuInchiriat(double suprafata, Cladire cladire, boolean ocupat, Contract contract) {
        super(suprafata, cladire);
        this.ocupat = ocupat;
        this.contract = contract;
    }

    public SpatiuInchiriat(double suprafata, Cladire cladire, boolean ocupat) {
        super(suprafata, cladire);
        this.ocupat = ocupat;
    }

    public boolean isOcupat() {
        return ocupat;
    }

    public void setOcupat(boolean ocupat, Contract contract) {
        this.ocupat = ocupat;
        this.contract = contract;
    }

    public void setOcupat(boolean ocupat){
        this.ocupat = ocupat;
    }

    @Override
    public SpatiuInchiriat clone() throws CloneNotSupportedException {
        return new SpatiuInchiriat(this.suprafata, this.cladire, this.ocupat, this.contract.Copie());
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    public Integer getDbId() {
        if(dbId == null){
            Insert(Service.connection);
        }
        return dbId;
    }

    public boolean Insert(Connection connection){
        try{
            int contract_id = -1;
            if(contract != null){
                contract_id = contract.getDbId();
            }
            Statement statement = connection.createStatement();
            String insertQuery = "INSERT INTO spatii_inchiriate (" +
                    "suprafata, " +
                    "cladire_id, " +
                    "contracte_id, " +
                    "ocupat) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDouble(1, suprafata);
            insertStatement.setInt(2, cladire.getDbId());
            insertStatement.setInt(3, contract_id);
            insertStatement.setBoolean(4, ocupat);
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
            int contract_id = -1;
            if(contract != null){
                contract_id = contract.getDbId();
            }
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE spatii_inchiriate SET " +
                    "suprafata = ?, " +
                    "cladire_id = ?, " +
                    "contracte_id = ?, " +
                    "ocupat = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, suprafata);
            updateStatement.setInt(2, cladire.getDbId());
            updateStatement.setInt(3, contract_id);
            updateStatement.setBoolean(4, ocupat);
            updateStatement.setInt(5, dbId);
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
            String deleteQuery = "DELETE FROM spatii_inchiriate WHERE id = ?";
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
