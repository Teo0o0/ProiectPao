package Factura;

import java.util.Date;

public abstract class Factura {
    protected Date dataEmitere;
    protected double pret;
    protected boolean platit;

    public Factura(Date dataEmitere, double pret, boolean platit) {
        this.dataEmitere = dataEmitere;
        this.pret = pret;
        this.platit = platit;
    }

    public Date getDataEmitere() {
        return dataEmitere;
    }

    public boolean isPlatit() {
        return platit;
    }

    public void setPlatit(boolean platit) {
        this.platit = platit;
    }

    public double getPret() {
        return pret;
    }
}
