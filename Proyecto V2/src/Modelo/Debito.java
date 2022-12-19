package Modelo;

import java.util.Date;

public class Debito extends Pago {
    String codTransaccion;
    String numTarjeta;

    public Debito (long monto, Date fecha, String codTrans, String numTarj){
        super(monto,fecha);
        codTransaccion=codTrans;
        numTarjeta=numTarj;
    }

    public String getCodTransaccion() {
        return codTransaccion;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

}
