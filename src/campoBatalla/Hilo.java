package campoBatalla;

import java.io.Serializable;

import personajes.Unidad;

public class Hilo implements Runnable,Serializable{
	
	String nombre;	
	Thread hilo;
	
	int idX;
	int idY;
	int direccionMovimiento;	
	
	public Hilo(String nombre) {
		this.nombre=nombre;				
		this.hilo = new Thread(this,nombre);				
	}		
	
	public Hilo(String nombre, int idX, int idY, int direccionMovimiento) {		
		this.nombre=nombre;
		this.hilo = new Thread(this,nombre);		
		this.idX=idX;
		this.idY=idY;
		this.direccionMovimiento= direccionMovimiento;			
	}	
	
	public void start(){
		hilo.start();		
	}
	
	@Override
	public void run() {										
		Mapa.getMapa().mover(this.idX,this.idY,this.direccionMovimiento);		
	}		

}
