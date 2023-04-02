/*
    * Factura emisa la cumpararea unei liste de produse
    * Extinde Factura, cu:
    * - produse
 */
package Factura;

import java.util.Date;

public class FacturaConsumabil extends Factura implements Cloneable{
    protected Consumabil[] produse;

    public FacturaConsumabil(Date dataEmitere, double pret, boolean platit, Consumabil[] produse) {
        super(dataEmitere, pret, platit);
        this.produse = produse.clone();
    }

    public Consumabil[] getProduse() {
        return produse;
    }

    @Override
    public FacturaConsumabil clone() throws CloneNotSupportedException {
        return new FacturaConsumabil(this.dataEmitere, this.pret, this.platit, this.produse.clone());
    }
}
