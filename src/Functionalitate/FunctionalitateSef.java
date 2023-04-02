package Functionalitate;

import java.util.Date;

public class FunctionalitateSef {
    private static FunctionalitateSef instanta = null;
    private FunctionalitateSef(){}
    public static FunctionalitateSef getInstance(){
        if(instanta == null) {
            instanta = new FunctionalitateSef();

        }
        return instanta;
    }
    public double GetTotal(Date azi){
        return 0;
    }
    public double GetBalanta(Date azi){
        return 0;
    }
    public void GetAngajati(Date azi){

    }
    public double GetFacturi(Date azi){
        return 0;
    }
}
