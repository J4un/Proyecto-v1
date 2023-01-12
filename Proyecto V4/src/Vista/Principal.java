package Vista;

import Controlador.ControladorArriendoEquipos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.zip.DataFormatException;

public class Principal extends JFrame {
    private JPanel panelPrincipal;
    private JButton nuevoConjuntoButton;
    private JButton nuevoImplementoButton;
    private JButton nuevoClienteButton;
    private JButton arrendarEquipoButton;
    private JButton listadoArriendosButton;
    private JButton listadoArriendosConPagosButton1;
    private JButton pagarArriendoButton;
    private JButton devolverArriendoButton;
    private JButton detalleDeUnArriendoButton;
    private JButton listadoPagoArriendoButton;
    private JButton listadoEquiposButton;
    private JButton listadoClientesButton;
    private JButton salirButton;
    private JButton guardarButton;
    private JButton abrirButton;
    private JPanel panelesInferiores;

    public Principal(){
        setContentPane(panelPrincipal);
        setTitle("Menu principal");
        setSize(1250,550);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);



        nuevoImplementoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearEquipo equipo =new CrearEquipo();
            }
        });
        nuevoClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               CrearCliente cliente=new CrearCliente();
            }
        });

        listadoPagoArriendoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListadoPagosArriendo pagosArriendo=new ListadoPagosArriendo();
            }
        });
        listadoClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListadoClientes listadoClientes=new ListadoClientes();
            }
        });
        pagarArriendoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagarArriendo pagarArriendo=new PagarArriendo();
            }
        });
        listadoArriendosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ListadoArriendo listaArriendo=new ListadoArriendo();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ControladorArriendoEquipos.getInstancia().saveDatosSistema();
                    JOptionPane.showMessageDialog(
                            null,
                            "Datos guardados exitosamente",
                            "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (DataFormatException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ha ocurrido un error al cargar los datos ",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        abrirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ControladorArriendoEquipos.getInstancia().readDatosSistema();
                    JOptionPane.showMessageDialog(
                            null,
                            "Datos leidos exitosamente",
                            "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (DataFormatException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ha ocurrido un error al abrir",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
