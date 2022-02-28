package campoBatalla;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JOptionPane;

import almacenamiento.GestionArchivoGanadores;
import imagenes.Imagenes;
import juego.Juego;
import juego.Jugador;
import juego.Maquina;

public class Accion implements Serializable{
	
	private int idAccion;	
	private Cuadro cuadro;
	
	private int sorteo;
	
	private Jugador jugador;
	
	public Accion(Cuadro cuadro) {
		this.cuadro= cuadro;
	}
	
	public Accion() {
	}
	
	public Accion(int sorteo) {
		this.sorteo=sorteo;
	}
		
	public Accion(Jugador jugador) {
		this.jugador=jugador;
	}
	
	public void crearUnidad(){
		this.idAccion=1;
		if(Control.nombreUnidad!=null){
			this.cuadro.crearUnidad(Control.nombreUnidad);
		}else{
			Mapa.cuadro[this.cuadro.getIdY()][this.cuadro.getIdX()].ocupar(this.cuadro.getUnidad());
		}
		Control.setReclutar(false);
		Control.nombreUnidad=null;
		//Juego.cambiarTurno();
	}
	
	public void moverUnidad(){
		this.idAccion=2;
		int direccionMovimiento=Mapa.getUnidadSeleccionada().validarAvance(this.cuadro.getIdX(), this.cuadro.getIdY());						
		Hilo hilo = new Hilo("mover", this.cuadro.getIdX(), this.cuadro.getIdY(), direccionMovimiento);					
		hilo.start();		
	}
	
	public void atacarUnidad(){
		this.idAccion=3;	
		if(this.cuadro.getUnidad()!=null){
			int direccionFuego=Mapa.getUnidadSeleccionada().validarAtaque(this.cuadro.getUnidad());		
			Mapa.getMapa().disparar(this.cuadro.getIdX(), this.cuadro.getIdY(), direccionFuego);
		}else{
			Mapa.getMapa().quitar(this.cuadro.getIdX(), this.cuadro.getIdY());
		}
	}	
	
	public void seleccionarUnidad(){
		this.idAccion=4;
		this.cuadro.seleccionarUnidad();		
	}			
	
	public void sortearTurno(){
		this.idAccion=5;		
		Random rn = new Random();
		this.sorteo = rn.nextInt(2);				
	}
	
	public void otorgarTurnoSorteado(){
		Juego.juego.getJugador()[this.sorteo].setTurno(true);	
		Juego.juego.getJugador()[this.sorteo].getControl().setHabilitarControl(true);
		Juego.setJugadorEnTurno(Juego.juego.getJugador()[this.sorteo]);
		JOptionPane.showMessageDialog(null, "Empieza el Jugador: " + 
				Juego.juego.getJugador()[this.sorteo].getNombre(), "Sorteo de turno",
				JOptionPane.INFORMATION_MESSAGE);
		Juego.juego.getJugador()[this.sorteo].setTurno(true);
	}
	
	public void cambiarTurno(){
		this.idAccion=6;
		System.out.println("turneando jugadores");
			if(Juego.juego.getJugador()[0].getTurno()){				
				Juego.juego.setJugadorEnTurno(Juego.juego.getJugador()[1]);				
				Juego.juego.getJugador()[0].setTurno(false);
				Juego.juego.getJugador()[0].getControl().setHabilitarControl(false);			
				Juego.juego.getJugador()[1].setTurno(true);
				Juego.juego.getJugador()[1].getControl().setHabilitarControl(true);
				
				if(!Juego.juego.getJugador()[1].getHumano()){
					System.out.println("es una maquina");
					new Thread(new Runnable() {						
						@Override
						public void run() {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								System.err.println("error esperando turno null");
							}
							Maquina.accionMaquina();							
						}
					},"hiloMaquina").start();;
				}
			}else{
				Juego.juego.setJugadorEnTurno(Juego.juego.getJugador()[0]);				
				Juego.juego.getJugador()[1].setTurno(false);
				Juego.juego.getJugador()[1].getControl().setHabilitarControl(false);
				Juego.juego.getJugador()[0].setTurno(true);
				Juego.juego.getJugador()[0].getControl().setHabilitarControl(true);
			}
	}
	
	public void ganar(){
		this.idAccion=7;		
		this.getJugador().setPuntaje(this.getJugador().getPuntaje()+150);
		new GestionArchivoGanadores(this.getJugador().getNombre(), this.getJugador().getPuntaje()).escribirArchivo_Aleatorio();
	}
	
	public void imprimirZonaObstaculo(){
		JOptionPane.showMessageDialog(null,"Zona Obstaculizada",
				"Comunicado",JOptionPane.INFORMATION_MESSAGE);		
		
		System.out.println("Zona obstaculo");
		
		Mapa.setSeleccion(false);
	}
	
	public void imprimirZonaOcupada(){
		JOptionPane.showMessageDialog(null,"Zona Ocupada",
				"Comunicado",JOptionPane.INFORMATION_MESSAGE);
				
		System.out.println("Zona ocupada");
		
		Mapa.setSeleccion(false);
	}
	
	public void imprimirZonaLibre(){
		JOptionPane.showMessageDialog(null,"Zona Libre",
				"Comunicado",JOptionPane.INFORMATION_MESSAGE);		
		
		System.out.println("Zona libre");
		
		Mapa.setSeleccion(false);
	}
	
	public void imprimirNoTurno(){
		JOptionPane.showMessageDialog(null,"No es tu turno",
				"Comunicado",JOptionPane.ERROR_MESSAGE);		
		
		System.out.println("No es tu turno");		
		
		Mapa.setSeleccion(false);
	}
	
	public void imprimirAutoAtaque(){
		JOptionPane.showMessageDialog(null,"Estas tratando de auto-atacarte!",
				"Comunicado",JOptionPane.ERROR_MESSAGE);	
		
		System.out.println("Estas tratando de autoatacarte!");
		
		Mapa.setSeleccion(false);
	}	
	
	public void imprimirFinConexion(){
		JOptionPane.showMessageDialog(null,"Se terminó la conexion con tu oponente",
				"Comunicado",JOptionPane.INFORMATION_MESSAGE);	
		
		System.out.println("Conexion finalizada");
		
		Mapa.setSeleccion(false);
	}
	
	public void imprimirCreditos(){
		JOptionPane.showMessageDialog(null,
				"Software realizado por: Cristian Bernardo Ruiz Verano\n"+
				"Estudiante de Ingeniería de Sistemas U.A.M.\n"+
				"\n"+
				"Presentado al Docente: Jairo Velez\n"+
				"\n"+
				"Para su desarrollo se utilizó 100% herramientas libres\n"+
				"y propiedad intelectual meramente del desarrollador\n"+
				"05 de Diciembre de 2016\n",
				"Creditos",JOptionPane.INFORMATION_MESSAGE);									
	}
	
	public void imprimirRanking(){
		JOptionPane.showMessageDialog(null,
				"aqui debe ir los datos del ranking ya procesado",
				"Ranking",JOptionPane.INFORMATION_MESSAGE);									
	}
	
	public void imprimirRetirada(){				
		JOptionPane.showMessageDialog(null,"Acabas de rendirte, eres un cobarde!",
				"Retirada",JOptionPane.INFORMATION_MESSAGE);	
		
		System.out.println("Retirada");		
	}
	
	public void imprimirGanador(){				
		JOptionPane.showMessageDialog(null,this.jugador.getNombre()+
				" Felicitaciones!, has conseguido la victoria!\n"+
				"seras premiado con 150 puntos mas\n"+
				"y entrarás en la lista de triunfantes",
				"Victoria",JOptionPane.INFORMATION_MESSAGE);	
		
		System.out.println("Ganador: "+this.jugador.getNombre()+" puntaje: "+this.jugador.getPuntaje());		
	}
	
	public void canjearPuntos(int puntos){		
		Juego.jugadorEnTurno.setPresupuesto(Juego.jugadorEnTurno.getPresupuesto()+(puntos*3));
		Juego.jugadorEnTurno.getControl().lblCredito.setText("-Credito: $"+Juego.jugadorEnTurno.getPresupuesto());
		Juego.jugadorEnTurno.setPuntaje(Juego.jugadorEnTurno.getPuntaje()-puntos);
		Juego.jugadorEnTurno.getControl().lblPuntos.setText("-Puntos: "+Juego.jugadorEnTurno.getPuntaje());
	}
	
	public void comprarSalud(){
		boolean comprarSalud=false;
		if(Juego.jugadorEnTurno.getPresupuesto()>=125){
			for (int i = 0; i < Mapa.cuadro.length; i++) {
				for (int j = 0; j < Mapa.cuadro.length; j++) {
					if(Mapa.cuadro[i][j].getUnidad()!=null){
						if(Mapa.cuadro[i][j].getUnidad().getIdJugador()==Juego.jugadorEnTurno.getIdJugador()){							
							Mapa.cuadro[i][j].getUnidad().setSalud(10);
							comprarSalud=true;
						}
					}					
				}
			}
			if(comprarSalud){
				Juego.jugadorEnTurno.setPresupuesto(Juego.jugadorEnTurno.getPresupuesto()-125);
				JOptionPane.showMessageDialog(null,"Todas tus unidades quedaron full de salud",
						"Comunicado",JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null,"No posees presupuesto suficiente",
					"Comunicado",JOptionPane.ERROR_MESSAGE);
		}		
	}	
	
	public int getIdAccion(){
		return this.idAccion;
	}
	
	public Cuadro getCuadro(){
		return this.cuadro;
	}
	
	public int getSorteo(){
		return this.sorteo;
	}
	
	public Jugador getJugador(){
		return this.jugador;
	}
}