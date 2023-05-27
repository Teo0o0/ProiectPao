/*
    * Oras:
    * - numeOras
    * - numarCladiri
    * - numeTara
    * Numai numarCladiri poate fi modificat
 */
package model.cladire;

public class Oras implements Cloneable {
    private final String numeOras;
    private int numarCladiri;
    private final String numeTara;

    public Oras(String numeOras, int numarCladiri, String numeTara) {
        this.numeOras = numeOras;
        this.numarCladiri = numarCladiri;
        this.numeTara = numeTara;
    }

    public int getNumarCladiri() {
        return numarCladiri;
    }

    public void setNumarCladiri(int numarCladiri) {
        this.numarCladiri = numarCladiri;
    }

    public String getNumeOras() {
        return numeOras;
    }

    public String getNumeTara() {
        return numeTara;
    }

    @Override
    public Oras clone() throws CloneNotSupportedException {
        return new Oras(this.numeOras, this.numarCladiri, this.numeTara);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Oras) {
            return (this.numeOras.equals(((Oras) obj).getNumeOras()) && this.numeTara.equals(((Oras) obj).getNumeTara()));
        }
        return false;
    }
}
