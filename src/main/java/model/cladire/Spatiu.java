/*
    * Superclasa pentru Spatiu, cu:
    * - suprafata
    * - model.chirias.cladire
 */
package model.cladire;

import model.chirias.Contract;

import java.sql.Connection;

public abstract class Spatiu implements Cloneable{
    protected double suprafata;
    protected Cladire cladire; // agregare

    public Spatiu(double suprafata, Cladire cladire) {
        this.suprafata = suprafata;
        this.cladire = cladire;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public Cladire getCladire(){
        return cladire;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    abstract public Contract getContract();

    public double getReparatii() {
        return 0;
    }
    abstract Integer getDbId();
    abstract public boolean Insert(Connection connection);
    abstract public boolean Update(Connection connection);
    abstract public boolean Delete(Connection connection);

}
