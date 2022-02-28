/*
package almacenamiento;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class GuardarGanadores {
	
	private String nombre;
	private int puntaje;
	private RandomAccessFile archivo;
	
	public GuardarGanadores(String nombre, int puntaje) {
		this.nombre=nombre;
		this.puntaje=puntaje;
	}
	
	public void guardarArchivo_Flujo(){
		try {
			BufferedWriter escribeArchivo = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream("src/almacenamiento/archivosJuego/listaGanadores.txt")
							)
					)
					;
			escribeArchivo.write("| Nombre: "+this.nombre+" |");
			escribeArchivo.write(" -- ");
			escribeArchivo.write("| Puntaje: "+this.puntaje+" |");
			
			System.out.println("Se guardo lista ganadores");
			
			escribeArchivo.close();
			
		} catch (Exception e) {
			System.out.println("Se produjo un error en guardarGanadores "+e.getMessage());
		}
	}
	
	public void escribirArchivo_Aleatorio(){
		try {
			this.archivo = new RandomAccessFile("src/almacenamiento/archivosJuego/listaGanadores.txt", "rw");
			
			this.archivo.seek(this.archivo.length());
			//this.archivo.writeUTF(this.nombre);
			this.archivo.writeInt(this.puntaje);
			this.archivo.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error en acceder a archivo para escribir "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error en posicion archivo para escribir"+e.getMessage());
			e.printStackTrace();
		}		
	}
		
}
*/