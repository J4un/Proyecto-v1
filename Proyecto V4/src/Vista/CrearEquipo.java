package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.EquipoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearEquipo extends JFrame {

    private long codigo;
    private String descripcion;
    private long precio;
    private JPanel JPanelCreaEquipo;
    private JTextField textCodigo;
    private JTextField textDescripcion;
    private JButton okButton;
    private JButton volverButton;
    private JTextField textPrecio;
    private JLabel imagenBota;
    private JLabel titulo;

    public CrearEquipo() {

        setContentPane(JPanelCreaEquipo);
        setTitle("Menu principal");
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);





        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    codigo= Long.parseLong(textCodigo.getText());
                    descripcion=textDescripcion.getText();
                    precio= Long.parseLong(textPrecio.getText());
                    try{
                        ControladorArriendoEquipos.getInstancia().creaImplemento(codigo,descripcion,precio);


                        JOptionPane.showMessageDialog(
                                null,
                                "Implemento creado satisfactoriamente",
                                "Mensaje",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ya existe un equipo con el codigo dado",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }catch(Exception ex2){
                    JOptionPane.showMessageDialog(
                            null,
                            "Ha ocurrido un error, no se puede ingresar el implemento",
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

    /* public static void main (String[] args){
        CrearEquipo nose=new CrearEquipo();
    }*/

}
