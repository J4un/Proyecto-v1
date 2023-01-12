package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Conjunto extends Equipo implements Serializable{

    ArrayList <Equipo> equipos;

    public Conjunto (long cod, String desc){
        super(cod,desc);
        equipos=new ArrayList<>();
    }
    public long getPrecioArriendoDia(){
        long total = 0;
        for (Equipo equipo: equipos){
            total = total + equipo.getPrecioArriendoDia();
        }
        return total;
    }
    public void addEquipo(Equipo equipo){
        equipos.add(equipo);
    }
    public int getNroEquipos(){
        return equipos.size();
    }
}
