/*
    * Superclasa pentru Spatiu, cu:
    * - suprafata
    * - cladire
 */
package Cladire;

public abstract class Spatiu implements Cloneable{
    protected double suprafata;
    protected Cladire cladire;

    public Spatiu(double suprafata, Cladire cladire) {
        this.suprafata = suprafata;
        this.cladire = cladire;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public Cladire getCladire() throws CloneNotSupportedException {
        return cladire.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
