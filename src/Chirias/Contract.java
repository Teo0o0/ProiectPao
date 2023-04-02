/*
    * Contract de inchiriere:
    * - dataSemnare
    * - dataFinalizare
    * - pretMP
    * - rataPenalizare
    * - spatiu
    * Nu pot fi modificate ulterior, cu exceptia dataFinalizare
 */
package Chirias;

import Cladire.SpatiuInchiriat;

import java.util.Date;

public class Contract implements Cloneable{
    protected final Date dataSemnare;
    protected Date dataFinalizare;
    protected final double pretMP;
    protected final double rataPenalizare;
    protected final SpatiuInchiriat spatiu;

    public Contract(Date dataSemnare, Date dataFinalizare, double pretMP, double rataPenalizare, SpatiuInchiriat spatiu) {
        this.dataSemnare = dataSemnare;
        this.dataFinalizare = dataFinalizare;
        this.pretMP = pretMP;
        this.rataPenalizare = rataPenalizare;
        this.spatiu = spatiu;
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

    public void setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
    }

    @Override
    protected Contract clone() throws CloneNotSupportedException {
        return new Contract(this.dataSemnare, this.dataFinalizare, this.pretMP, this.rataPenalizare, this.spatiu.clone());
    }
}
