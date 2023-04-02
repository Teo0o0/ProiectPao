package Cladire;

public class SpatiuComun extends Spatiu{
    protected String tipSpatiu;

    public SpatiuComun(double suprafata, int idCladire, String tipSpatiu) {
        super(suprafata, idCladire);
        this.tipSpatiu = tipSpatiu;
    }

    public String getTipSpatiu() {
        return tipSpatiu;
    }
}
