package Factura;

public class Consumabil {
    protected String numeConsumabil;
    protected double pret;
    protected int cantitate;

    public Consumabil(String numeConsumabil, double pret, int cantitate) {
        this.numeConsumabil = numeConsumabil;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public String getNumeConsumabil() {
        return numeConsumabil;
    }

    public double getPret() {
        return pret;
    }

    public int getCantitate() {
        return cantitate;
    }
}