package Modelo;

import java.util.ArrayList;
import java.io.Serializable;

public abstract class Equipo implements Serializable{
    protected long codigo;
    protected String descripcion;
    protected long precioArriendoDia;
    protected EstadoEquipo estado;
    protected ArrayList<DetalleArriendo> detalles;
    protected ArrayList<Equipo> equipos;

    public Equipo(long cod, String desc){
        codigo= cod;
        descripcion=desc;
        estado=EstadoEquipo.OPERATIVO;
        detalles= new ArrayList<>();
        equipos=new ArrayList<>();
    }

    public long getCodigo() {

        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }


    //Funcion abstracta usada por el implemento y el conjunto
    public abstract long getPrecioArriendoDia();

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

    public void addEquipos(Equipo equipo) {
        equipos.add(equipo);
    }
}
