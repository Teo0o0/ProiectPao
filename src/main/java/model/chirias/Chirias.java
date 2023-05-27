package model.chirias;

import model.factura.Factura;
import model.persoana.PersoanaContact;
import java.util.ArrayList;

public class Chirias implements Cloneable {
    private final String numeAfacere;
    private String iban;
    private ArrayList<PersoanaContact> persoaneContact;
    private ArrayList<Contract> contracte;
    private ArrayList<Antecedent> antecedente;

    public Chirias(String numeAfacere, String iban, ArrayList<PersoanaContact> persoaneContact, ArrayList<Contract> contracte, ArrayList<Antecedent> antecedente) {
        this.numeAfacere = numeAfacere;
        this.iban = iban;
        this.persoaneContact = new ArrayList<>(persoaneContact);
        this.contracte = new ArrayList<>(contracte);
        this.antecedente = new ArrayList<>(antecedente);
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

    public ArrayList<PersoanaContact> getPersoaneContact() {
        return new ArrayList<>(persoaneContact);
    }

    public void setPersoaneContact(ArrayList<PersoanaContact> persoaneContact) {
        this.persoaneContact = new ArrayList<>(persoaneContact);
    }

    public ArrayList<Contract> getContracte() {
        return new ArrayList<>(contracte);
    }

    public void setContracte(ArrayList<Contract> contracte) {
        this.contracte = new ArrayList<>(contracte);
    }

    public ArrayList<Antecedent> getAntecedente() {
        return new ArrayList<>(antecedente);
    }

    public void setAntecedente(ArrayList<Antecedent> antecedente) {
        this.antecedente = new ArrayList<>(antecedente);
    }

    @Override
    public Chirias clone() throws CloneNotSupportedException {
        return new Chirias(this.numeAfacere, this.iban, new ArrayList<>(this.persoaneContact), new ArrayList<>(this.contracte), new ArrayList<>(this.antecedente));
    }

    public void AdaugaContract(Contract cNou) throws CloneNotSupportedException {
        contracte.add(cNou.Copie());
    }

    public void AdaugaPersoanaContact(PersoanaContact pNou) {
        persoaneContact.add(pNou.clone());
    }

    public void ScoatePersoanaContact(PersoanaContact pVechi) {
        persoaneContact.remove(pVechi);
    }

    public void AdaugaAntecedent(Antecedent aNou) {
        antecedente.add(aNou.Copie());
    }

//    public Factura[] getFacturi() {
//    }
}
