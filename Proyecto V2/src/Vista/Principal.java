package Vista;

import javax.swing.*;

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
    }
    public static void main (String[] args){
        Principal panel =new Principal();
    }
}
