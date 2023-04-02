package Factura;

import java.util.Date;

public class FacturaConsumabil extends Factura{
    protected Consumabil[] produse;

    public FacturaConsumabil(Date dataEmitere, double pret, boolean platit, Consumabil[] produse) {
        super(dataEmitere, pret, platit);
        this.produse = produse;
    }

    public Consumabil[] getProduse() {
        return produse;
    }
}
