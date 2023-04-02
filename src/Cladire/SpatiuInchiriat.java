package Cladire;

public class SpatiuInchiriat extends Spatiu {

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
}
