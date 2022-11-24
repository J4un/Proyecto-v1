package Modelo;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

public class Arriendo {
    private long codigo;
    private Date fechaInicio;
    private Date fechaDevolucion;
    private EstadoArriendo estado;
    private Cliente cliente;

    private ArrayList <DetalleArriendo> detalle;

    public Arriendo(long codigo, Date fechaInicio,Cliente cliente) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.cliente=cliente;
        estado=EstadoArriendo.INICIADO;
        detalle=new ArrayList<>();
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
        DetalleArriendo creaDetalle=new DetalleArriendo(equipo.getPrecioArriendoDia(),equipo,this);
        detalle.add(creaDetalle);
    }
    public int getNumeroDiasArriendo(){
        if(this.getEstado()==EstadoArriendo.DEVUELTO){
            return (int) ChronoUnit.DAYS.between((Temporal) fechaInicio, (Temporal) fechaDevolucion);
        }
        return 0;
    }
    public long getMontoTotal(){
        long total=0;
        if(this.getEstado()==EstadoArriendo.DEVUELTO){
            for(int i = 0; i<detalle.size(); i++){
                total=total+(detalle.get(i).getPrecioAplicado()*getNumeroDiasArriendo());
                return total;
            }
        } else if (this.getEstado()==EstadoArriendo.ENTREGADO) {
            for(int i = 0; i<detalle.size(); i++){
                total=total+(detalle.get(i).getPrecioAplicado());
                return total;
            }
        }
        return total;
    }
    public String[][] getDetallestoString(){
        String[][] stringDetalles=new String[detalle.size()][3];
        if(this.estado!=EstadoArriendo.INICIADO || detalle.size()==0){
            return stringDetalles;
        }else{
            int i=0;
            for(DetalleArriendo detalle: detalle){
                stringDetalles [i][0]= String.valueOf(detalle.getEquipo().getCodigo());
                stringDetalles [i][1]=detalle.getEquipo().getDescripcion();
                stringDetalles [i][2]= String.valueOf(detalle.getPrecioAplicado());
                i++;
            }
        }
        return stringDetalles;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public Equipo[] getEquipos(){
        Equipo[] equiposArr=new Equipo[detalle.size()];
        int i=0;
        for(DetalleArriendo detalleArriendo: detalle){
            equiposArr[i]=detalleArriendo.getEquipo();
            i++;
        }
        return equiposArr;
    }
}
