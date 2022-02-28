package conexion;

import java.net.ServerSocket;
import java.net.Socket;

import juego.Juego;

public class Servidor {
	
	static final int PUERTO=5000;
	
	private ServerSocket skServidor;
	private Socket socket;
	
	private HiloEnviar hiloEnviar;
	private HiloRecibir hiloRecibir;
	
	public Servidor() {				
		
		System.out.println("*Servidor*");
		try {
			this.skServidor = new ServerSocket(PUERTO);
			
			System.out.println("Escucho el puerto "+ PUERTO );
										
				this.socket = this.skServidor.accept();
				System.out.println("cliente conectado");	
				
				this.hiloEnviar =  new HiloEnviar(this.socket);
				this.hiloEnviar.start();
				this.hiloRecibir = new HiloRecibir(this.socket);
				this.hiloRecibir.start();
												
		} catch (Exception e) {
			System.out.println("error en el servidor"+e.getMessage());
		}		
	}
		
	public void enviarObjeto(Object objeto){		
		hiloEnviar.setObjeto(objeto);
	}
	
	public void activarHilos2(){		
		this.hiloEnviar =  new HiloEnviar(socket);
		this.hiloEnviar.setObjeto(Juego.juego);
		hiloEnviar.start();				
		
		this.hiloRecibir = new HiloRecibir(socket);
		hiloRecibir.start();
	}
}
