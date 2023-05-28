/*
    * Superclasa pentru facturi:
    * - dataEmitere
    * - pret
    * - platit
    * Doar platit poate fi modificat
 */
package model.factura;

import model.cladire.Cladire;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public abstract class Factura implements Cloneable{
    protected final Date dataEmitere;
    protected final double pret;
    protected boolean platit;
    protected Cladire cladire;

    public Factura(Date dataEmitere, double pret, boolean platit, Cladire cladire) {
        this.dataEmitere = dataEmitere;
        this.pret = pret;
        this.platit = platit;
        this.cladire = cladire;
    }

    public Factura(Date dataEmitere, double pret, boolean platit) {
        this.dataEmitere = dataEmitere;
        this.pret = pret;
        this.platit = platit;
    }

    public void setCladire(Cladire cladire) {
        this.cladire = cladire;
    }

    public Date getDataEmitere() {
        return dataEmitere;
    }

    public boolean isPlatit() {
        return platit;
    }

    public void setPlatit(boolean platit) {
        this.platit = platit;
    }

    public double getPret() {
        return pret;
    }

    @Override
    public Factura clone() throws CloneNotSupportedException {
        return this.clone();
    }

    abstract public ArrayList<Consumabil> getProduse();

    @Override
    public String toString() {
        return "Factura{" +
                "dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }
    abstract Integer getDbId();
    abstract public boolean Insert(Connection connection);
    abstract public boolean Update(Connection connection);
    abstract public boolean Delete(Connection connection);
}
