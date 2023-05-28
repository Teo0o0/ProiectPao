package model.factura;

import model.cladire.Cladire;
import service.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FacturaConsumabil extends Factura implements Cloneable {
    private Integer dbId = null;
    protected ArrayList<Consumabil> produse;

    public FacturaConsumabil(Date dataEmitere, double pret, boolean platit, ArrayList<Consumabil> produse) {
        super(dataEmitere, pret, platit);
        this.produse = produse;
    }

    public FacturaConsumabil(Date dataEmitere, double pret, boolean platit, Cladire cladire, ArrayList<Consumabil> produse) {
        super(dataEmitere, pret, platit, cladire);
        this.produse = produse;
    }

    @Override
    public ArrayList<Consumabil> getProduse() {
        return new ArrayList<>(produse);
    }

    @Override
    public String toString() {
        return "FacturaConsumabil{" +
                "produse=" + produse +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    @Override
    public FacturaConsumabil clone() throws CloneNotSupportedException {
        return new FacturaConsumabil(this.dataEmitere, this.pret, this.platit, new ArrayList<>(this.produse));
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
            String insertQuery = "INSERT INTO facturi_consumabile ( " +
                    "data_emitere, " +
                    "pret, " +
                    "platit, " +
                    "cladire_id) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDate(1, (java.sql.Date) dataEmitere);
            insertStatement.setDouble(2, pret);
            insertStatement.setBoolean(3, platit);
            insertStatement.setInt(4, cladire.getDbId());
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

            String consumabilInsertQuery = "INSERT INTO consumabil_factura(" +
                    "factura_id, " +
                    "consumabil_id) VALUES (?, ?)";
            PreparedStatement consumabilInsertStatement = connection.prepareStatement(consumabilInsertQuery);
            for(Consumabil consumabil:produse){
                consumabilInsertStatement.setInt(1, dbId);
                consumabilInsertStatement.setInt(2, consumabil.getDbId());
                consumabilInsertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean Update(Connection connection){
        try{
            Statement statement = connection.createStatement();
            String updateQuery = "UPDATE facturi_consumabile SET " +
                    "data_emitere = ?, " +
                    "pret = ?, " +
                    "platit = ?, " +
                    "cladire_id = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDate(1, (java.sql.Date) dataEmitere);
            updateStatement.setDouble(2, pret);
            updateStatement.setBoolean(3, platit);
            updateStatement.setInt(4, cladire.getDbId());
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
            String deleteQuery = "DELETE FROM facturi_consumabile WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
