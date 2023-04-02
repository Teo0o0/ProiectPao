/*
    * Extinde Factura cu:
    * - tipReparatie
    * - persoana
    * - spatiu
    * Campurile nu pot fi modificate ulterior
 */
package Factura;

import Cladire.Spatiu;
import Persoana.PersoanaContact;

import java.util.Date;

public class FacturaReparatie extends Factura implements Cloneable{
    protected final String tipReparatie;
    protected final PersoanaContact persoana;
    protected final Cladire.Spatiu spatiu;

    public FacturaReparatie(Date dataEmitere, double pret, boolean platit, String tipReparatie, PersoanaContact persoana, Spatiu spatiu) throws CloneNotSupportedException {
        super(dataEmitere, pret, platit);
        this.tipReparatie = tipReparatie;
        this.persoana = persoana.clone();
        this.spatiu = (Spatiu) spatiu.clone();
    }

    public String getTipReparatie() {
        return tipReparatie;
    }

    public PersoanaContact getPersoana() throws CloneNotSupportedException {
        return persoana.clone();
    }

    public Spatiu getSpatiu() throws CloneNotSupportedException {
        return (Spatiu) spatiu.clone();
    }

    @Override
    public FacturaReparatie clone() throws CloneNotSupportedException {
        return new FacturaReparatie(this.dataEmitere, this.pret, this.platit, this.tipReparatie, this.persoana.clone(), (Spatiu) this.spatiu.clone());
    }
}
