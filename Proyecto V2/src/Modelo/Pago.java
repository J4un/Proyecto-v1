package Modelo;

import java.io.Serializable;
import java.util.Date;

public abstract class Pago implements Serializable{
    long monto;
    Date fecha;

    public Pago(long monto, Date fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }

    public long getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }
}
