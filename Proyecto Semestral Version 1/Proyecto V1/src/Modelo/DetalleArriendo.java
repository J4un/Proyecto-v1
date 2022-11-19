package Modelo;

import java.util.ArrayList;

public class DetalleArriendo {
    private long precioAplicado;

    public DetalleArriendo(long precioAplicado,Equipo equipo, Arriendo arriendo) {
        this.precioAplicado = precioAplicado;

    }

    public long getPrecioAplicado() {
        return precioAplicado;
    }
    public long getArriendo(){
        return arriendo;
    }
}
