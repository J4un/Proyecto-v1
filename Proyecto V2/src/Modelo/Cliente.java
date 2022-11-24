package Modelo;
import java.util.ArrayList;

public class Cliente {
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private static boolean activo=true;
    private final ArrayList<Arriendo> arriendos;

    public Cliente (String rut, String nom, String dir, String tel){
        this.rut=rut;
        this.nombre=nom;
        this.direccion=dir;
        this.telefono=tel;
        arriendos=new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public static boolean isActivo(){

        return activo;
    }
    public static void setActivo(){
        activo = true;
    }
    public static void setInactivo(){
        activo = false;
    }
    public void addArriendo(Arriendo arriendo){
        arriendos.add(arriendo);
    }
    public Arriendo[] getArriendosPorDevolver(){
        ArrayList<Arriendo> entregados=new ArrayList<>();
        for(Arriendo arriendo: arriendos){
            if(arriendo.getEstado()==EstadoArriendo.ENTREGADO){
                entregados.add(arriendo);
            }
        }
        Arriendo [] listaEntregados = new Arriendo [entregados.size()];
        for(int i=0;i<arriendos.size();i++){
            listaEntregados[i]=entregados.get(i);
        }
        return listaEntregados;
    }
}
