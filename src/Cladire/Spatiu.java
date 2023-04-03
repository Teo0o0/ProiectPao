/*
    * Superclasa pentru Spatiu, cu:
    * - suprafata
    * - cladire
 */
package Cladire;

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
}
