package Vista;

import Controlador.ControladorArriendoEquipos;
import Excepciones.ArriendoException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ListadoPagosArriendo extends JFrame{
    private JPanel panel1;
    private JButton volverButton;
    private JTable tablaPagos;
    private JLabel textCodigoArriendo;


    public ListadoPagosArriendo() {
        setContentPane(panel1);
        setTitle("Listado de arriendos");
        setSize(550, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);



        try{

            Long cod = Long.parseLong(JOptionPane.showInputDialog(
                    null, "Ingrese el codigo del arriendo", "Input", JOptionPane.QUESTION_MESSAGE));
            try{
                String[][] datosPago = ControladorArriendoEquipos.getInstancia().listaPagosDeArriendo(cod);
                TablaPagoArriendos tablaInicial=new TablaPagoArriendos(datosPago);
                tablaPagos.setModel(tablaInicial);
                setVisible(true);


            }catch(ArriendoException ar){
                JOptionPane.showMessageDialog(
                        null, "El arriendo no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }catch(Exception ex){
            JOptionPane.showMessageDialog(
                    null, "El dato ingresado no es un numero", "Error", JOptionPane.ERROR_MESSAGE);
        }

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /*public static void main (String[] args) throws ParseException {
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        ListadoPagosArriendo lista = new  ListadoPagosArriendo();


    }*/





    private static class TablaPagoArriendos extends AbstractTableModel {

        private final String[] COLUMNAS = {"Monto","Fecha","Tipo de pago"};
        private String[][] datos;

        public TablaPagoArriendos(String[][] datos) {
            this.datos = datos;
        }

        @Override
        public int getRowCount() {
            return datos.length;
        }

        @Override
        public int getColumnCount() {
            return COLUMNAS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> datos[rowIndex][0];
                case 1 -> datos[rowIndex][1];
                case 2 -> datos[rowIndex][2];
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNAS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return //"Hi".getClass();
                getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
