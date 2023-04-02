package Cladire;

public class SpatiuInchiriat extends Spatiu {

    protected boolean ocupat;

    public SpatiuInchiriat(double suprafata, int idCladire, boolean ocupat) {
        super(suprafata, idCladire);
        this.ocupat = ocupat;
    }

    public boolean isOcupat() {
        return ocupat;
    }

    public void setOcupat(boolean ocupat) {
        this.ocupat = ocupat;
    }
}
