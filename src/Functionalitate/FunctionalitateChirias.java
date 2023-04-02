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
        return 0;
    }
    public double GetPenalizare(Date azi, Contract c){
        return 0;
    }
    public double GetTotal(Date azi){
        return 0;
    }
    public double GetReparatii(Date azi){
        return 0;
    }
}
