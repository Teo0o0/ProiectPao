package Persoana;
import Cladire.Oras;
import Persoana.Persoana;

public class PersoanaContact extends Persoana {

  protected Oras oras;

    public PersoanaContact(String nume, int varsta, String email, String numarTelefon, Oras oras) {
        super(nume, varsta, email, numarTelefon);
        this.oras = oras;
    }

    public Oras getOras() {
        return oras;
    }
}
