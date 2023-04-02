package Factura;

import Cladire.Spatiu;
import Persoana.PersoanaContact;

import java.util.Date;

public class FacturaReparatie extends Factura{
    protected String tipReparatie;
    protected PersoanaContact persoana;

    protected Cladire.Spatiu spatiu;

    public FacturaReparatie(Date dataEmitere, double pret, boolean platit, String tipReparatie, PersoanaContact persoana, Cladire.Spatiu spatiu) {
        super(dataEmitere, pret, platit);
        this.tipReparatie = tipReparatie;
        this.persoana = persoana;
        this.spatiu = spatiu;
    }

    public String getTipReparatie() {
        return tipReparatie;
    }

    public PersoanaContact getPersoana() {
        return persoana;
    }

    public Spatiu getSpatiu() {
        return spatiu;
    }
}
