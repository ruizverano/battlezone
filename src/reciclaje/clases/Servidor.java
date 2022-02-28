package reciclaje.clases;
/*
package interconexion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Servidor extends ConexionVentana {
	
	public Servidor() {
		super(true);	
		iniciarServidor();
	}
	
	public void iniciarServidor(){
		
		try {
			System.out.println("Servidor inicializado, Esperando conexion de cliente...");
			
			super.setSocketCliente(super.getSocketServidor().accept());
			
			System.out.println("Cliente conectado");
			
			super.setSalidaCliente(new DataOutputStream(super.getSocketCliente().getOutputStream()));
						
			BufferedReader entrada = new BufferedReader(new InputStreamReader(super.getSocketCliente().getInputStream()));
			
			setMensajeServidor(entrada.readLine());
			
			if(getMensajeServidor() != null){
				System.out.println("recibido: "+getMensajeServidor());				
			}
			
		} catch (Exception e) {
			System.out.println("Error en el servidor "+e.getMessage());
		}
	}		
}
*/
