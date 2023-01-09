package Controlador;

import Modelo.*;
import Excepciones.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat; //importa para implementar puntos en los miles
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;


public class ControladorArriendoEquipos implements Serializable{

    private static ControladorArriendoEquipos instancia=null;
    private ArrayList<Cliente> clientes;
    private ArrayList<Equipo> equipos;
    private ArrayList<Arriendo> arriendos;
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

    public void llenarControlador (){
        try {
            creaCliente("001","Juanito","Nose","32489384");
            creaCliente("002","Pepito","Nose","32484");
            creaImplemento(6969,"Moto",1000);
            long codArriendo = ControladorArriendoEquipos.getInstancia().creaArriendo("001");//CREA ARRIENDO
            System.out.println(ControladorArriendoEquipos.getInstancia().agregaEquipoToArriendo(0, 6969));
            cierraArriendo(0);
            System.out.println("Codigo arriendo aaaaa: "+arriendos.get(0).getCodigo());

            String[][] detallesArriendo = ControladorArriendoEquipos.getInstancia().listaDetallesArriendo(0);
            EstadoEquipo[] estadito=new EstadoEquipo [detallesArriendo.length];
            estadito[0]=EstadoEquipo.OPERATIVO;
            devuelveEquipos(0,estadito);
            pagaArriendoContado(0, 999);

        }catch (ClienteException | EquipoException |ArriendoException e) {
            System.out.println(e.getMessage()+"La wea no funciono AAAAAAAAAAAa");
    }
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

    public void creaConjunto(long codigo, String descripcion, long [] codEquipos) throws EquipoException{
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
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo != null ){
            if (arriendo.getEstado() == EstadoArriendo.DEVUELTO){
                if (monto <= arriendo.getSaldoAdeudado()){
                    arriendo.addPagoContado(new Contado(monto,new Date()));
                    if ((monto + arriendo.getMontoPagado() )== arriendo.getSaldoAdeudado()){
                        arriendo.setEstado(EstadoArriendo.PAGADO);
                    }
                    System.out.println("Pago realizado exitosamente");
                } else {
                    throw new ArriendoException("el monto ingresado es mayor al que se debe");
                }
            } else {
                throw new ArriendoException("el equipo aun no se ha devuelto o ya esta pagado");
            }
        } else {
            throw new ArriendoException("El arriendo no existe");
        }
    }
    
    

    public void pagaArriendoDebito(long codArriendo, long monto, String codTransaccion,
            String numTarjeta) throws ArriendoException{
    	Arriendo arriendo = buscaArriendo(codArriendo);
    	if (arriendo != null ){
    		if (arriendo.getEstado() == EstadoArriendo.DEVUELTO){
    			if (monto <= arriendo.getSaldoAdeudado()){
    				Debito pago = new Debito(monto,new Date(),codTransaccion,numTarjeta);
    				arriendo.addPagoDebito(pago);
    				if ((monto + arriendo.getMontoPagado() )== arriendo.getSaldoAdeudado()){
    					arriendo.setEstado(EstadoArriendo.PAGADO);
    					}
    				System.out.println("Pago realizado exitosamente");
    				} else {
    					throw new ArriendoException("el monto ingresado es mayor al que se debe");
    					}
    			} else {
    				throw new ArriendoException("el equipo aun no se ha devuelto o ya se encuantra pagado");
    				}
    		}else {
    			throw new ArriendoException("El arriendo no existe");
    			}
    	}



    public void pagaArriendoCredito(long codArriendo, long monto, String codTransaccion, String numTarjeta,
                                    int nroCuotas) throws ArriendoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo != null ){
            if (arriendo.getEstado()== EstadoArriendo.DEVUELTO){
                if (monto <= arriendo.getSaldoAdeudado()){
                    Credito pago = new Credito(monto,new Date(),codTransaccion,numTarjeta,nroCuotas);
                    arriendo.addPagoCredito(pago);
                    if ((monto + arriendo.getMontoPagado() )== arriendo.getSaldoAdeudado()){
                        arriendo.setEstado(EstadoArriendo.PAGADO);
                    }
                    System.out.println("Pago realizado exitosamente");
                } else {
                    throw new ArriendoException("el monto ingresado es mayor al que se debe");
                }
            } else {
                throw new ArriendoException("el equipo aun no se ha devuelto o ya se encuantra pagado");
            }
        } else {
            throw new ArriendoException("El arriendo no existe");
        }
    }



    public String[] consultaArriendoAPagar(long codigo){
        Arriendo arriendo=buscaArriendo(codigo);
        if (arriendo != null && arriendo.getEstado().equals(EstadoArriendo.DEVUELTO)){
            String[] datosArriendo = new String[7];
            datosArriendo[0]= String.valueOf(arriendo.getCodigo());
            datosArriendo[1]= String.valueOf(arriendo.getEstado()).toLowerCase();
            datosArriendo[2]= arriendo.getCliente().getRut();
            datosArriendo[3]= arriendo.getCliente().getNombre();
            datosArriendo[4]= String.valueOf(arriendo.getMontoTotal());
            datosArriendo[5]= String.valueOf(arriendo.getMontoPagado());
            datosArriendo[6]= String.valueOf(arriendo.getSaldoAdeudado());
            return datosArriendo;
        }
        return new String[0];
    }
    
    public String[][] listaArriendosPagados(){
        String[][] arriendosPagados = new String[arriendos.size()][7];
        int i= 0;
        for (Arriendo arriendo : arriendos){
            if (arriendo.getPagosToString() != null){
                arriendosPagados[i][0]= String.valueOf(arriendo.getCodigo());
                arriendosPagados[i][1]= String.valueOf(arriendo.getEstado()).toLowerCase();
                arriendosPagados[i][2]= arriendo.getCliente().getRut();
                arriendosPagados[i][3]= arriendo.getCliente().getNombre();
                arriendosPagados[i][4]= String.valueOf(arriendo.getMontoTotal());
                arriendosPagados[i][5]= String.valueOf(arriendo.getMontoPagado());
                arriendosPagados[i][6]= String.valueOf(arriendo.getSaldoAdeudado());
                i++;
            }
        }
        if(i == 0){
            return new String[0][0];
        }
        return arriendosPagados;
    }


    public String[][] listaPagosDeArriendo(long codArriendo) throws ArriendoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo != null){
            if (arriendo.getEstado() != EstadoArriendo.INICIADO && arriendo.getEstado() != EstadoArriendo.ENTREGADO){
                return arriendo.getPagosToString();
            } else {
                throw  new ArriendoException("el arriendo no esta habilitado para recibir pagos");
            }
        } else {
            throw new ArriendoException("No existe el arriendo con el codigo dado");
        }
    }
    
    
    
    //LEE E IMPORTA LOS DATOS GUARDADOS DESDE UN ARCHIVO
    public void readDatosSistema() throws DataFormatException {
        try {
        	
        	ObjectInputStream lecturaObj=new ObjectInputStream(new FileInputStream("controlador.obj"));
        	instancia=(ControladorArriendoEquipos)lecturaObj.readObject();
        	lecturaObj.close();
        }catch (Exception e){
            throw new DataFormatException("No ha sido posible leer datos del sistema desde archivo");
        }
    } 
    

    
    //GUARDA LOS DATOS DEL SISTEMA EN UN ARCHIVO (CLIENTE,EQUIPOS Y ARRIENDO)
     public void saveDatosSistema() throws DataFormatException {
        try {
        	

        	ObjectOutputStream controlador=new ObjectOutputStream(new FileOutputStream("controlador.obj"));
        	controlador.writeObject(ControladorArriendoEquipos.getInstancia());
        	controlador.close();

        }catch (Exception e){
            throw new DataFormatException("No ha sido posible guardar datos del sistema");
        }
     }
   
     
    
    public void cambiaEstadoCliente(String rutCliente)throws ClienteException{
        Cliente cliente = buscaCliente(rutCliente);
        if (cliente!=null){
            if(cliente.isActivo()){
                cliente.setInactivo();
            } else {
                cliente.setActivo();
            }
        }else{
            throw new ClienteException("El cliente no existe");
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
    public long creaArriendo(String rut) throws ClienteException{
        if(buscaCliente(rut)!=null){
            if (buscaCliente(rut).isActivo()){
                int codigo=arriendos.size();
                Arriendo nuevoArriendo= new Arriendo(codigo,new Date(),buscaCliente(rut));
                arriendos.add(nuevoArriendo);
                buscaCliente(rut).addArriendo(nuevoArriendo);
                return codigo;
            }else {
                throw new ClienteException("El cliente esta inactivo");
            }
        }else {
            throw new ClienteException("El cliente no existe");
        }
    }
    
    public String agregaEquipoToArriendo(long codArriendo,long codEquipo) throws ArriendoException, EquipoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo != null){
            if (arriendo.getEstado()== EstadoArriendo.INICIADO){
                Equipo equipo = buscaEquipo(codEquipo);
                if (equipo != null){
                    if (!equipo.isArrendado()){
                        if (equipo.getEstado()==EstadoEquipo.OPERATIVO){
                            arriendo.addDetalleArriendo(equipo);
                            return equipo.getDescripcion();
                        }else {
                            throw new EquipoException("El equipo no esta operativo");
                        }
                    }else {
                        throw new EquipoException("El equipo se encuentra arrendado");
                    }
                }else {
                    throw new EquipoException("El equipo ingresado no existe");
                }
            }else {
                throw new ArriendoException("El arriendo no esta iniciado");
            }
        }else {
            throw new ArriendoException("El arriendo ingresado no existe");
        }
    }
    public long cierraArriendo(long codArriendo) throws ArriendoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo != null){
            String detalles[][]= arriendo.getDetallestoString();
            long cantEquipos = detalles.length;
            if (cantEquipos >0){
                buscaArriendo(codArriendo).setEstado(EstadoArriendo.ENTREGADO);
                return arriendo.getMontoTotal();
            }else {
                throw new ArriendoException("el arriendo no tiene equipos asociados");
            }
        }else {
            throw new ArriendoException("El arriendo ingresado no existe");
        }

    }
    public void devuelveEquipos(long codArriendo,EstadoEquipo[] estadoEquipos) throws ArriendoException{
        Arriendo arriendo = buscaArriendo(codArriendo);
        if (arriendo!= null){
            if (arriendo.getEstado()==EstadoArriendo.ENTREGADO){
                Equipo[] equipos = arriendo.getEquipos();
                for (int i =0; i<estadoEquipos.length;i++){
                    equipos[i].setEstado(estadoEquipos[i]);
                }
                arriendo.setEstado(EstadoArriendo.DEVUELTO);
                arriendo.setFechaDevolucion(new Date());
            }else {
                throw new ArriendoException("el arriendo no tiene devolucion pendiente");
            }
        }else {
            throw new ArriendoException("el arriendo ingresado no existe");
        }
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
        if(buscaArriendo(codigo)!=null) {

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
        }
        return datos;
    }
    public String[][] listaArriendos(Date fechaAlInicioPeriodo, Date FechaFinPeriodo){
        String[][] arriendoArr = new String [arriendos.size()][7];
        String format="dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        int i = 0;
        for(Arriendo arriendo:arriendos){
            if(buscaArriendo(i)!=null) {
                if ((arriendo.getFechaInicio().after(fechaAlInicioPeriodo) || arriendo.getFechaInicio().equals(fechaAlInicioPeriodo)) &&
                        (arriendo.getFechaInicio().before(FechaFinPeriodo) || arriendo.getFechaInicio().equals(FechaFinPeriodo))){
                //if(arriendo.getFechaInicio().after(fechaAlInicioPeriodo) && arriendo.getFechaInicio().before(FechaFinPeriodo)) {
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
            }else{
                System.out.println("EL ERROR ESTA EN EL METODO CREOAAAAA");
            }
        }
        return arriendoArr;
    }
    /** public String[][] listaArriendosPorDevolver(String rutCliente){

        Cliente cliente= buscaCliente(rutCliente);
        if (cliente != null) {
      	  String[][] datos = new String[cliente.getArriendosPorDevolver().length][7];

      	  String format = "dd MMMM yyyy";
      	  
      	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
      	System.out.println("getArriendosPorDevolver lenght es: "+cliente.getArriendosPorDevolver().length);
      	  for (int i = 0; i < cliente.getArriendosPorDevolver().length; i++) {
      	    datos[i][0] = String.valueOf(cliente.getArriendosPorDevolver()[i].getCodigo());
      	    datos[i][1] = simpleDateFormat.format(cliente.getArriendosPorDevolver()[i].getFechaInicio());
      	    if (cliente.getArriendosPorDevolver()[i].getFechaDevolucion() != null) {
      	      datos[i][2] = simpleDateFormat.format(cliente.getArriendosPorDevolver()[i].getFechaDevolucion());
      	    } else {
      	      datos[i][2] = "No devuelto";
      	    }
      	    datos[i][3] = cliente.getArriendosPorDevolver()[i].getEstado().name().toLowerCase();
      	    System.out.println(datos[i][5]);
      	    datos[i][4] = cliente.getArriendosPorDevolver()[i].getCliente().getRut();
      	    datos[i][5] = cliente.getArriendosPorDevolver()[i].getCliente().getNombre();
      	    datos[i][6] = String.valueOf(cliente.getArriendosPorDevolver()[i].getMontoTotal());
      	  }
      	  return datos;
      	} else {
      		System.out.println("EL CLIENTE ES NULO");
      	  return new String[0][0];
      	}

    }**/
    
    public String[][] listaArriendosPorDevolver(String rutCliente) throws ClienteException{
        Cliente cliente = buscaCliente(rutCliente);
        if (cliente!= null){
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
        }else {
            throw new ClienteException("El cliente ingresado no existe");
        }
    }
    
    public String[][] listaDetallesArriendo(long codArriendo) throws ArriendoException{
    	if(buscaArriendo(codArriendo)!=null) {
    		if(buscaArriendo(codArriendo).getDetallestoString().length>0){
    			return buscaArriendo(codArriendo).getDetallestoString();
    		}else{
    			return null;
    			}
    	}else {
    		throw new ArriendoException("El codigo no corresponde a ningun arriendo");
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

        for(Arriendo arriendo: arriendos){
            if(arriendo.getCodigo()==codigo){
                return arriendo;
            }
        }
        return null;
    }
}
