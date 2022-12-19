package Modelo;

import java.text.SimpleDateFormat;
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
    private ArrayList <Credito> credito;
    private ArrayList <Debito> debito;
    private ArrayList <Contado> contado;

    public Arriendo(long codigo, Date fechaInicio,Cliente cliente) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.cliente=cliente;
        estado=EstadoArriendo.INICIADO;
        detalle=new ArrayList<>();
        credito=new ArrayList<>();
        debito=new ArrayList<>();
        contado=new ArrayList<>();
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
        return 696969;
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

    public void addPagoContado(Contado pago){
        contado.add(pago);
    }
    public void addPagoDebito(Debito pago){
        debito.add(pago);
    }
    public void addPagoCredito(Credito pago){
        credito.add(pago);
    }

    public long getMontoPagado(){
        long total=0;
        for(Contado contado:contado){
            total=total+contado.getMonto();
        }
        for(Debito debito:debito){
            total=total+debito.getMonto();
        }
        for(Credito credito:credito){
            total=total+credito.getMonto();
        }
        return total;
    }
    public long getSaldoAdeudado(){
        long deuda;
        if(getMontoTotal()-getMontoPagado()<=0){
            deuda=getMontoTotal()-getMontoPagado();
        }else{
            deuda=getMontoTotal();
        }
        return deuda;
    }
    public String[][] getPagosToString(){
        String format="dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        int nroPagos=contado.size()+debito.size()+credito.size();
        String[][] datosPagos=new String[3][nroPagos];
        int i=0;
        for(Contado contado:contado){
            datosPagos[i][0]= String.valueOf(contado.getMonto());
            datosPagos[i][1]= simpleDateFormat.format((contado.getFecha()));
            datosPagos[i][2]= "Contado";
            i++;
        }
        for(Debito debito:debito){
            datosPagos[i][0]= String.valueOf(debito.getMonto());
            datosPagos[i][1]= simpleDateFormat.format((debito.getFecha()));
            datosPagos[i][2]= "Debito";
            i++;
        }
        for(Credito credito:credito){
            datosPagos[i][0]= String.valueOf(credito.getMonto());
            datosPagos[i][1]= simpleDateFormat.format((credito.getFecha()));
            datosPagos[i][2]= "Credito";
            i++;
        }
        return datosPagos;

    }

}
