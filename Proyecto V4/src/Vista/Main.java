package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.ClienteException;

import javax.swing.*;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class Main {
    public static void main(String[] args){
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        Principal panel =new Principal();
    }
}
