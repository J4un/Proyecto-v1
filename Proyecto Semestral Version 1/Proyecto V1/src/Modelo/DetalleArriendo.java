package Modelo;

import java.util.ArrayList;

public class DetalleArriendo {
    private long precioAplicado;
    private Arriendo arriendo;
    private Equipo equipo;

    public DetalleArriendo(long precioAplicado,Equipo equipo, Arriendo arriendo) {
        this.precioAplicado = precioAplicado;

        this.equipo = equipo;           //agregados como prueba
        this.arriendo = arriendo;       //revisar si estan bien
    }

    public long getPrecioAplicado() {
        return precioAplicado;
    }

    public Equipo getEquipo(){
        return equipo;        //revisar si es correcto
    }

    public Arriendo getArriendo(){
        return this.arriendo;      //revisar si es correcto
    }
}
