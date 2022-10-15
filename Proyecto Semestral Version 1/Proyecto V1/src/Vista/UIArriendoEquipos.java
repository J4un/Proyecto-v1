package Vista;

import Controlador.ControladorArriendoEquipos;

import java.util.Scanner;

public class UIArriendoEquipos {

    private static UIArriendoEquipos instancia=null;;
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

        ControladorArriendoEquipos.getInstancia().crearCliente(rut,nom,dir,tel);
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

        ControladorArriendoEquipos.getInstancia().crearEquipo(cod,desc,precio);
    }
    private void listaCliente(){

    }
    private void listaEquipo(){

    }
}
