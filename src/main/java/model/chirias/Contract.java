/*
    * Contract de inchiriere:
    * - dataSemnare
    * - dataFinalizare
    * - pretMP
    * - rataPenalizare
    * - spatiu
    * Nu pot fi modificate ulterior, cu exceptia dataFinalizare
 */
package model.chirias;

import model.cladire.SpatiuInchiriat;

import java.util.Date;

public class Contract{
    protected final Date dataSemnare;
    protected Date dataFinalizare;
    protected final double pretMP;
    protected final double rataPenalizare;
    protected final SpatiuInchiriat spatiu; // agregare
    protected final Chirias chirias;

    public Contract(Date dataSemnare, Date dataFinalizare, double pretMP, double rataPenalizare, SpatiuInchiriat spatiu, Chirias chirias) {
        this.dataSemnare = dataSemnare;
        this.dataFinalizare = dataFinalizare;
        this.pretMP = pretMP;
        this.rataPenalizare = rataPenalizare;
        this.spatiu = spatiu;
        this.chirias = chirias;
    }

    public Date getDataSemnare() {
        return dataSemnare;
    }

    public Date getDataFinalizare() {
        return dataFinalizare;
    }

    public double getPretMP() {
        return pretMP;
    }

    public double getRataPenalizare() {
        return rataPenalizare;
    }

    public SpatiuInchiriat getSpatiu() throws CloneNotSupportedException {
        return spatiu.clone();
    }

    public Chirias getChirias() throws CloneNotSupportedException {
        return chirias.clone();
    }

    public void setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "dataSemnare=" + dataSemnare +
                ", dataFinalizare=" + dataFinalizare +
                ", pretMP=" + pretMP +
                ", spatiu=" + spatiu.getSuprafata() +
                '}';
    }

    public Contract Copie() throws CloneNotSupportedException {
        return new Contract(this.dataSemnare, this.dataFinalizare, this.pretMP, this.rataPenalizare, this.spatiu.clone(), this.chirias.clone());

    }


}
