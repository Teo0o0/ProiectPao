/*
    * Cladire:
    * - numeCladire
    * - oras
    * - numarSpatii
    * - suprafata
    * - spatii
    * - facturi
    * Se pot:
    * - adauga facturi
 */
package Cladire;


public class Cladire implements Cloneable {
    protected String numeCladire;
    protected Oras oras;
    protected final int numarSpatii;
    protected final double suprafata;
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

    public Oras getOras() throws CloneNotSupportedException {
        return oras.clone();
    }

    public int getNumarSpatii() {
        return numarSpatii;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public Spatiu[] getSpatii() {
        return spatii.clone();
    }

    public Factura.Factura[] getFacturi() {
        return facturi.clone();
    }

    @Override
    public Cladire clone() throws CloneNotSupportedException {
        return new Cladire(this.numeCladire, this.oras.clone(), this.numarSpatii, this.suprafata, this.spatii.clone(), this.facturi.clone());
    }
    public void AdaugaFactura(Factura.Factura facturaNoua) throws CloneNotSupportedException {
        Factura.Factura f[];
        f = new Factura.Factura[this.facturi.length + 1];
        for(int i = 0; i < facturi.length; i++){
            f[i] = facturi[i];
        }
        f[facturi.length] = facturaNoua.clone();
        facturi = f;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cladire){
            return this.numeCladire.equals(((Cladire) obj).numeCladire) && this.oras.equals(((Cladire) obj).oras);
        }
        return false;
    }
}
