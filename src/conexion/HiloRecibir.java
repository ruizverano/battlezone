package conexion;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import campoBatalla.Accion;
import campoBatalla.Control;
import campoBatalla.Mapa;
import campoBatalla.Obstaculos;
import imagenes.Imagenes;
import juego.Juego;
import juego.Jugador;
import principal.JugarContraHumano;
import principal.Principal;

public class HiloRecibir implements Runnable {
	private InputStream entrada;
	private DataInputStream flujoEntradaString;
	private ObjectInputStream flujoEntradaObjeto;
	private Socket socket;

	private String texto="";

	private Object objeto;

	private Thread hiloRecibir;

	public HiloRecibir(Socket socket) {
		this.socket = socket;
		this.hiloRecibir = new Thread(this, "Hilo Recibir");
	}

	public void recibirDato() {
		System.out.println("Recibiendo dato...");
		try {
			this.entrada = socket.getInputStream();						
			try {				
				
				do {
					try {
						this.flujoEntradaObjeto = new ObjectInputStream(this.entrada);
						this.objeto = flujoEntradaObjeto.readObject();							
					} catch (Exception e) {
						new Accion().imprimirFinConexion();				
						break;
					}															
					
					System.out.println("Dato recibido de " + this.socket.getInetAddress());
					System.out.println("objeto: "+this.objeto.getClass().getSimpleName());
					
					if(this.objeto.getClass().getSimpleName().equals("Juego")){
						recibirObjeto_Juego();
																				
					}else if(this.objeto.getClass().getSimpleName().equals("Accion")){						
						recibirObjeto_Accion();
					
					}else if(this.objeto.getClass().getSimpleName().equals("Humano")){						
						recibirObjeto_Jugador();					
					}else if(this.objeto.getClass().getSimpleName().equals("String")){						
						System.out.println("Se recibio un string "+this.objeto);
						
					}else {
						System.out.println("no es ninguno");
					}
				} while (true);																			
				
			} catch (Exception e) {
				System.out.println("Error en HiloRecibir Flujo: " + e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Error recibiendo Dato: " + e.getMessage());
		}	
	}
	
	public void recibirObjeto_Juego() {
		System.out.println("Recibiendo Objeto Juego...");
		try {						
			Juego.juego = (Juego) this.objeto;
			Juego.juego.iniciarJugadorHumano2(Principal.getNombreJugador());
			Obstaculos obstaculos = Juego.juego.getMapa().getObstaculos();															
			
			Juego.juego.crearMapa();
			Juego.juego.getMapa().obstaculizarPisos(obstaculos);	
			new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
			
			String msg="hola servidor";			
			
			JugarContraHumano.cliente.enviarObjeto(msg);
			JugarContraHumano.cliente.enviarObjeto(Juego.juego.getJugador()[1]);
			
			//this.socket.close();
		} catch (Exception e) {
			System.out.println("Error en Hilo Recibir Objeto_Juego: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void recibirObjeto_Jugador() {
		System.out.println("Recibiendo Objeto Jugador...");
		try {					
			Juego.juego.setJugador((Jugador)this.objeto,1);
		} catch (Exception e) {
			System.out.println("Error en Hilo Recibir Objeto_Jugador: " + e.getMessage());
			e.printStackTrace();
		}
	}	
	
	public void recibirObjeto_Accion(){
		System.out.println("Recibiendo Objeto Accion...");
		try {
			Accion accion = (Accion)this.objeto;				
			switch (accion.getIdAccion()) {
			case 1:
				new Accion(accion.getCuadro()).crearUnidad();
				break;
			case 2:
				new Accion(accion.getCuadro()).moverUnidad();
				break;
			case 3:
				new Accion(accion.getCuadro()).atacarUnidad();
				break;
			case 4:
				new Accion(accion.getCuadro()).seleccionarUnidad();
				break;
			case 5:
				new Accion(accion.getSorteo()).otorgarTurnoSorteado();
				break;
			case 6:
				new Accion().cambiarTurno();
				break;
			case 7:
				new Accion(accion.getJugador()).imprimirGanador();
				break;
			default:
				break;
			}	
		} catch (Exception e) {
			System.out.println("Error en metodo recibir objeto_Accion() "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void start() {
		this.hiloRecibir.start();
	}

	@Override
	public void run() {
		recibirDato();
	}
	
	public void recibirTexto() {		
		System.out.println("Recibiendo Texto...");		
			try {
				do {
					this.texto = flujoEntradaString.readUTF();
					System.out.println("Dato recibido de " + this.socket.getInetAddress() + " " + this.texto);
				} while (!this.texto.equals("salir"));

				this.socket.close();
			} catch (Exception e) {
				System.out.println("Error en Hilo Recibir Texto: " + e.getMessage());
			}

	}
}
