package Modelo;

public class Equipo {
    private long codigo;
    private String descripcion;
    private long precioArriendoDia;
    private EstadoEquipo estado;

    public Equipo(String cod, String desc, long precio){
        codigo= Long.parseLong(cod);
        descripcion=desc;
        precioArriendoDia=precio;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public long getPrecioArriendoDia() {
        return precioArriendoDia;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }
}
