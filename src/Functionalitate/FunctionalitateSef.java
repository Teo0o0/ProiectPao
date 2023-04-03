package Functionalitate;

import java.util.Date;

public class FunctionalitateSef {
    private static FunctionalitateSef instanta = null;
    private Cladire.Cladire[] cladiri; // agregare
    private Persoana.Angajat[] angajati; // compozitie
    private FunctionalitateSef(Cladire.Cladire[] cladiri, Persoana.Angajat[] angajati){
        this.cladiri = new Cladire.Cladire[cladiri.length];
        System.arraycopy(cladiri, 0, this.cladiri, 0, cladiri.length);
        this.angajati = angajati.clone();
    }
    public static FunctionalitateSef getInstance(Cladire.Cladire[] cladiri, Persoana.Angajat[] angajati){
        if(instanta == null) {
            instanta = new FunctionalitateSef(cladiri, angajati);

        }
        return instanta;
    }
    public double GetTotal(Date azi){
        /// returneaza totalul de incasat pe chirii, fara facturi pe utilitati etc
        return 0;
    }
    public double GetBalanta(Date azi){
        /// returneaza total_incasat - total_de_dat
        return 0;
    }
    public void GetAngajati(Date azi){
        /// afiseaza lista angajatilor
    }
    public double GetFacturi(Date azi){
        /// calculeazatotalul pe facturi
        return 0;
    }
    public void InchiriereSpatiu(Date azi, Date dataFinal, Chirias.Chirias chirias, Cladire.Oras oras, double suprafata, double pret, double rataPenalizare){
        /// realizeza un contract cu chiriasul
    }
    public void AdaugaAngajat(Date azi, Persoana.Angajat aNou){
        /// adauga un angajat
    }
}
