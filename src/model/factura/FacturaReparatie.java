/*
    * Extinde Factura cu:
    * - tipReparatie
    * - model.chirias.persoana
    * - spatiu
    * Campurile nu pot fi modificate ulterior
 */
package model.factura;

import model.cladire.Spatiu;
import model.persoana.PersoanaContact;

import java.util.ArrayList;
import java.util.Date;

public class FacturaReparatie extends Factura implements Cloneable{
    protected final String tipReparatie;
    protected final PersoanaContact persoana; // compozitie
    protected final Spatiu spatiu; // agregare

    public FacturaReparatie(Date dataEmitere, double pret, boolean platit, String tipReparatie, PersoanaContact persoana, Spatiu spatiu) throws CloneNotSupportedException {
        super(dataEmitere, pret, platit);
        this.tipReparatie = tipReparatie;
        this.persoana = persoana.clone();
        this.spatiu = spatiu;
    }

    public String getTipReparatie() {
        return tipReparatie;
    }

    public PersoanaContact getPersoana() throws CloneNotSupportedException {
        return persoana.clone();
    }

    public Spatiu getSpatiu() throws CloneNotSupportedException {
        return spatiu;
    }

    @Override
    public String toString() {
        return "FacturaReparatie{" +
                "tipReparatie='" + tipReparatie + '\'' +
                ", persoana=" + persoana +
                ", spatiu=" + spatiu +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    @Override
    public FacturaReparatie clone() throws CloneNotSupportedException {
        return new FacturaReparatie(this.dataEmitere, this.pret, this.platit, this.tipReparatie, this.persoana.clone(), this.spatiu);
    }

    @Override
    public ArrayList<Consumabil> getProduse() {
        return null;
    }
}