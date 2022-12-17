package Controlador;

import Modelo.*;
import Excepciones.*;
import java.text.DecimalFormat; //importa para implementar puntos en los miles
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


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

    public void creaCliente(String rut, String nom, String dir, String tel)throws ClienteException{
        Cliente cliente = buscaCliente(rut);
        if (cliente == null){
            clientes.add(new Cliente(rut, nom, dir, tel));
            System.out.println("Cliente creado con exito");
        } else{
            throw new ClienteException("El cliente ya existe");
        }
    }

    public void creaImplemento(long codigo, String descripcion, long precioArriendoDia)throws EquipoException {
        Equipo implemento=buscaEquipo(codigo);
        if (implemento == null){
            equipos.add(new Implemento(codigo, descripcion, precioArriendoDia));
            System.out.println("Implemento creado con exito");
        }else {
            throw new EquipoException("El Implemento ya existe");
        }
    }

    public void CreaConjunto(long codigo, String descripcion, long [] codEquipos) throws EquipoException{
        Equipo conjunto = buscaEquipo(codigo);
        if (conjunto == null){
            for (long codEquipo : codEquipos) {
                if (buscaEquipo(codEquipo) != null) {
                    buscaEquipo(codigo).addEquipos(buscaEquipo(codEquipo));
                } else {
                    throw new EquipoException("el implemento ingresadp al conjunto, con codigo: " + codEquipo + " no existe");
                }
            }
            equipos.add(new Conjunto(codigo, descripcion));

            System.out.println("Conjunto creado con exito");
        }else {
            throw new EquipoException("el conjunto ya existe");
        }
    }

    public void pagaArriendoContado(long codArriendo, long monto) throws ArriendoException{
        String[] arriendo = consultaArriendo(codArriendo);
        if (arriendo[0] != null ){
            if (arriendo[3].equals("DEVUELTO")){
                if (monto >= Long.parseLong(arriendo[6])){
                    Date fecha = new Date();
                    Contado pago = new Contado(monto,fecha);
                    Objects.requireNonNull(buscaArriendo(codArriendo)).addPagoContado(pago);
                    System.out.println("Pago realizado exitosamente");
                }
                throw new ArriendoException("el monto ingresado es mayor al que se debe");
            }
            throw new ArriendoException("el equipo aun no se ha devuelto");
        }
        throw new ArriendoException("El arriendo no existe");
    }

    public void pagaArriendoDebito(long codArriendo, long monto, String codTransaccion,
                                   String numTarjeta) throws ArriendoException{
        String[] arriendo = consultaArriendo(codArriendo);
        if (arriendo[0] != null ){
            if (arriendo[3].equals("DEVUELTO")){
                if (monto >= Long.parseLong(arriendo[6])){
                    Date fecha = new Date();
                    Debito pago = new Debito(monto,fecha,codTransaccion,numTarjeta);
                    Objects.requireNonNull(buscaArriendo(codArriendo)).addPagoDebito(pago);
                    System.out.println("Pago realizado exitosamente");
                }
                throw new ArriendoException("el monto ingresado es mayor al que se debe");
            }
            throw new ArriendoException("el equipo aun no se ha devuelto");
        }
        throw new ArriendoException("El arriendo no existe");
    }

    public void pagaArriendoCredito(long codArriendo, long monto, String codTransaccion, String numTarjeta,
                                    int nroCuotas) throws ArriendoException{
        String[] arriendo = consultaArriendo(codArriendo);
        if (arriendo[0] != null ){
            if (arriendo[3].equals("DEVUELTO")){
                if (monto >= Long.parseLong(arriendo[6])){
                    Date fecha = new Date();
                    Credito pago = new Credito(monto,fecha,codTransaccion,numTarjeta,nroCuotas);
                    Objects.requireNonNull(buscaArriendo(codArriendo)).addPagoCredito(pago);
                    System.out.println("Pago realizado exitosamente");
                }
                throw new ArriendoException("el monto ingresado es mayor al que se debe");
            }
            throw new ArriendoException("el equipo aun no se ha devuelto");
        }
        throw new ArriendoException("El arriendo no existe");
    }
    /*
    Retorna un arreglo de String con los datos del arriendo, si este existe y si tiene el estado devuelto,
    en caso contrario, retorna un arreglo de tamaño cero. Si existe el arriendo y este puede tener un pago
    asociado, se almacena, en el arreglo, el código del arriendo, su estado en minúsculas, el rut del cliente
    asociado, el nombre de este, el monto total del arriendo, el monto pagado a la fecha y el monto total adeudado.
     */
    public String[][] consultaArriendoAPagar(long codigo){
        String[] arriendo = consultaArriendo(codigo);
        if (arriendo[0] != null && arriendo[3].equals("DEVUELTO")){
            String [][] arr = new String[9][9];
            return  arr;
        }
        return new String[0][0];
    }
    /*
    String [] datos= new String[5];
        if(buscaEquipo(codigo)!=null) {
            datos[0]= String.valueOf(codigo);
            datos[1]=buscaEquipo(codigo).getDescripcion();
            datos[2]= String.valueOf(buscaEquipo(codigo).getPrecioArriendoDia());
            datos[3]= String.valueOf(buscaEquipo(codigo).getEstado()).toLowerCase().replace("_"," ");
            if(buscaEquipo(codigo).isArrendado()){
                datos[4]="Arrendado";
            }else{
                datos[4]="Disponible";
            }
        }
        return datos;



    public void readDatosSistema(){

    }
    public void saveDatosSistema(){

    }
    public String[][] listaArriendosPagados(){

    }
    public String[][] listaPagosDeArriendo(){

    }
    */
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
    public String [][] listaClientes(){
        String [][] clienteArr = new String [clientes.size()][6];
        int i=0;
        for(Cliente cliente: clientes){
            clienteArr[i][0] = cliente.getRut();
            clienteArr[i][1] = cliente.getNombre();
            clienteArr[i][2] = cliente.getDireccion();
            clienteArr[i][3] = cliente.getTelefono();
            if(cliente.isActivo()){
                clienteArr[i][4] = "Activo";
            }
            else {
                clienteArr[i][4] = "Inactivo";
            }
            clienteArr[i][5]= String.valueOf(cliente.getArriendosPorDevolver().length);
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
        if(buscaCliente(rut)!=null && buscaCliente(rut).isActivo()){
            int codigo=arriendos.size();
            arriendos.add(new Arriendo(codigo,new Date(),buscaCliente(rut)));
            return codigo;
        }
        return 0;
    }
    public String agregaEquipoToArriendo(long codArriendo,long codEquipo){
        buscaArriendo(codArriendo).addDetalleArriendo(buscaEquipo(codEquipo));
        return buscaEquipo(codEquipo).getDescripcion();
    }
    public long cierraArriendo(long codArriendo){
        buscaArriendo(codArriendo).setEstado(EstadoArriendo.ENTREGADO);
        return buscaArriendo(codArriendo).getMontoTotal();
    }
    public void devuelveEquipos(long codArriendo,EstadoEquipo[] estadoEquipos){
        Equipo[] equipos =buscaArriendo(codArriendo).getEquipos();
        for(int i=0;i<buscaArriendo(codArriendo).getEquipos().length;i++){
            equipos[i].setEstado(estadoEquipos[i]);
        }
        buscaArriendo(codArriendo).setEstado(EstadoArriendo.DEVUELTO);
        buscaArriendo(codArriendo).setFechaDevolucion(new Date());
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
        return datos;
    }
    public String[] consultaEquipo(long codigo){
        String [] datos= new String[5];
        if(buscaEquipo(codigo)!=null) {
            datos[0]= String.valueOf(codigo);
            datos[1]=buscaEquipo(codigo).getDescripcion();
            datos[2]= String.valueOf(buscaEquipo(codigo).getPrecioArriendoDia());
            datos[3]= String.valueOf(buscaEquipo(codigo).getEstado()).toLowerCase().replace("_"," ");
            if(buscaEquipo(codigo).isArrendado()){
                datos[4]="Arrendado";
            }else{
                datos[4]="Disponible";
            }
        }
        return datos;
    }
    public String[] consultaArriendo(long codigo){
        String format="dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        String [] datos= new String[7];
        //if(buscaArriendo(codigo)!=null) {

            datos[0]= String.valueOf(buscaArriendo(codigo).getCodigo());

            datos[1]= simpleDateFormat.format(buscaArriendo(codigo).getFechaInicio());

            if(buscaArriendo(codigo).getFechaDevolucion()!=null){
                datos[2]= simpleDateFormat.format(buscaArriendo(codigo).getFechaDevolucion());
            }else{
                datos[2]="No devuelto";
            }
            datos[3]= String.valueOf(buscaArriendo(codigo).getEstado()).toLowerCase();
            datos[4]=buscaArriendo(codigo).getCliente().getRut();
            datos[5]=buscaArriendo(codigo).getCliente().getNombre();
            datos[6]= String.valueOf(buscaArriendo(codigo).getMontoTotal());
        //}
        return datos;
    }
    public String[][] listaArriendos(Date fechaAlInicioPeriodo, Date FechaFinPeriodo){
        String[][] arriendoArr = new String [arriendos.size()][7];
        String format="dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        int i = 0;
        for(Arriendo arriendo:arriendos){
            if(buscaArriendo(i)!=null) {

                arriendoArr[i][0]= String.valueOf(buscaArriendo(i).getCodigo());

                arriendoArr[i][1]= simpleDateFormat.format(buscaArriendo(i).getFechaInicio());

                if(buscaArriendo(i).getFechaDevolucion()!=null){
                    arriendoArr[i][2]= simpleDateFormat.format(buscaArriendo(i).getFechaDevolucion());
                }else{
                    arriendoArr[i][2]="No devuelto";
                }
                arriendoArr[i][3]= String.valueOf(buscaArriendo(i).getEstado()).toLowerCase();
                arriendoArr[i][4]=buscaArriendo(i).getCliente().getRut();
                arriendoArr[i][5]=buscaArriendo(i).getCliente().getNombre();
                arriendoArr[i][6]= String.valueOf(buscaArriendo(i).getMontoTotal());
                i++;
            }
        }
        return arriendoArr;
    }
    public String[][] listaArriendosPorDevolver(String rutCliente){
        Arriendo[] porDevolver=buscaCliente(rutCliente).getArriendosPorDevolver();
        String[][] datos=new String[porDevolver.length][7];
        String format="dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        for(int i=0;i<porDevolver.length;i++){
            datos[i][0]= String.valueOf(porDevolver[i].getCodigo());
            datos[i][1]=simpleDateFormat.format(porDevolver[i].getFechaInicio());
            if(porDevolver[i].getFechaDevolucion()!=null){
                datos[i][2]= simpleDateFormat.format(porDevolver[i].getFechaDevolucion());
            }else{
                datos[i][2]="No devuelto";
            }
            datos[i][3]= String.valueOf(porDevolver[i].getEstado()).toLowerCase();
            datos[i][4]=porDevolver[i].getCliente().getRut();
            datos[i][5]=porDevolver[i].getCliente().getNombre();
            datos[i][6]= String.valueOf(porDevolver[i].getMontoTotal());
        }
        return datos;
    }
    public String[][] listaDetallesArriendo(long codArriendo){
        if(buscaArriendo(codArriendo).getDetallestoString().length>0){
            return buscaArriendo(codArriendo).getDetallestoString();
        }else{
            return null;
        }
    }

    private Cliente buscaCliente (String rut){
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
        Arriendo busca;
        for(Arriendo arriendo: arriendos){
            if(arriendo.getCodigo()==codigo){
                return arriendo;
            }
        }
        return null;
    }
}
