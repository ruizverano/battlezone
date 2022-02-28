package juego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

import javax.sound.midi.Soundbank;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import almacenamiento.GuardarXML;
import campoBatalla.Control;
import campoBatalla.Mapa;
import personajes.Unidad;

public class Juego implements Serializable {

	private int idJuego;

	public static Jugador jugadorEnTurno;

	private Jugador[] jugador;
	public static int idJugador;

	private static GuardarXML xml;
	public static Juego juego;
	private Mapa mapa;

	public Juego() {
		jugador = new Jugador[2];
	}

	public void crearMapa() {
		mapa = new Mapa(20, 20, 35, 35);
	}

	public void iniciarJugadorHumano1(String nombre) {
		jugador[0] = new Humano(nombre, 1, true, false, 0, 500);
		idJugador = 1;	
	}

	public void iniciarJugadorHumano2(String nombre) {
		jugador[1] = new Humano(nombre, 2, true, false, 0, 500);
		idJugador = 2;		
	}
	
	public void iniciarJugadorMaquina() {
		jugador[1] = new Maquina(false, 0, 500);
		idJugador = 2;
	}

	public void guardarPartida_Xml() {
		System.out.println("guardando ...");
		xml = new GuardarXML(juego, mapa, mapa.getCuadro(), jugador);

	}

	public void cargarPartida() {

	}

	public void rankearPuntajes() {
	}

	public void iniciarJugador(int i, String nombreJugador, int idJugador, boolean humano) {
		try {
			jugador[i] = new Humano(nombreJugador, idJugador, humano, false, 0, 500);
		} catch (Exception e) {
			System.out.println("error creando jugador: " + e.getMessage());
		}
	}
/*
	public void sortearTurno() {
		Random rn = new Random();
		int sorteo = rn.nextInt(2);

		jugador[sorteo].setTurno(true);
		jugador[sorteo].getControl().setHabilitarControl(true);
		jugadorEnTurno = jugador[sorteo];

		JOptionPane.showMessageDialog(null, "Empieza el Jugador: " + jugador[sorteo].getNombre(), "Sorteo de turno",
				JOptionPane.INFORMATION_MESSAGE);

		jugador[sorteo].setTurno(true);
	}
*/
	public static void darPuntos(Jugador jugador, int idUnidad) {
		switch (idUnidad) {
		case 0:
			jugador.setPuntaje(jugador.getPuntaje() + 3);
			break;
		case 1:
			jugador.setPuntaje(jugador.getPuntaje() + 6);
			break;
		case 2:
			jugador.setPuntaje(jugador.getPuntaje() + 12);
			break;
		case 3:
			jugador.setPuntaje(jugador.getPuntaje() + 9);
			break;
		default:
			System.out.println("No existe la unidad");
			break;
		}
	}

	public int getIdJuego() {
		return this.idJuego;
	}

	public int getIdJugadorEnTurno() {
		return this.jugadorEnTurno.getIdJugador();
	}
	
	public String getNombreJugadorEnTurno(){
		return this.jugadorEnTurno.getNombre();
	}
	
	public static void setJugadorEnTurno(Jugador jugador) {
		jugadorEnTurno=jugador;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Jugador[] getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador, int i) {
		this.jugador[i] = jugador;
	}
	
	public void cambiarTurno(){
		System.out.println("turneando jugadores");
			if(jugador[0].getTurno()){				
				jugadorEnTurno=jugador[1];
				System.out.println("esta de turno el jugador "+jugadorEnTurno.getNombre());
				jugador[0].setTurno(false);
				jugador[0].getControl().setHabilitarControl(false);			
				jugador[1].setTurno(true);
				jugador[1].getControl().setHabilitarControl(true);
			}else{
				jugadorEnTurno=jugador[0];
				System.out.println("esta de turno el jugador "+jugadorEnTurno.getNombre());
				jugador[1].setTurno(false);
				jugador[1].getControl().setHabilitarControl(false);
				jugador[0].setTurno(true);
				jugador[0].getControl().setHabilitarControl(true);
			}
	}
}