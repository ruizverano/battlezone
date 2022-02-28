/*package conexion;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import principal.JugarContraHumano;

public class HiloEnviarObj  implements Runnable{
	private OutputStream salida;
	private ObjectOutputStream info;
	private Socket socket;
	
	private Object obj;
	
	private Thread hiloEnviar;	
	
	Scanner sn= new Scanner(System.in);	
	
	public HiloEnviarObj(Socket socket) {			
		this.socket=socket;
		this.hiloEnviar= new Thread(this,"Hilo Enviar");	
	}
	
	public HiloEnviarObj(Socket socket,Object dato) {			
		this.socket=socket;
		this.hiloEnviar= new Thread(this,"Hilo Enviar");
		System.out.println(dato);
	}
	
	public void enviarDato(){
		System.out.println("Enviando...");
		try {			
			this.salida=this.socket.getOutputStream();
			this.info= new ObjectOutputStream(this.salida);
				
			
			this.obj= JugarContraHumano.juego;			
			
			this.info.writeObject(this.obj);
			
			System.out.println("se envio "+this.obj.getClass().getTypeName());

			this.socket.close();
		} catch (Exception e) {
			System.out.println("Error HiloEnviar.enviarDato "+e.getMessage());
		}
	}
	
	public void start(){
		this.hiloEnviar.start();
	}
	
	@Override
	public void run() {		
		enviarDato();		
	}
		

}
*/