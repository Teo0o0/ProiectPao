/*
    * Extinde clasa Factura cu:
    * - dataScadenta
    * - tipUtilitate
    * Nu pot fi modificate ulterior
 */
package model.factura;

import java.util.ArrayList;
import java.util.Date;

public class FacturaUtilitate extends Factura{

    protected final Date dataScadenta;
    protected final String tipUtilitate;
    public FacturaUtilitate(Date dataEmitere, double pret, boolean platit, Date dataScadenta, String tipUtilitate) {
        super(dataEmitere, pret, platit);
        this.dataScadenta = dataScadenta;
        this.tipUtilitate = tipUtilitate;
    }

    @Override
    public String toString() {
        return "FacturaUtilitate{" +
                "dataScadenta=" + dataScadenta +
                ", tipUtilitate='" + tipUtilitate + '\'' +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    public Date getDataScadenta() {
        return dataScadenta;
    }

    public String getTipUtilitate() {
        return tipUtilitate;
    }

    @Override
    public ArrayList<Consumabil> getProduse() {
        return null;
    }
}
