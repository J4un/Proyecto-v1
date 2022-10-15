package Controlador;

import Vista.UIArriendoEquipos;

public class ControladorArriendoEquipos {

    private static ControladorArriendoEquipos instancia=null;
    public static ControladorArriendoEquipos getInstancia(){
        if(instancia==null){
            instancia=new ControladorArriendoEquipos();
        }
        return instancia;
    }
}
