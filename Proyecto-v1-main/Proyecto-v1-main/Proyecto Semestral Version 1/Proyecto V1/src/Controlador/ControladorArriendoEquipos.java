package Controlador;

import Modelo.Arriendo;
import Modelo.Cliente;
import Modelo.Equipo;
import Vista.UIArriendoEquipos;
import java.text.DecimalFormat; //importa para implementar puntos en los miles
import java.util.ArrayList;
import java.util.*;

public class ControladorArriendoEquipos {

    private static ControladorArriendoEquipos instancia=null;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Equipo> equipos;
    private final ArrayList<Arriendo> arriendos;
    private ControladorArriendoEquipos(){
        clientes= new ArrayList<>();
        equipos= new ArrayList<>();
        arriendos=new ArrayList<>();
    }
    public static ControladorArriendoEquipos getInstancia(){
        if(instancia==null){
            instancia=new ControladorArriendoEquipos();
        }
        return instancia;
    }

    public void creaCliente(String rut, String nom, String dir, String tel){
        clientes.add(new Cliente(rut, nom, dir, tel));
    }

    public void creaEquipo(long cod, String desc, long precio){
        equipos.add(new Equipo(cod, desc, precio));
    }

    public String [][] listaClientes(){
        String [][] clienteArr = new String [clientes.size()][5];
        int i=0;
        for(Cliente cliente: clientes){
            clienteArr[i][0] = cliente.getRut();
            clienteArr[i][1] = cliente.getNombre();
            clienteArr[i][2] = cliente.getDireccion();
            clienteArr[i][3] = cliente.getTelefono();
            if(cliente.isActivo()==true){
                clienteArr[i][4] = "Activo";
            }
            else {
                clienteArr[i][4] = "Inactivo";
            }
            i++;
        }
        return clienteArr;
    }
    public String[][] listaEquipos(){

        DecimalFormat miles =new DecimalFormat("###,###.##");

        String[][] equipoArr = new String [equipos.size()][4];
        int i = 0;
        for(Equipo equipo: equipos){
            equipoArr[i][0] = String.valueOf(equipo.getCodigo());
            equipoArr[i][1] = equipo.getDescripcion();
            equipoArr[i][2] = miles.format(equipo.getPrecioArriendoDia()); //al numero ingresado le agrega los puntos en los miles
            equipoArr[i][3] = String.valueOf(equipo.getEstado());
            i++;
        }
        return equipoArr;
    }
    public long creaArriendo(String rut){
        if(buscaCliente(rut)!=null && "ACA FALTA ALGO AA"){
            int codigo=arriendos.size();
            arriendos.add(new Arriendo(codigo,new Date(),buscaCliente(rut)));
            return codigo;
        }
    }
    public String agregaEquipoToArriendo(long codArriendo,long codEquipo){

    }
    public long cierraArriendo(long codArriendo){

    }
    public void devuelveEquipos(long codArriendo,EstadoEquipo[] estadoEquipos){

    }
    public void cambiaEstadoCliente(String rutCliente){

    }
    public String[] consultaCliente(String rut){
        String [] datos= new String[6];
        if(buscaCliente(rut)!=null) {
            datos[0]=rut;
            datos[1]=buscaCliente(rut).getNombre();
            datos[2]=buscaCliente(rut).getDireccion();
            datos[3]=buscaCliente(rut).getTelefono();
            if(buscaCliente(rut).isActivo()) {
                datos[4]="Activo";
            }else{
                datos[4]="Inactivo";
            }
            datos[5]= String.valueOf((buscaCliente(rut).getArriendosPorDevolver()).length);
        }
    }
    public String[] consultaEquipo(long codigo){

    }
    public String[] consultaArriendo(long codigo){

    }
    public String[][] listaArriendos(Date fechaAlInicioPeriodo,Date FechaFinPeriodo){

    }
    public String[][] listaArriendosPorDevolver(String rutCliente){

    }
    public String[][] listaDetallesArriendo(long codArriendo){

    }
    private Cliente buscaCliente(String rut){
        for(Cliente cliente: clientes){
            if(cliente.getRut().equals(rut)){
                return cliente;
            }
        }
        return null;
    }
    private Equipo buscaEquipo(long codigo){
        for(Equipo equipo: equipos){
            if(equipo.getCodigo()==codigo){
                return equipo;
            }
        }
        return null;
    }
    }
    private Arriendo buscaArriendo(long codigo){
        for(Arriendo arriendo: arriendos){
            if(arriendo.getCodigo()==codigo){
                return arriendo;
            }
        }
        return null;
    }
}
