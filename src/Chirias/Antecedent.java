package Chirias;

import java.util.Date;
public class Antecedent {
    protected String descriere;
    protected Date data;

    public Antecedent() {
    }
    public Antecedent(String descriere, Date data) {
        this.descriere = descriere;
        this.data = data;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
