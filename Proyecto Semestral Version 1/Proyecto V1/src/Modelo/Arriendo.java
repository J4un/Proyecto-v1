package Modelo;

import java.util.*;

public class Arriendo {
    private long codigo;
    private Date fechaInicio;
    private Cliente cliente;
    private Date fechaDevolucion;
    private EstadoArriendo estado;
    private ArrayList<DetalleArriendo>detalleArriendo;

    public Arriendo(long codigo, Date fechaInicio,Cliente cliente) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.cliente= cliente;
        estado=EstadoArriendo.INICIADO;
        detalleArriendo=new ArrayList<>();
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

        for (DetalleArriendo arriendo:detalleArriendo) {
            if (this.getEstado() == EstadoArriendo.DEVUELTO) {

                arriendo.getEquipo().getPrecioArriendoDia();                //****************
                long l = getNumeroDiasArriendo();                      //****************
                return l*(arriendo.getEquipo().getPrecioArriendoDia());     //****************

            }else if(this.getEstado() == EstadoArriendo.ENTREGADO){

                return arriendo.getEquipo().getPrecioArriendoDia();    //****************

            }
        }

        return 0;
    }
    public String[][] getDetallestoString(){

        String[][] detallesToString = new String[3][detalleArriendo.size()];

        if (this.getEstado() == EstadoArriendo.ENTREGADO || this.getEstado() == EstadoArriendo.DEVUELTO){

            int i=0;
            for (DetalleArriendo detalle:detalleArriendo) {
                detallesToString [0][i] = String.valueOf(detalle.getEquipo());
                detallesToString [1][i] = detalle.getEquipo().getDescripcion();
                detallesToString [2][i] = String.valueOf(detalle.getPrecioAplicado());
                i++;
            }
        }else {

        }

        return detallesToString;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public Equipo[] getEquipos(){
        Equipo[] equipos = new Equipo[detalleArriendo.size()];
        int i=0;
        for (DetalleArriendo detalle : detalleArriendo) {
            equipos[i] = detalle.getEquipo();
            i++;
        }
        return equipos;
    }

}
