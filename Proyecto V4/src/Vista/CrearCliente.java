package Vista;

import Controlador.ControladorArriendoEquipos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearCliente extends JFrame {

    private String rut;
    private String nombre;
    private String direc;
    private String tel;
    private JTextField textRut;
    private JTextField textNombre;
    private JTextField textDireccion;
    private JButton volverButton;
    private JButton okButton;
    private JPanel principal;
    private JTextField textNroTel;

    public CrearCliente(){
        setContentPane(principal);
        setTitle("Menu principal");
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rut= textRut.getText();
                    nombre=textNombre.getText();
                    direc= textDireccion.getText();
                    tel=textNroTel.getText();
                    try{
                        ControladorArriendoEquipos.getInstancia().creaCliente(rut,nombre,direc,tel);


                        JOptionPane.showMessageDialog(
                                null,
                                "Cliente creado satisfactoriamente",
                                "Mensaje",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ya existe un cliente con el rut dado",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }catch(Exception ex2){
                    JOptionPane.showMessageDialog(
                            null,
                            "Ha ocurrido un error, no se puede ingresar el cliente",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    /*public static void main (String[] args){
        CrearCliente panel =new CrearCliente();
    } */
}
