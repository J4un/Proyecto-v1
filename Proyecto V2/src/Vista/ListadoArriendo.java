package Vista;

import Controlador.ControladorArriendoEquipos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;

public class ListadoArriendo extends JFrame{
    private JPanel panel1;
    private JButton OKButton;
    private JButton volverButton;
    private JTextField textFechaInicio;
    private JTextField textFechaFin;
    private JTable tablaArriendos;
    private JScrollPane scrollPane;


    public ListadoArriendo() throws ParseException {
        setContentPane(panel1);
        setTitle("Listado de arriendos");
        setSize(750,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        LocalDate dateActual= LocalDate.now();
        LocalDate datePasada=dateActual.minusYears(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String finalPeriodo = dateActual.format(formatter);
        String inicioPeriodo = datePasada.format(formatter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

        textFechaInicio.setText(inicioPeriodo);
        textFechaFin.setText(finalPeriodo);

        Date dateInicio = sdf.parse(inicioPeriodo);
        Date dateFin = sdf.parse(finalPeriodo);

        //String[][] listaArriendos=ControladorArriendoEquipos.getInstancia().listaArriendos(dateInicio,dateFin);
        //TablaArriendos tablaInicial=new TablaArriendos(listaArriendos);
        //tablaArriendos.setModel(tablaInicial);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

                try {
                    Date dateInicio = sdf.parse(textFechaInicio.getText());
                    Date dateFinal = sdf.parse(textFechaFin.getText());
                    String[][] listaArriendos=ControladorArriendoEquipos.getInstancia().listaArriendos(dateInicio,dateFinal);
                    TablaArriendos tablaNueva=new TablaArriendos(listaArriendos);
                    tablaArriendos.setModel(tablaNueva);
                    tablaArriendos.setAutoCreateRowSorter(true);

                } catch (ParseException ex) {
                    System.out.println("ERRROR ACAAAAA");
                    throw new RuntimeException(ex);
                }

            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SimpleDateFormat formato =new SimpleDateFormat("dd/MM/yyyy");
                        Date fechaInicial = formato.parse(textFechaInicio.getText());
                        Date fechaFinal = formato.parse(textFechaFin.getText());

                    String[][] matrizArriendo = ControladorArriendoEquipos.getInstancia().listaArriendos(fechaInicial,fechaFinal);
                    if (matrizArriendo.length > 0) {
                        System.out.println(matrizArriendo.length);
                        for (String[] linea : matrizArriendo) {
                            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Codigo", "Fecha inicio", "Fecha devol.", "Estado",
                                    "Rut cliente", "Monto total");
                            System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", linea[0], linea[1], linea[2], linea[3],
                                    linea[4], linea[6]);
                        }
                    }else{
                        System.out.println("\nNo hay arriendos");
                    }
                } catch (ParseException xd) {
                    System.out.println("Error en el programa");
                }
            }
        });
    }

    public static void main (String[] args) throws ParseException {
        ControladorArriendoEquipos.getInstancia().llenarControlador();
        ListadoArriendo lista = new  ListadoArriendo();

    }

    private static class TablaArriendos extends AbstractTableModel {

        private final String[] COLUMNAS = {"Codigo", "Fechainicio", "Fecha devol.", "Estado","Rut",
                "Nombre", "Monto total"};
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
                case 6 -> datos[rowIndex][6];
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
                return "Hi".getClass();
                //getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
