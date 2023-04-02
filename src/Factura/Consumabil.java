/*
    * Produusele folosite, caracterizate de:
    * - numeConsumabil
    * - pret
    * - cantitate
    * , la momentul cumpararii
    * Acestea nu pot fi modificate
 */
package Factura;

public class Consumabil {
    protected final String numeConsumabil;
    protected final double pret;
    protected final int cantitate;

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
