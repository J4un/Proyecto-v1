package Modelo;

import java.io.Serializable;

public class Implemento extends Equipo implements Serializable{
    long precioArriendoDia;

    public Implemento(long cod, String desc, long precio){
        super(cod,desc);
        precioArriendoDia=precio;
    }

    public long getPrecioArriendoDia() {
        return precioArriendoDia;
    }
}
