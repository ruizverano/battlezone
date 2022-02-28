package conexion;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HiloEnviar  implements Runnable{
	private OutputStream salida;
	private DataOutputStream flujoSalidaString;
	private ObjectOutputStream flujoSalidaObjeto;
	private Socket socket;
	
	private String texto="";
	
	private Object objeto;
	
	private Thread hiloEnviar;	
	
	Scanner sn= new Scanner(System.in);		

	public HiloEnviar(Socket socket) {
		this.socket=socket;
		this.hiloEnviar= new Thread(this,"Hilo Enviar");		
	}
		
	public void enviarObjeto_Juego(){			
		try {			
			this.salida=this.socket.getOutputStream();												
				while(true){		
				this.flujoSalidaObjeto= new ObjectOutputStream(this.salida);
				this.flujoSalidaObjeto.writeObject(this.objeto);			
				System.out.println("se envio "+this.objeto.getClass().getSimpleName());
				//this.flujoSalidaObjeto.reset();
				break;								
				}
			//this.socket.close();
		} catch (Exception e) {
			System.out.println("Error HiloEnviar.enviarObjeto_Juego "+e.getMessage());
			e.printStackTrace();			
		}
	}
	
	public void start(){
		this.hiloEnviar.start();
	}
	
	public void setObjeto(Object objeto){		
		this.objeto=objeto;
		enviarObjeto_Juego();
	}
	
	@Override
	public void run() {	
		//enviarObjeto_Juego();
		/*		
		if(this.objeto.getClass().getSimpleName().equals("String")){
			enviarTexto();
		}else if(this.objeto.getClass().getSimpleName().equals("Juego")){
			enviarobjeto_Juego();
		}
		*/
	}	
	
	public void enviarTexto(){
		System.out.println("Enviando Texto...");
		try {			
			this.salida=this.socket.getOutputStream();
			this.flujoSalidaString= new DataOutputStream(this.salida);
						
				while(!this.texto.equals("salir")){							
					this.texto = this.sn.nextLine();
					this.flujoSalidaString.writeUTF(this.texto);			
				}

			//this.socket.close();
		} catch (Exception e) {
			System.out.println("Error HiloEnviar.enviarDato "+e.getMessage());
		}
	}

}
