package Cladire;

public class SpatiuComun extends Spatiu{
    protected String tipSpatiu;

    public SpatiuComun(double suprafata, Cladire Cladire, String tipSpatiu) {
        super(suprafata, Cladire);
        this.tipSpatiu = tipSpatiu;
    }

    public String getTipSpatiu() {
        return tipSpatiu;
    }
}
