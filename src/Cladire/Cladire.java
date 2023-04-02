package Cladire;

public class Cladire {
    protected String numeCladire;
    protected Oras oras;
    protected int numarSpatii;
    protected double suprafata;
    protected Spatiu[] spatii;
    protected Factura.Factura[] facturi;

    public Cladire(String numeCladire, Oras oras, int numarSpatii, double suprafata, Spatiu[] spatii, Factura.Factura[] facturi) {
        this.numeCladire = numeCladire;
        this.oras = oras;
        this.numarSpatii = numarSpatii;
        this.suprafata = suprafata;
        this.spatii = spatii;
        this.facturi = facturi;
    }

    public String getNumeCladire() {
        return numeCladire;
    }

    public Oras getOras() {
        return oras;
    }

    public int getNumarSpatii() {
        return numarSpatii;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public Spatiu[] getSpatii() {
        return spatii;
    }

    public Factura.Factura[] getFacturi() {
        return facturi;
    }
}
