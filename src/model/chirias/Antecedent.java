/*
  * Antecedent :
  * - descriere : tipul, gravitate
  * - data adaugare
  * Nu pot fi modificate dupa adaugare, deci nu avem setteri si toate datele sunt pe privat si final
  * Si clasa poate fi setata pe final - nu urmeaza extinderi
 */
package model.chirias;

import java.util.Date;
public final class Antecedent{
    private final String descriere;
    private final Date data;
    public Antecedent(String descriere, Date data) {
        this.descriere = descriere;
        this.data = data;
    }
    public String getDescriere() {
        return descriere;
    }
    public Date getData() {
        return data;
    }

    public Antecedent Copie(){
        return new Antecedent(descriere, data);
    }
}
