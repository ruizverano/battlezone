package conexion;

import java.net.Socket;

import campoBatalla.Cuadro;

public class Cliente {

	private String host;
	private static final int PUERTO = 5000;
	private Socket skCliente;

	HiloEnviar hiloEnviar;
	HiloRecibir hiloRecibir;

	public Cliente(String servidor) {				
		
		System.out.println("*Cliente*");
		this.host = servidor;
		try {
			this.skCliente = new Socket(this.host, PUERTO);
			System.out.println("conectado a servidor");
			
			this.hiloRecibir = new HiloRecibir(this.skCliente);		
			this.hiloRecibir.start();
			this.hiloEnviar = new HiloEnviar(this.skCliente);
			this.hiloEnviar.start();

		} catch (Exception e) {
			System.out.println("error en cliente " + e.getMessage());
		}
	}

	public void enviarObjeto(Object objeto){		
		hiloEnviar.setObjeto(objeto);
	}
	
	public void activarHilos2() {

		this.hiloRecibir = new HiloRecibir(skCliente);
		this.hiloRecibir.start();
	}
}
