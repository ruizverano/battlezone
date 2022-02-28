package almacenamiento;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class GestionArchivoGanadores {
	
	private Ganador objGanador;
	private ObjectOutputStream escribir;
	private ObjectInputStream leer;
	
	private String nombre;
	private int puntaje;
	private RandomAccessFile archivo;
	
	private static final String RUTA_ARCHIVO="src/almacenamiento/archivosJuego/listaGanadores.dat";
	
	public GestionArchivoGanadores() {
		
	}
	
	public GestionArchivoGanadores(String nombre, int puntaje) {
		this.objGanador= new Ganador(nombre, puntaje);
		
		this.nombre=nombre;
		this.puntaje=puntaje;
	}		
	
	/*public GestionArchivoGanadores(String nombre, int puntaje) {
		this.nombre=nombre;
		this.puntaje=puntaje;
	}*/
	
	public void escribirArchivo_Objeto(){
		try {
			this.escribir= new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO));
			
			this.escribir.writeObject(this.objGanador);
			this.escribir.writeObject(this.objGanador);
			this.escribir.writeObject(this.objGanador);
			this.escribir.close();
			
		} catch (Exception e) {
			System.out.println("error escribiendo archivo "+e.getMessage());
		}
	}
	
	public void leerArchivo_Objeto(){
		try {
			this.leer=new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO));
			this.objGanador=(Ganador)leer.readObject();
						
			System.out.println("ganador "+this.objGanador.getNombre()+" puntaje "+this.objGanador.getPuntaje()+" fecha "+this.objGanador.getFechaActual());
			
		} catch (Exception e) {
			System.out.println("error leyendo archivo "+e.getMessage());
		}
	}	
				
	public void guardarArchivo_Flujo(){
		try {
			BufferedWriter escribeArchivo = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(RUTA_ARCHIVO)
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
			this.archivo = new RandomAccessFile(RUTA_ARCHIVO, "rw");
			
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
		
		public void leerArchivo_Aleatorio(){		
			
			try {
				this.archivo= new RandomAccessFile(RUTA_ARCHIVO, "r");
				this.archivo.seek(4);							
							
			} catch (FileNotFoundException e) {
				System.out.println("Error en acceder a archivo para leer "+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error en posicion archivo para leer "+e.getMessage());
				e.printStackTrace();
			}						
		}
	
}
