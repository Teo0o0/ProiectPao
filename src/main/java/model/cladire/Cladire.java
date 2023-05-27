package model.cladire;

import model.chirias.Contract;
import model.factura.Factura;
import java.util.ArrayList;
import java.util.Date;

public class Cladire implements Cloneable {
    protected String numeCladire;
    protected Oras oras;
    protected final int numarSpatii;
    protected final double suprafata;
    protected ArrayList<Spatiu> spatii;
    protected ArrayList<Factura> facturi;
    protected Contract[] contracte;

    public Cladire(String numeCladire, Oras oras, int numarSpatii, double suprafata, ArrayList<Spatiu> spatii, ArrayList<Factura> facturi, Contract[] contracte) {
        this.numeCladire = numeCladire;
        this.oras = oras;
        this.numarSpatii = numarSpatii;
        this.suprafata = suprafata;
        this.spatii = new ArrayList<>(spatii);
        this.facturi = new ArrayList<>(facturi);
        this.contracte = contracte;
    }

    public String getNumeCladire() {
        return numeCladire;
    }

    public Oras getOras() throws CloneNotSupportedException {
        return oras.clone();
    }

    public int getNumarSpatii() {
        return numarSpatii;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public ArrayList<Spatiu> getSpatii() {
        return new ArrayList<>(spatii);
    }

    public ArrayList<Factura> getFacturi() {
        return new ArrayList<>(facturi);
    }

    @Override
    public Cladire clone() throws CloneNotSupportedException {
        return new Cladire(this.numeCladire, this.oras.clone(), this.numarSpatii, this.suprafata, new ArrayList<>(this.spatii), new ArrayList<>(this.facturi), this.contracte);
    }

    public void AdaugaFactura(Factura facturaNoua) throws CloneNotSupportedException {
        facturi.add(facturaNoua.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cladire other) {
            return this.numeCladire.equals(other.numeCladire) && this.oras.equals(other.oras);
        }
        return false;
    }

    public double GetTotalIncasat(Date azi) {
        double totalIncasat = 0.0;

        for (Contract contract : contracte) {
            if (contract.getDataSemnare().before(azi) && (contract.getDataFinalizare() == null || contract.getDataFinalizare().after(azi))) {
                SpatiuInchiriat spatiu = null;
                try {
                    spatiu = contract.getSpatiu().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                if (spatiu != null && spatiu.isOcupat()) {
                    totalIncasat += contract.getPretMP() * spatiu.getSuprafata();
                }
            }
        }

        return totalIncasat;    }

    public double GetTotalDeDat(Date azi) {
        double totalDeDat = 0.0;

        for (Factura factura : facturi) {
            if (factura.getDataEmitere().getMonth() == azi.getMonth()) {
                totalDeDat += factura.getPret();
            }
        }

        return totalDeDat;
    }

}
