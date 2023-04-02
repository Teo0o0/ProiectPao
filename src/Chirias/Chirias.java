package Chirias;

import Persoana.PersoanaContact;

public class Chirias {
    protected String numeAfacere;
    protected String iban;
    protected PersoanaContact[] persoaneContact;
    protected Contract[] contracte;
    protected Antecedent[] antecedente;

    public Chirias(String numeAfacere, String iban, PersoanaContact[] persoaneContact, Contract[] contracte, Antecedent[] antecedente) {
        this.numeAfacere = numeAfacere;
        this.iban = iban;
        this.persoaneContact = persoaneContact;
        this.contracte = contracte;
        this.antecedente = antecedente;
    }

    public Chirias() {
    }

    public String getNumeAfacere() {
        return numeAfacere;
    }

    public void setNumeAfacere(String numeAfacere) {
        this.numeAfacere = numeAfacere;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public PersoanaContact[] getPersoaneContact() {
        return persoaneContact;
    }

    public void setPersoaneContact(PersoanaContact[] persoaneContact) {
        this.persoaneContact = persoaneContact;
    }

    public Contract[] getContracte() {
        return contracte;
    }

    public void setContracte(Contract[] contracte) {
        this.contracte = contracte;
    }

    public Antecedent[] getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(Antecedent[] antecedente) {
        this.antecedente = antecedente;
    }
}
