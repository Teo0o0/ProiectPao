package service;

import model.chirias.Chirias;
import model.chirias.Contract;
import model.cladire.Cladire;
import model.cladire.Oras;
import model.cladire.SpatiuInchiriat;
import model.persoana.Angajat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sef {
    private static Sef instanta = null;
    public List<Cladire> cladiri;
    public List<Angajat> angajati;
    private ArrayList<String> comenzi = new ArrayList<>();

    private Sef(List<Cladire> cladiri, List<Angajat> angajati) {
        this.cladiri = new ArrayList<>(cladiri);
        this.angajati = new ArrayList<>(angajati);
        comenzi.add("1 - GetTotal");
        comenzi.add("2 - GetBalanta");
        comenzi.add("3 - GetFacturi");
        comenzi.add("4 - GetAngajati");
        comenzi.add("5 - InchiriereSpatiu");
        comenzi.add("6 - Stop");
    }
    public void Consulta(){
        System.out.println(comenzi);
    }

    public synchronized static Sef getInstance(List<Cladire> cladiri, List<Angajat> angajati) {
        if (instanta == null) {
            instanta = new Sef(cladiri, angajati);
        }
        return instanta;
    }

    public double GetTotal(Date azi) {
        // returneaza totalul de incasat pe chirii, fara facturi pe utilitati etc
        double total = 0;
        for (Cladire cladire : cladiri) {
            total += cladire.GetTotalIncasat(azi);
        }
        return total;
    }

    public double GetBalanta(Date azi) {
        // returneaza total_incasat - total_de_dat
        double totalIncasat = 0;
        double totalDeDat = 0;

        for (Cladire cladire : cladiri) {
            totalIncasat += cladire.GetTotalIncasat(azi);
            totalDeDat += cladire.GetTotalDeDat(azi);
        }

        return totalIncasat - totalDeDat;
    }

    public void GetAngajati(Date azi) {
        // afiseaza lista angajatilor
        System.out.println("List of employees:");
        for (Angajat angajat : angajati) {
            System.out.println(angajat.getNume());
        }
    }

    public double GetFacturi(Date azi) {
        // calculeaza totalul pe facturi
        double total = 0;
        for (Cladire cladire : cladiri) {
            total += cladire.GetTotalIncasat(azi);
        }
        return total;
    }

    public void InchiriereSpatiu(Date azi, Date dataFinal, Chirias chirias, SpatiuInchiriat spatiu, double suprafata, double pret, double rataPenalizare) throws CloneNotSupportedException {
        // realizeaza un contract cu chiriasul
        Contract contract = new Contract(azi, dataFinal, pret, rataPenalizare, spatiu, chirias);

        chirias.AdaugaContract(contract);
        spatiu.setOcupat(true);
    }

    public void AdaugaAngajat(Date azi, Angajat aNou) {
        // adauga un angajat
        if (azi.after(aNou.getDataAngajare())) {
            angajati.add(aNou);
        }
    }
}
