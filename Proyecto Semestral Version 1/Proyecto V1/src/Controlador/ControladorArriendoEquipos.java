package Controlador;

import Modelo.*;
import Excepciones.*;
import Vista.UIArriendoEquipos;
import java.text.DecimalFormat; //importa para implementar puntos en los miles
import java.util.ArrayList;

public class ControladorArriendoEquipos {

    private static ControladorArriendoEquipos instancia=null;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Equipo> equipos;
    private ControladorArriendoEquipos(){
        clientes= new ArrayList<>();
        equipos= new ArrayList<>();
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
    public void cambiaEstadoCliente(String rutCliente)throws ClienteException{
        Cliente cliente = buscaCliente(rutCliente);
        if (cliente!=null){
            if(Cliente.isActivo()){
                Cliente.setInactivo();
            } else {
                Cliente.setActivo();
            }
        }else{
            throw new ClienteException("el cliente no existe");
        }
    }
    public String [] consultaCliente(String rut) {
        Cliente cliente = buscaCliente(rut);
        if (cliente != null) {
            String[] clientesArr = new String[6];
            clientesArr[0] = cliente.getRut();
            clientesArr[1] = cliente.getNombre();
            clientesArr[2] = cliente.getDireccion();
            clientesArr[3] = cliente.getTelefono();
            if(Cliente.isActivo()){
                clientesArr[4] = "Activo";
            }
            else {
                clientesArr[4] = "Inactivo";
            }
            //clientesArr[6] =cliente.getArriendosPorDevolver();
            return clientesArr;
        } else{
            return new String[0];
        }

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
}
