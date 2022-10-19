package Vista;

import Controlador.ControladorArriendoEquipos;

import java.util.Scanner;

public class UIArriendoEquipos {

    private static UIArriendoEquipos instancia=null;
    private final Scanner scan;
    private UIArriendoEquipos(){
        scan=new Scanner(System.in);
    }

    public static UIArriendoEquipos getInstancia(){

        if(instancia==null){
            instancia=new UIArriendoEquipos();
        }
        return instancia;
    }

    public void menu() {
        int opcion;
        do{
            System.out.println("******* SISTEMA DE ARRIENDO DE EQUIPOS DE NIEVE *******");
            System.out.println("\n*** MENU DE OPCIONES ***");
            System.out.println("1. Crea un nuevo cliente");
            System.out.println("2. Crea un nuevo equipo");
            System.out.println("3. Lista todos los clientes");
            System.out.println("4. Lista todos los equipos");
            System.out.println("5. Salir");
            System.out.print("\tIngrese opcion: ");
            opcion=scan.nextInt();

            switch(opcion){
                case 1:
                    crearCliente();
                    break;
                case 2:
                    crearEquipo();
                    break;
                case 3:
                    listaCliente();
                    break;
                case 4:
                    listaEquipo();
                    break;
                case 5:
                    break;
                default:
                    System.out.print("La opcion ingresada no es valida");
                    break;
            }
        }while(opcion!=5);
    }
    private void crearCliente(){
        String rut,nom,dir,tel;

        System.out.print("Ingrese el rut del cliente: ");
        rut=scan.next();

        System.out.print("Ingrese el nombre del cliente: ");
        nom=scan.nextLine();

        System.out.print("Ingrese la direccion del cliente: ");
        dir=scan.nextLine();

        System.out.print("Ingrese el numero de telefono del cliente: ");
        tel=scan.next();

        ControladorArriendoEquipos.getInstancia().creaCliente(rut,nom,dir,tel);
    }

    private void crearEquipo(){
        String cod,desc;
        long precio;
        System.out.print("Ingrese el rut del cliente: ");
        cod=scan.nextLine();

        System.out.print("Ingrese el nombre del cliente: ");
        desc=scan.nextLine();

        System.out.print("Ingrese la direccion del cliente: ");
        precio=scan.nextLong();

        ControladorArriendoEquipos.getInstancia().creaEquipo(cod,desc,precio);
    }
    private void listaCliente(){
        String[][] matrizCliente = ControladorArriendoEquipos.getInstancia().listaClientes();
        if (matrizCliente.length > 0) {
            System.out.println("\nListado de clientes");
            System.out.println("------------");
            System.out.printf("%-15s%-15s%-15s%-15s%n","Rut","Nombre","Direccion","Telefono","Estado");
            for (String[] linea : matrizCliente) {
                System.out.printf("%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2],linea[3]);
            }
        } else {
            System.out.println("\nNo hay clientes");
        }
    }
    private void listaEquipo(){
        String[][] matrizEquipo = ControladorArriendoEquipos.getInstancia().listaEquipos();
        if (matrizEquipo.length > 0) {
            System.out.println("\nListado de equipo");
            System.out.println("------------");
            System.out.printf("%-15s%-15s%-15s%-15s%n","Rut","Nombre","Direccion","Telefono","Estado");
            for (String[] linea : matrizEquipo) {
                System.out.printf("%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2],linea[3]);
            }
        } else {
            System.out.println("\nNo hay equipo");
        }
    }
}
