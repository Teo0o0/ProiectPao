/*
  * Chirias:
  * - numeAfacere - pentru identificare facila
  * - iban
  * - persoaneContact - ale afacerii
  * - contracte - legaturi cu spatiile inchiriate
  * - antecedente
  *
  * Precizari:
  * - numeAfacere nu poate fi modificat
  * - ibanul poate fi modificat
  * - Se pot adauga persoane de contact si se pot scoate dupa nume
  * - Se pot adauga contracte, dar nu se pot scoate
  * - Se pot adauga antecedente, dar nu se pot scoate
 */
package Chirias;

import Persoana.Persoana;
import Persoana.PersoanaContact;

public class Chirias implements Cloneable{
    private final String numeAfacere;
    private String iban;
    private PersoanaContact[] persoaneContact;
    private Contract[] contracte;
    private Antecedent[] antecedente;

    public Chirias(String numeAfacere, String iban, PersoanaContact[] persoaneContact, Contract[] contracte, Antecedent[] antecedente) {
        this.numeAfacere = numeAfacere;
        this.iban = iban;
        this.persoaneContact = persoaneContact.clone();
        this.contracte = contracte.clone();
        this.antecedente = antecedente.clone();
    }

    public String getNumeAfacere() {
        return numeAfacere;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public PersoanaContact[] getPersoaneContact() {
        return persoaneContact.clone();
    }

    public void setPersoaneContact(PersoanaContact[] persoaneContact) {
        this.persoaneContact = persoaneContact.clone();
    }

    public Contract[] getContracte() {
        return contracte.clone();
    }

    public void setContracte(Contract[] contracte) {
        this.contracte = contracte.clone();
    }

    public Antecedent[] getAntecedente() {
        return antecedente.clone();
    }
    public void setAntecedente(Antecedent[] antecedente) {
        this.antecedente = antecedente.clone();
    }

    @Override
    public Chirias clone() throws CloneNotSupportedException {
        return new Chirias(this.numeAfacere, this.iban, this.persoaneContact.clone(), this.contracte.clone(), this.antecedente.clone());
    }

    public void AdaugaContract(Contract cNou) throws CloneNotSupportedException {
        Contract[] c = new Contract[contracte.length + 1];
        for(int i = 0; i < contracte.length; i++){
            c[i] = contracte[i];
        }
        c[contracte.length] = cNou.clone();
        contracte = c;
    }

    public void AdaugaPersoanaContact(PersoanaContact pNou) throws CloneNotSupportedException {
        PersoanaContact[] p = new PersoanaContact[persoaneContact.length + 1];
        for(int i = 0; i < persoaneContact.length; i++){
            p[i] = persoaneContact[i];
        }
        p[persoaneContact.length] = pNou.clone();
        persoaneContact = p;
    }
    public void ScoatePersoanaContact(PersoanaContact pVechi){
        int i = -1;
        for(int j = 0; j < persoaneContact.length; j++){
            if(persoaneContact[j].equals(pVechi)) i = j;
        }
        if(i == -1) return;
        PersoanaContact[] p = new PersoanaContact[persoaneContact.length - 1];
        for(int j = 0; j < persoaneContact.length; j++){
            if(j < i) p[j] = persoaneContact[j];
            else if(j > i) p[j-1] = persoaneContact[j];
        }
        persoaneContact = p;
    }
    public void AdaugaAntecedent(Antecedent aNou) throws CloneNotSupportedException {
        Antecedent[] a = new Antecedent[antecedente.length + 1];
        for(int i = 0; i < antecedente.length; i++){
            a[i] = antecedente[i];
        }
        a[antecedente.length] = aNou.clone();
        antecedente = a;
    }

}
