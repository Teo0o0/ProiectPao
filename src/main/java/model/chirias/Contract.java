/*
    * Contract de inchiriere:
    * - dataSemnare
    * - dataFinalizare
    * - pretMP
    * - rataPenalizare
    * - spatiu
    * Nu pot fi modificate ulterior, cu exceptia dataFinalizare
 */
package model.chirias;

import model.cladire.SpatiuInchiriat;
import service.Service;

import java.sql.*;
import java.util.Date;

public class Contract{
    protected final Date dataSemnare;
    protected Date dataFinalizare;
    protected final double pretMP;
    protected final double rataPenalizare;
    protected SpatiuInchiriat spatiu; // agregare
    protected Chirias chirias;
    private Integer dbId = null;

    public Contract(Date dataSemnare, Date dataFinalizare, double pretMP, double rataPenalizare, SpatiuInchiriat spatiu, Chirias chirias) {
        this.dataSemnare = dataSemnare;
        this.dataFinalizare = dataFinalizare;
        this.pretMP = pretMP;
        this.rataPenalizare = rataPenalizare;
        this.spatiu = spatiu;
        this.chirias = chirias;
    }

    public Contract(Date dataSemnare, Date dataFinalizare, double pretMP, double rataPenalizare) {
        this.dataSemnare = dataSemnare;
        this.dataFinalizare = dataFinalizare;
        this.pretMP = pretMP;
        this.rataPenalizare = rataPenalizare;
    }

    public void setSpatiu(SpatiuInchiriat spatiu) {
        this.spatiu = spatiu;
    }

    public void setChirias(Chirias chirias) {
        this.chirias = chirias;
    }

    public Date getDataSemnare() {
        return dataSemnare;
    }

    public Date getDataFinalizare() {
        return dataFinalizare;
    }

    public double getPretMP() {
        return pretMP;
    }

    public double getRataPenalizare() {
        return rataPenalizare;
    }

    public SpatiuInchiriat getSpatiu() throws CloneNotSupportedException {
        return spatiu.clone();
    }

    public Chirias getChirias() throws CloneNotSupportedException {
        return chirias.clone();
    }

    public void setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "dataSemnare=" + dataSemnare +
                ", dataFinalizare=" + dataFinalizare +
                ", pretMP=" + pretMP +
                ", spatiu=" + spatiu.getSuprafata() +
                '}';
    }

    public Contract Copie() throws CloneNotSupportedException {
        return new Contract(this.dataSemnare, this.dataFinalizare, this.pretMP, this.rataPenalizare, this.spatiu.clone(), this.chirias.clone());

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
            String insertQuery = "INSERT INTO contracte (data_semnare, " +
                    "data_finalizare, " +
                    "pret_mp, " +
                    "rata_penalizare, " +
                    "spatiu_inchiriat_id, " +
                    "chirias_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setDate(1, (java.sql.Date) dataSemnare);
            insertStatement.setDate(2, (java.sql.Date) dataFinalizare);
            insertStatement.setDouble(3, pretMP);
            insertStatement.setDouble(4, rataPenalizare);
            insertStatement.setInt(5, spatiu.getDbId());
            insertStatement.setInt(6, chirias.getDbId());
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
            String updateQuery = "UPDATE contracte SET " +
                    "data_semnare = ?, " +
                    "data_finalizare = ?, " +
                    "pret_mp = ?, " +
                    "rata_penalizare = ?, " +
                    "spatiu_inchiriat_id = ?, " +
                    "chirias_id = ? " +
                    "WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDate(1, (java.sql.Date) dataSemnare);
            updateStatement.setDate(2, (java.sql.Date) dataFinalizare);
            updateStatement.setDouble(3, pretMP);
            updateStatement.setDouble(4, rataPenalizare);
            updateStatement.setInt(5, spatiu.getDbId());
            updateStatement.setInt(6, chirias.getDbId());
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
            String deleteQuery = "DELETE FROM contracte WHERE id = ?";
            PreparedStatement antecedentDeleteStatement = connection.prepareStatement(deleteQuery);
            antecedentDeleteStatement.setInt(1, dbId);
            antecedentDeleteStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
