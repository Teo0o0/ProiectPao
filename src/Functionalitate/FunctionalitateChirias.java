package Functionalitate;
import Chirias.Chirias;
import Chirias.Contract;
import Cladire.SpatiuInchiriat;

import java.util.Date;

public class FunctionalitateChirias {
    public final Chirias chirias;
    public FunctionalitateChirias(Chirias chirias) {
        this.chirias = chirias;
    }
    public double GetFacturaCurenta(Date azi, Contract c){
        /// returneaza totalul de plata pe luna anterioara si afiseaza detaliile
        return 0;
    }
    public double GetPenalizare(Date azi, Contract c){
        /// returneaza costul fcturii cu penalizare
        return 0;
    }
    public double GetTotal(Date azi){
        /// returneaza totalul, pe toate spatiile inchiriate
        return 0;
    }
    public double GetReparatii(Date azi){
        /// returneaza totalul pe reparatii pe luna anterioara
        return 0;
    }
}
