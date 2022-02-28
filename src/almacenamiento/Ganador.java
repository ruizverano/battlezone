package almacenamiento;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import juego.Jugador;

public class Ganador extends Jugador implements Serializable{
	
	private Date fecha;
	private String fechaActual;	
	
	public Ganador(String nombre, int puntaje) {
		super.setNombre(nombre);
		super.setPuntaje(puntaje);
		
		this.fecha= new Date();
		this.fechaActual = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss").format(this.fecha);
	}
	
	public String getFechaActual(){
		return this.fechaActual;
	}
		
}
