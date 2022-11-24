package Modelo;

import java.util.ArrayList;
import java.util.*;

public class Equipo {
    private long codigo;
    private String descripcion;
    private long precioArriendoDia;
    private EstadoEquipo estado;
    private ArrayList<DetalleArriendo> detalles;

    public Equipo(long cod, String desc, long precio){
        codigo= cod;
        descripcion=desc;
        precioArriendoDia=precio;
        estado=EstadoEquipo.OPERATIVO;
        detalles= new ArrayList<>();
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

    public void setEstado(EstadoEquipo estado){
        this.estado=estado;
    }

    public void addDetalleArriendo(DetalleArriendo detalle){
        detalles.add(detalle);
    }
    public boolean isArrendado(){
        if(detalles.size()<0){
            if(detalles.get(detalles.size()-1).getArriendo().getEstado()==EstadoArriendo.ENTREGADO){
                return true;
            }
        }
        return false;
    }
}
