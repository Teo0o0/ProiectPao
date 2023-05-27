/*
    * Extinde clasa Persoana, cu:
    * - oras
    * Folosita pentru persoanele de contact date de chiriasi si reparatii
 */
package model.persoana;
import model.cladire.Oras;

public class PersoanaContact extends Persoana implements Cloneable {

  protected Oras oras; // agregare

    public PersoanaContact(String nume, int varsta, String email, String numarTelefon, Oras oras) {
        super(nume, varsta, email, numarTelefon);
        this.oras = oras;
    }

    public Oras getOras() {
        return oras;
    }

    @Override
    public PersoanaContact clone() {
        Oras o = this.oras;
        PersoanaContact c = new PersoanaContact(this.nume, this.varsta, this.email, this.numarTelefon, o);
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersoanaContact){
            return this.oras.equals(((PersoanaContact) obj).oras) && this.nume.equals(((PersoanaContact) obj).getNume());
        }
        return false;
    }
}
