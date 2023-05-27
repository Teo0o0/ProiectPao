/*
 * Clasa abstracta, contine:
 * - nume
 * - varsta
 * - email
 * - numarTelefon
 * Toate pot fi accesate din clasele ce o extind si pot fi modificate
 */
package model.persoana;
public abstract class Persoana implements Cloneable {
    protected String nume;
    protected int varsta;
    protected String email;
    protected String numarTelefon;

    public Persoana(String nume, int varsta, String email, String numarTelefon) {
        this.nume = nume;
        this.varsta = varsta;
        this.email = email;
        this.numarTelefon = numarTelefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
