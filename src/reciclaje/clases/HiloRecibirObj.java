/*package conexion;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import juego.Juego;

public class HiloRecibirObj implements Runnable{
	private InputStream entrada;
	private ObjectInputStream info;
	private Socket socket;
	private Object obj;
	
	//public static Juego juego;
	private Thread hiloRecibir;
	
	public HiloRecibirObj(Socket socket) {		
		this.socket=socket;
		this.hiloRecibir= new Thread(this,"Hilo Recibir");	
	}
	
	public void recibirDato(){
		System.out.println("Recibiendo...");
		try {
			this.entrada=socket.getInputStream();
				try{
					this.info=new ObjectInputStream(this.entrada);
						
						 this.obj= info.readObject();
						 System.out.println("Dato recibido de "+this.socket.getInetAddress() + " "+ this.obj);										
						
						 Juego.juego = (Juego)obj;
						 
						 //Juego.juego.getMapa().start();
						 //new Juego().setMapa(Juego.juego.getMapa());
						 Juego.juego.getMapa();
						 //Juego.juego.getMapa().d;uplicarEscenario();
						 //Juego.juego.iniciarJugador("ingrid");
						 
					this.socket.close();
				}catch (Exception e) {
					System.out.println("Error en HiloRecibir "+ e.getMessage());
				}
		}catch (Exception e) {
			System.out.println("Error recibiendo "+e.getMessage());
		}
	
	}
	
	public void start(){
		this.hiloRecibir.start();
	}

@Override
public void run() {
	recibirDato();	
}
}
*/
