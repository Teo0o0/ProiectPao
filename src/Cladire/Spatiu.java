package Cladire;

public abstract class Spatiu {
    protected double suprafata;
    protected Cladire cladire;

    public Spatiu(double suprafata, Cladire cladire) {
        this.suprafata = suprafata;
        this.cladire = cladire;
    }
}
