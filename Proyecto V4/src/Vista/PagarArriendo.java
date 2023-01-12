package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.ArriendoException;

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
    private int option;
    private long cod;


    public PagarArriendo() {

        setContentPane(panel1);
        setTitle("Listado de arriendos");
        setSize(850,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        textCuotas.setEnabled(false);
        textNroTransaccion.setEnabled(false);
        textNTarjeta.setEnabled(false);

        //BUSCAR ARRIENDOS
        buscarArriendoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cod=Long.parseLong(codigoArriendo.getText());
                    String[] arriendosPagados = ControladorArriendoEquipos.getInstancia().consultaArriendoAPagar(cod);
                    codigoArriendo.setText(arriendosPagados[0]);
                    estado.setText(arriendosPagados[1]);
                    rutCliente.setText(arriendosPagados[2]);
                    nombreCliente.setText(arriendosPagados[3]);
                    montoTotal.setText("$ "+arriendosPagados[4]);
                    montoPagado.setText("$ "+arriendosPagados[5]);
                    saldoAdeudado.setText("$ "+arriendosPagados[6]);

                    textCuotas.setEnabled(true);
                    textNroTransaccion.setEnabled(true);
                    textNTarjeta.setEnabled(true);

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


        //BOTONES RADIO PARA SELECCIONAR DETALLES DE PAGO
        contadoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option=1;
                textCuotas.setEnabled(false);
                textNroTransaccion.setEnabled(false);
                textNTarjeta.setEnabled(false);
            }
        });
        debitoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option=2;
                textNroTransaccion.setEnabled(true);
                textNTarjeta.setEnabled(true);
            }
        });
        creditoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option=3;
                textCuotas.setEnabled(true);
                textNroTransaccion.setEnabled(true);
                textNTarjeta.setEnabled(true);
            }
        });


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (option) {
                    case 1 -> {
                        try {
                            try {
                                Long cod = Long.parseLong(codigoArriendo.getText());
                                Long monto = Long.parseLong(textMonto.getText());
                                ControladorArriendoEquipos.getInstancia().pagaArriendoContado(cod, monto);
                            } catch (NumberFormatException num) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Los datos ingresados son incorrectos",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (ArriendoException ex) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "El monto ingresado es mayor al que se debe",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }

                case 2 -> {
                    try {
                        try {
                            Long cod = Long.parseLong(codigoArriendo.getText());
                            Long monto = Long.parseLong(textMonto.getText());
                            String nroTransaccion = textNroTransaccion.getText();
                            String nroTarjeta = textNTarjeta.getText();
                            ControladorArriendoEquipos.getInstancia().pagaArriendoDebito(cod, monto, nroTransaccion, nroTarjeta);
                        } catch (NumberFormatException num) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Los datos ingresados son incorrectos",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (ArriendoException ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "El monto ingresado es mayor al que se debe",
                                "Advertencia",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    }
                    case 3->{
                        try {
                            try{
                                Long cod=Long.parseLong(codigoArriendo.getText());
                                Long monto=Long.parseLong(textMonto.getText());
                                String nroTransaccion=textNroTransaccion.getText();
                                String nroTarjeta=textNTarjeta.getText();
                                int nroCuotas=Integer.parseInt(textCuotas.getText());
                                ControladorArriendoEquipos.getInstancia().pagaArriendoCredito(cod,monto,nroTransaccion,nroTarjeta,nroCuotas);
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Se ha realizado el pago exitosamente",
                                        "Pagado",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }catch (NumberFormatException num){
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Los datos ingresados son incorrectos",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (ArriendoException ex) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "El monto ingresado es mayor al que se debe",
                                    "Advertencia",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
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


    /*public static void main (String[] args) throws ParseException {
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        PagarArriendo pago = new  PagarArriendo();

    }*/
}
