package Factura;

import java.util.Date;

public class FacturaUtilitate extends Factura{

    protected Date dataScadenta;
    protected String tipUtilitate;
    public FacturaUtilitate(Date dataEmitere, double pret, boolean platit, Date dataScadenta, String tipUtilitate) {
        super(dataEmitere, pret, platit);
        this.dataScadenta = dataScadenta;
        this.tipUtilitate = tipUtilitate;
    }

    public Date getDataScadenta() {
        return dataScadenta;
    }

    public String getTipUtilitate() {
        return tipUtilitate;
    }
}
