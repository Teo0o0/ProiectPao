/*
    * Extinde clasa Persoana, la care adauga:
    * - tip
    * - salariu
    * - adresa
    * - dataAngajare
    * - dataDemisie
    * Toate campurile, cu exceptia datei de Angajare, pot fi modificate pe parcurs
 */
package model.persoana;

import java.util.Date;
import java.util.Objects;

public class Angajat extends Persoana implements Cloneable {
    protected String tip;
    protected double salariu;
    protected String adresa;
    protected final Date dataAngajare;
    protected Date dataDemisie;
    private boolean ValidareTip(String tip){
        if(Objects.equals(tip, "administrator") || Objects.equals(tip, "curateniie") || Objects.equals(tip, "paza")) return true;
        return false;
    }
    public Angajat(String nume, int varsta, String email, String numarTelefon, String tip, double salariu, String adresa, Date dataAngajare, Date dataDemisie) {
        super(nume, varsta, email, numarTelefon);
        boolean ok = ValidareTip(tip);
        if(!ok) throw new IllegalArgumentException("Tip invalid");
        this.tip = tip;
        this.salariu = salariu;
        this.adresa = adresa;
        this.dataAngajare = dataAngajare;
        this.dataDemisie = dataDemisie;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Date getDataAngajare() {
        return dataAngajare;
    }

    public Date getDataDemisie() {
        return dataDemisie;
    }

    public void setDataDemisie(Date dataDemisie) {
        this.dataDemisie = dataDemisie;
    }

    @Override
    protected Angajat clone() throws CloneNotSupportedException {
        return new Angajat(this.nume, this.varsta, this.email, this.numarTelefon, this.tip, this.salariu, this.adresa, this.dataAngajare, this.dataDemisie);
    }
}
