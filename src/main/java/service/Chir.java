package service;
import model.chirias.Contract;
import model.factura.Factura;

import java.util.Date;

public class Chir {
    public final model.chirias.Chirias chirias;
    public Chir(model.chirias.Chirias chirias) {
        this.chirias = chirias;
    }
    public double GetFacturaCurenta(Date azi, Contract c) throws CloneNotSupportedException {
        /// returneaza totalul de plata pe luna anterioara si afiseaza detaliile
        double total = c.getPretMP() * c.getSpatiu().getSuprafata();
        System.out.println(c);
        return total;

    }
    public double GetTotal(Date azi) throws CloneNotSupportedException {
        /// returneaza totalul, pe toate spatiile inchiriate
        double total = 0;
        for (Contract contract : chirias.getContracte()) {
            if (contract.getDataFinalizare().after(azi) & contract.getDataSemnare().before(azi)) {
                total += contract.getSpatiu().getSuprafata() * contract.getPretMP();
            }
        }
        return total;
    }
    public double GetReparatii(Date azi) throws CloneNotSupportedException {
        /// returneaza totalul pe reparatii pe luna anterioara
        double totalReparatii = 0;

        for (Contract contract : chirias.getContracte()) {
            if (contract.getDataFinalizare().before(azi)) {

                totalReparatii += contract.getSpatiu().getReparatii();
            }
        }

        return totalReparatii;
    }
}
