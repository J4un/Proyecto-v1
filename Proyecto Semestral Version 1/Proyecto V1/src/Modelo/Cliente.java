package Modelo;

public class Cliente {
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private static boolean activo=true;

    public Cliente (String rut, String nom, String dir, String tel){
        this.rut=rut;
        this.nombre=nom;
        this.direccion=dir;
        this.telefono=tel;
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

}
