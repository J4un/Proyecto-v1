package Modelo;

import java.util.ArrayList;

public class Conjunto extends Equipo{
    ArrayList <Equipo> equipos;

    public Conjunto (long cod, String desc){
        super(cod,desc);
        equipos=new ArrayList<>();
    }
    public long getPrecioArriendoDia(){
        return super.precioArriendoDia;
    }
    public void addEquipo(Equipo equipo){
        equipos.add(equipo);
    }
    public int getNroEquipos(){
        return equipos.size();
    }
}
