package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Contado extends Pago implements Serializable{
    public Contado(long monto,Date fecha) {
        super(monto,fecha);
    }
}
