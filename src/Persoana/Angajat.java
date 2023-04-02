package Persoana;

import java.util.Date;

public class Angajat extends Persoana {
    protected String tip;
    protected double salariu;
    protected String adresa;
    protected Date dataAngajare;
    protected Date dataDemisie;
    public Angajat(String nume, int varsta, String email, String numarTelefon, String tip, double salariu, String adresa, Date dataAngajare, Date dataDemisie) {
        super(nume, varsta, email, numarTelefon);
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

    public void setDataAngajare(Date dataAngajare) {
        this.dataAngajare = dataAngajare;
    }

    public Date getDataDemisie() {
        return dataDemisie;
    }

    public void setDataDemisie(Date dataDemisie) {
        this.dataDemisie = dataDemisie;
    }
}
