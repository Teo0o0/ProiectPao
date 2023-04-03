/*
    * Spatiile care pot fi inchiriate, adauga:
    * - ocupat
 */
package Cladire;

public class SpatiuInchiriat extends Spatiu implements Cloneable {

    protected boolean ocupat;

    public SpatiuInchiriat(double suprafata, Cladire cladire, boolean ocupat) {
        super(suprafata, cladire);
        this.ocupat = ocupat;
    }

    public boolean isOcupat() {
        return ocupat;
    }

    public void setOcupat(boolean ocupat) {
        this.ocupat = ocupat;
    }

    @Override
    public SpatiuInchiriat clone() {
        return new SpatiuInchiriat(this.suprafata, this.cladire, this.ocupat);
    }
}
