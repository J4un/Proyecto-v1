package Vista;

import javax.swing.*;

public class CrearCliente extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JButton button2;
    private JPanel principal;

    public CrearCliente(){
        setContentPane(principal);
        setTitle("Menu principal");
        setSize(450,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main (String[] args){
        CrearCliente panel =new CrearCliente();
    }
}
