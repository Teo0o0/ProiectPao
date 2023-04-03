/*
    * Spatiile compune ce extind cu:
    * - tipSpatiu
 */
package Cladire;

import java.util.Objects;

public class SpatiuComun extends Spatiu implements Cloneable{
    protected String tipSpatiu;
    boolean ValidateTipSpatiu(String tipSpatiu)
    {
        if(Objects.equals(tipSpatiu, "baie") || Objects.equals(tipSpatiu, "coridor")) return true;
        return false;
    }

    public SpatiuComun(double suprafata, Cladire Cladire, String tipSpatiu) {
        super(suprafata, Cladire);
        if(!ValidateTipSpatiu(tipSpatiu)) throw new IllegalArgumentException("Tip Saptiu Invalid");
        this.tipSpatiu = tipSpatiu;
    }

    public String getTipSpatiu() {
        return tipSpatiu;
    }

    @Override
    public Object clone() {
        return new SpatiuComun(this.suprafata, this.cladire, this.tipSpatiu);
    }
}
