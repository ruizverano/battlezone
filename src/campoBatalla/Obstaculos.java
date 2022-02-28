package campoBatalla;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

public class Obstaculos implements Serializable{
	private int[] arregloX;
	private int[] arregloY;
	private int nArreglo;
	
	public Obstaculos(int nArreglo, int nX, int nY ) {
		this.nArreglo= nArreglo;
		arregloX = new int[this.nArreglo];
		arregloY = new int[this.nArreglo];
		
		Random rn = new Random();
		
		for (int i = 0; i < nArreglo; i++) {
			this.arregloX[i]= rn.nextInt(nX);
			this.arregloY[i]=rn.nextInt(nY);			
		}					
	}
	
	public int[] getArregloX(){
		return this.arregloX;
	}
	
	
	public int[] getArregloY(){	
		return this.arregloY;
	}
	
	public int getNArreglo(){
		return this.nArreglo;
	}
}	