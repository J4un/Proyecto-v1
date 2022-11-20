package Controlador;

import Modelo.*;
import Excepciones.*;
import Vista.UIArriendoEquipos;
import java.text.DecimalFormat; //importa para implementar puntos en los miles
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


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
    ArrayList<Cliente> ListaClientes = new ArrayList<>();
    ArrayList<Equipo> ListaEquipos = new ArrayList<>();

    public void creaCliente(String rut, String nom, String dir, String tel)throws ClienteException{
        Cliente cliente = buscaCliente(rut);
        if (cliente == null){
            clientes.add(new Cliente(rut, nom, dir, tel));
            System.out.println("Cliente creado con exito");
        } else{
            throw new ClienteException("El cliente ya existe");
        }
    }



    public void creaEquipo(long cod, String desc, long precio)throws EquipoException {
        Equipo equipo=buscaEquipo(cod);
        if (equipo == null){
            equipos.add(new Equipo(cod, desc, precio));
            System.out.println("Equipo creado con exito");
        }else {
            throw new EquipoException("El equipo ya existe");
        }
    }
    public void devuelveEquipos (long codArriendo, EstadoEquipo[] estadoEquipos) throws ArriendoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo!=null ){
            if(arriendo.getEstado() == EstadoArriendo.ENTREGADO){
                arriendo.setEstado(EstadoArriendo.DEVUELTO);
                for (int i=0;i<estadoEquipos.length; i++){
                    Date fecha = new Date();
                    arriendo.setFechaDevolucion(fecha);
                }
            }else{
                throw new ArriendoException("el arriendo ya fue entregado");
            }
        }else{
            throw new ArriendoException("no existe un arriendo con el codigo dado");
        }
    }
    public void cambiaEstadoCliente(String rutCliente)throws ClienteException{
        Cliente cliente = buscaCliente(rutCliente);
        if (cliente!=null){
            if (cliente.isActivo()) {
                cliente.setInactivo();
            } else {
                cliente.setActivo();
            }
        }else{
            throw new ClienteException("el cliente no existe");
        }
    }
    public String [] consultaCliente(String rut) {
        Cliente cliente = buscaCliente(rut);
        String[] clientesArr = new String[6];
        if (cliente != null) {
            clientesArr[0] = cliente.getRut();
            clientesArr[1] = cliente.getNombre();
            clientesArr[2] = cliente.getDireccion();
            clientesArr[3] = cliente.getTelefono();
            if(cliente.isActivo()){
                clientesArr[4] = "Activo";
            }
            else {
                clientesArr[4] = "Inactivo";
            }
            clientesArr[6] = Arrays.toString(cliente.getArriendosPorDevolver());
            return clientesArr;
        } else{
            return clientesArr;
        }

    }
    public String [][] listaClientes(){
        String [][] clienteArr = new String [clientes.size()][6];
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
            clienteArr[5] = cliente.getArriendosPorDevolver();
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
    //public String agregaEquipoToArriendo(long codArriendo,long codEquipo){ }
    //public long cierraArriendo(long codArriendo){ }
    //public void devuelveEquipos(long codArriendo,EstadoEquipo[] estadoEquipos){ }

    //public String[] consultaEquipo(long codigo){ }
    //public String[] consultaArriendo(long codigo){ }
    //public String[][] listaArriendos(Date fechaAlInicioPeriodo,Date FechaFinPeriodo){ }
    //public String[][] listaArriendosPorDevolver(String rutCliente){ }
    //public String[][] listaDetallesArriendo(long codArriendo){

    private Cliente buscaCliente(String rut){
        for (Cliente cliente : clientes) {
            if (cliente.getRut().equalsIgnoreCase(rut)) {
                return cliente;
            }
        }
        return null;
    }
    private Equipo buscaEquipo(long cod){
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo()==(cod)) {
                return equipo;
            }
        }
        return null;
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
