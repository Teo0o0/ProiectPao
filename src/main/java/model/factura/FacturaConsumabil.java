package model.factura;

import java.util.ArrayList;
import java.util.Date;

public class FacturaConsumabil extends Factura implements Cloneable {
    protected ArrayList<Consumabil> produse;

    public FacturaConsumabil(Date dataEmitere, double pret, boolean platit, ArrayList<Consumabil> produse) {
        super(dataEmitere, pret, platit);
        this.produse = new ArrayList<>(produse);
    }

    @Override
    public ArrayList<Consumabil> getProduse() {
        return new ArrayList<>(produse);
    }

    @Override
    public String toString() {
        return "FacturaConsumabil{" +
                "produse=" + produse +
                ", dataEmitere=" + dataEmitere +
                ", pret=" + pret +
                ", platit=" + platit +
                '}';
    }

    @Override
    public FacturaConsumabil clone() throws CloneNotSupportedException {
        return new FacturaConsumabil(this.dataEmitere, this.pret, this.platit, new ArrayList<>(this.produse));
    }
}
