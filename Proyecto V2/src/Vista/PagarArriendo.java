package Vista;

import Controlador.ControladorArriendoEquipos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class PagarArriendo extends JFrame {
    private JPanel panel1;
    private JButton OKButton;
    private JButton volverButton;
    private JTextField codigoArriendo;
    private JButton buscarArriendoButton;
    private JRadioButton contadoRadioButton;
    private JRadioButton debitoRadioButton;
    private JRadioButton creditoRadioButton;
    private JTextField textMonto;
    private JTextField textNroTransaccion;
    private JTextField textNTarjeta;
    private JTextField textCuotas;
    private JLabel estado;
    private JLabel rutCliente;
    private JLabel nombreCliente;
    private JLabel fecha;
    private JLabel montoPagado;
    private JLabel montoTotal;
    private JLabel saldoAdeudado;

    public PagarArriendo() {
        setContentPane(panel1);
        setTitle("Listado de arriendos");
        setSize(850,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        buscarArriendoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String[] arriendosPagados = ControladorArriendoEquipos.getInstancia().consultaArriendoAPagar(Long.parseLong(codigoArriendo.getText()));
                    codigoArriendo.setText(arriendosPagados[0]);
                    estado.setText(arriendosPagados[1]);
                    rutCliente.setText(arriendosPagados[2]);
                    nombreCliente.setText(arriendosPagados[3]);
                    montoTotal.setText(arriendosPagados[4]);
                    montoPagado.setText(arriendosPagados[5]);
                    saldoAdeudado.setText(arriendosPagados[6]);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    Date date=new Date();
                    fecha.setText(sdf.format(date));

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(
                            null,
                            "El arriendo no existe",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    System.out.println(ex);
                }


            }
        });
    }
    public static void main (String[] args) throws ParseException {
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        PagarArriendo pago = new  PagarArriendo();

    }
}
