package Modelo;

public class Implemento extends Equipo{
    long precioArriendoDia;

    public Implemento(long cod, String desc, long precio){
        super(cod,desc);
        precioArriendoDia=precio;
    }

    public long getPrecioArriendoDia() {
        return precioArriendoDia;
    }
}
