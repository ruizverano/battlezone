/*
package almacenamiento;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeerGanadores {
	
	private RandomAccessFile archivo;
	private static int[] listaPuntajeGanadores;
	private static String[] listaNombresGanadores;
	
	public LeerGanadores() {
		this.listaPuntajeGanadores= new int[10];
		this.listaNombresGanadores= new String[10];
	}		
	
	public void leerArchivo_Aleatorio(){		
		
		try {
			this.archivo= new RandomAccessFile("src/almacenamiento/archivosJuego/listaGanadores.txt", "r");
			this.archivo.seek(4);			
			
			for (int i = 0; i < 10; i++) {
				//this.listaPuntajeGanadores[i]=this.archivo.readInt();
				this.listaNombresGanadores[i]=this.archivo.readUTF();
			}
						
		} catch (FileNotFoundException e) {
			System.out.println("Error en acceder a archivo para leer "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error en posicion archivo para leer "+e.getMessage());
			e.printStackTrace();
		}
		
		for (int j = 0; j < listaPuntajeGanadores.length; j++) {
			System.out.println("lista "+listaPuntajeGanadores[j]);
		}
	}

}
*/