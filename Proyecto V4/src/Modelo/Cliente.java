package Modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable{
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean activo=true;
    private ArrayList <Arriendo> arriendos;

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
    public boolean isActivo(){

        return activo;
    }
    public void setActivo(){
        activo = true;
    }
    public void setInactivo(){
        activo = false;
    }
    public void addArriendo(Arriendo arriendo){
        arriendos.add(arriendo);

    }

    public Arriendo[] getArriendosPorDevolver() {
    	  ArrayList <Arriendo> porDevolver = new ArrayList<>();
    	  for (Arriendo arriendo : arriendos) {
    	    if (arriendo.getEstado() == EstadoArriendo.ENTREGADO) {
    	      porDevolver.add(arriendo);
    	    }
    	  }

    	  Arriendo[] arriendosPorDevolver = new Arriendo[porDevolver.size()];
    	  for (int i = 0; i < porDevolver.size(); i++) {
    	    arriendosPorDevolver[i] = porDevolver.get(i);
    	  }

    	  return arriendosPorDevolver;
    	}
}
