package Chirias;

import Cladire.SpatiuInchiriat;

import java.util.Date;

public class Contract {
    protected Date dataSemnare;
    protected Date dataFinalizare;
    protected double pretMP;
    protected double rataPenalizare;
    protected SpatiuInchiriat spatiu;

    public Contract() {
    }

    public Contract(Date dataSemnare, Date dataFinalizare, double pretMP, double rataPenalizare, SpatiuInchiriat spatiu) {
        this.dataSemnare = dataSemnare;
        this.dataFinalizare = dataFinalizare;
        this.pretMP = pretMP;
        this.rataPenalizare = rataPenalizare;
        this.spatiu = spatiu;
    }
}
