package Functionalitate;

import Chirias.Chirias;
import Cladire.Cladire;

import java.util.Date;

public class FunctionalitateAdministrator {
    public final Cladire c;

    public FunctionalitateAdministrator(Cladire c) {
        this.c = c;
    }
    public Chirias[] GetChiriasi(Date azi){
        /// returneaza lista chiriasilor ce au cel putin un spatiu in cladire
        return null;
    }
    public double GetFacturi(Date azi){
        /// returneaza totalul facturilor pe luna anterioara
        return 0;
    }
    public void AfisCumparaturi(Date azi){
        /// afiseaza produsele cumparate luna anterioara
    }
}
