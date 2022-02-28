package reciclaje.clases;
/*
package reciclaje.interconexion;

import java.io.DataOutputStream;

public class Cliente extends ConexionVentana{
	public Cliente() {
		super(false);
		iniciarCliente();
	}
	
	public void iniciarCliente(){
		try {
			super.setSalidaServidor(new DataOutputStream(super.getSocketCliente().getOutputStream()));
			
			
			for (int i = 0; i < 4; i++) {
				super.getSalidaServidor().writeUTF("Mensaje "+i);				
			}
		
			
			//super.getSalidaServidor().writeUTF("mensaje");
			
			super.getSocketCliente().close();
			
		} catch (Exception e) {
			System.out.println("Error tratando en enviar mensaje a servidor "+e.getMessage());
		}
	}

}
*/