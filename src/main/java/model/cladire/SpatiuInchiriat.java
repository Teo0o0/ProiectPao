/*
    * Spatiile care pot fi inchiriate, adauga:
    * - ocupat
 */
package model.cladire;

import model.chirias.Contract;

public class SpatiuInchiriat extends Spatiu implements Cloneable {

    protected boolean ocupat;
    protected Contract contract;

    public SpatiuInchiriat(double suprafata, Cladire cladire, boolean ocupat, Contract contract) {
        super(suprafata, cladire);
        this.ocupat = ocupat;
        this.contract = contract;
    }

    public SpatiuInchiriat(double suprafata, Cladire cladire, boolean ocupat) {
        super(suprafata, cladire);
        this.ocupat = ocupat;
    }

    public boolean isOcupat() {
        return ocupat;
    }

    public void setOcupat(boolean ocupat, Contract contract) {
        this.ocupat = ocupat;
        this.contract = contract;
    }

    public void setOcupat(boolean ocupat){
        this.ocupat = ocupat;
    }

    @Override
    public SpatiuInchiriat clone() throws CloneNotSupportedException {
        return new SpatiuInchiriat(this.suprafata, this.cladire, this.ocupat, this.contract.Copie());
    }

    @Override
    public Contract getContract() {
        return contract;
    }
}
