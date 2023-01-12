package Vista;

import Controlador.ControladorArriendoEquipos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ListadoClientes extends JFrame{
    private JPanel panel1;
    private JButton volverButton;
    private JTable tablaClientes;

    public ListadoClientes(){
        setContentPane(panel1);
        setTitle("Listado de arriendos");
        setSize(550,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        String[][] listaClientes= ControladorArriendoEquipos.getInstancia().listaClientes();
        TablaArriendos tablaInicial=new TablaArriendos(listaClientes);
        tablaClientes.setModel(tablaInicial);
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /*public static void main (String[] args) throws ParseException {
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        ListadoClientes lista = new  ListadoClientes();

    }*/

    private static class TablaArriendos extends AbstractTableModel {

        private final String[] COLUMNAS = {"Rut","Nombre","Direccion","Telefono","Estado","Arriendos por devolver"};
        private String[][] datos;

        public TablaArriendos(String[][] datos) {
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
                case 3 -> datos[rowIndex][3];
                case 4 -> datos[rowIndex][4];
                case 5 -> datos[rowIndex][5];
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
