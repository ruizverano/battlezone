package juego;

import java.io.Serializable;

import campoBatalla.Control;

public class Humano extends Jugador implements Serializable{

	//private static final long serialVersionUID = 1L;

	public Humano(String nombre, int idJugador, boolean humano, boolean turno, int puntaje, int presupuesto) {
	      super.setNombre(nombre);
	      super.setIdJugador(idJugador);
	      super.setHumano(humano);
	      super.setTurno(turno);	      
	      super.setPuntaje(puntaje);
	      super.setPresupuesto(presupuesto);
	      
	      super.setControl(new Control(this));
	  }	  
}