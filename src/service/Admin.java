package service;

import model.chirias.Chirias;
import model.cladire.Cladire;
import model.cladire.Spatiu;
import model.cladire.SpatiuInchiriat;
import model.factura.Factura;

import java.util.ArrayList;
import java.util.Date;

public class Admin {
    public final Cladire c;

    public Admin(Cladire c) {
        this.c = c;
    }
    public Chirias[] GetChiriasi(Date azi) throws CloneNotSupportedException {
        /// returneaza lista chiriasilor ce au cel putin un spatiu in model.chirias.cladire
        ArrayList<Chirias> chiriasi = new ArrayList<>();
        for (Spatiu spatiu : c.getSpatii()) {
            if (spatiu.getContract().getDataFinalizare().after(azi)) {
                chiriasi.add(spatiu.getContract().getChirias());
            }
        }
        return chiriasi.toArray(new Chirias[0]);
    }
    public double GetFacturi(Date azi){
        /// returneaza totalul facturilor pe luna anterioara
        double totalFacturi = 0;
        for (Factura factura : c.getFacturi()) {
            if (factura.getDataEmitere().before(azi)) {
                totalFacturi += factura.getPret();
            }
        }
        return totalFacturi;
    }
    public void AfisCumparaturi(Date azi){
        /// afiseaza produsele cumparate luna anterioara
        System.out.println("Produsele cumparate luna anterioara:");
        for (Factura factura : c.getFacturi()) {
            if (factura.getDataEmitere().before(azi)) {
                System.out.println(factura.getProduse());
            }
        }
    }
}
