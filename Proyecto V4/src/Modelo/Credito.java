package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Credito extends Pago implements Serializable{
    String codTransaccion;
    String numTarjeta;
    int nroCuotas;

    public Credito (long monto, Date fecha, String codTrans, String numTarj, int nroCtas){
        super(monto,fecha);
        codTransaccion=codTrans;
        numTarjeta=numTarj;
        nroCuotas=nroCtas;
    }

    public String getCodTransaccion() {
        return codTransaccion;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public int getNroCuotas() {
        return nroCuotas;
    }

}
