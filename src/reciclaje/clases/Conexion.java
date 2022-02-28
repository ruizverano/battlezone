package reciclaje.clases;
/*
package interconexion;
//https://www.programarya.com/Cursos/Java-Avanzado/Sockets
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Conexion {
	private  final int PUERTO = 5000;
	private String ipHost="127.0.0.1";
	private static ServerSocket socketServidor;
	private static Socket socketCliente;
	
	protected String mensajeServidor;
	
	protected DataOutputStream salidaServidor;
	private DataOutputStream salidaCliente;

	public Conexion(boolean servidor) {				
		if(servidor){
			try {
				this.socketServidor = new ServerSocket(PUERTO);
				this.socketCliente = new Socket();
			} catch (IOException e) {
				System.out.println("Error inicializando servidor" + e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				this.socketCliente = new Socket(this.ipHost, PUERTO);
			} catch (UnknownHostException e) {
				System.out.println("Error1 inicializando cliente " +e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error2 inicializando cliente" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	protected void setIpHost(String ipHost){
		this.ipHost= ipHost;
	}
	
	public String getIpHost(){
		return this.ipHost;
	}
	
	protected void setSocketServidor(ServerSocket socketServidor){
		this.socketServidor=socketServidor;
	}
	
	public ServerSocket getSocketServidor(){
		return this.socketServidor;
	}
	
	protected void setSocketCliente(Socket socketCliente){
		this.socketCliente=socketCliente;
	}
	
	public Socket getSocketCliente(){
		return this.socketCliente;
	}
	
	protected void setSalidaServidor(DataOutputStream salidaServidor){
		this.salidaServidor=salidaServidor;
	}
	
	public DataOutputStream getSalidaServidor(){
		return this.salidaServidor;
	}
	
	protected void setSalidaCliente(DataOutputStream salidaCliente){
		this.salidaCliente=salidaCliente;
	}
	
	public DataOutputStream getSalidaCliente(){
		return this.salidaCliente;
	}
	
	protected void setMensajeServidor(String mensajeServidor){
		this.mensajeServidor= mensajeServidor;
	}
	
	public String getMensajeServidor(){
		return this.mensajeServidor;
	}
}
*/