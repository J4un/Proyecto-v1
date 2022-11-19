package Modelo;

import java.util.*;

public class Arriendo {
    private long codigo;
    private Date fechaInicio;
    private Date fechaDevolucion;
    private EstadoArriendo estado;


    public Arriendo(long codigo, Date fechaInicio,Cliente cliente) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        estado=EstadoArriendo.INICIADO;
        detalleArriendo=new ArrayList<DetalleArriendo>();
    }

    public long getCodigo() {
        return codigo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public EstadoArriendo getEstado() {
        return estado;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setEstado(EstadoArriendo estado) {
        this.estado = estado;
    }
    public void addDetalleArriendo(Equipo equipo) {
        detalleArriendo.add(new DetalleArriendo(equipo.getPrecioArriendoDia(),equipo,this));
    }
    public int getNumeroDiasArriendo(){
        if(this.getEstado()==EstadoArriendo.DEVUELTO){
            return (int)(fechaInicio.getTime()-fechaDevolucion.getTime());
        }
        return 0;
    }
    public long getMontoTotal(){
        if(this.getEstado()==EstadoArriendo.DEVUELTO){

        }
        return ;
    }
    public String[][] getDetallestoString(){
        return
    }
}
