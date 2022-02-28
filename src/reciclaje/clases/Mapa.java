/*
package reciclaje.clases;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import juego.Juego;
import juego.Jugador;
import personajes.Unidad;
 
public class Mapa implements Serializable{	
	
	private int filas;		

	private int columnas;
	
	private int tamañoCuadroX;
	private int tamañoCuadroY;
	
	public static JPanel pnlCampo= new JPanel();
	
	private JFrame frmCampo = new JFrame("Battle Zone_1.0");		
	
	public static Cuadro[][] cuadro;
	
	private Unidad unidad;
	
	private static boolean seleccion;
	
	private static Unidad unidadSeleccionada;
		
	
	public Mapa(int filas, int columnas, int tamañoCuadroX, int tamañoCuadroY) {			
		
		
		this.filas=filas;
		this.columnas=columnas;						
		this.tamañoCuadroX=tamañoCuadroX;
		this.tamañoCuadroY=tamañoCuadroY;
		
		GridLayout layout = new GridLayout();
		layout.setColumns(columnas);
		layout.setRows(filas);
		
		pnlCampo.setLayout(layout);									
		pnlCampo.removeAll();				
		
		cargarEscenario();
													
	}
		
	public void montarEscenario(){
		frmCampo.add(pnlCampo).setVisible(true);				
		frmCampo.setSize(this.tamañoCuadroX*this.columnas,this.tamañoCuadroY*this.filas);		
		frmCampo.setVisible(true);
		frmCampo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCampo.setLocationRelativeTo(null);
	}
	
	public void duplicarEscenario(){
		new JFrame();		
	}
	
	public void cargarEscenario(){				
		
		cuadro = new Cuadro[this.filas][this.columnas];
		Random rnd = new Random();
		
		int aleatorioX;
		int aleatorioY;
		
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				
				aleatorioX=(rnd.nextInt(this.filas));
				aleatorioY=(rnd.nextInt(this.columnas));
				
				cuadro[i][j] = new Cuadro(this.tamañoCuadroX,this.tamañoCuadroY,i,j);							
				
				cuadro[i][j].setImgPiso("pisoLibre7Barricada");								

				//pinta obstaculos en cuadros aleatoriamente				
				if(i==aleatorioX || j==aleatorioY){
					cuadro[i][j].setImgPiso("obstaculo1");
					cuadro[i][j].setPisoObstaculo(true);
				}
								
				pnlCampo.add(cuadro[i][j]);
			}					
		}
		montarEscenario();
	}	
	
	public void posicionarUnidad(int posX, int posY, Unidad unidad){

	}	
	
	public static void setUnidaSeleccionada(Unidad unidad){		
		unidadSeleccionada=unidad;		
		System.out.println("unidad seleccionada "+unidad.getPosX() + ","+unidad.getPosY());
		seleccion=true;
		
		Control.lblNombreUnidad.setText("-Unidad: "+ unidad.getNombre());
		Control.lblSaludUnidad.setText("-Salud: "+ unidad.getSalud());
		Control.lblCostoUnidad.setText("-Costo: "+ unidad.getCosto());
		Control.lblDesplazamientoUnidad.setText("-Desplazamiento: "+ unidad.getDesplazamiento()+" sectores");
		Control.lblAlcanceFuegoUnidad.setText("-Alcance Fuego: "+ unidad.getAlcanceFuego()+" sectores");
		
		//mostrarLibre();
	}
	
	public static Unidad getUnidadSeleccionada(){
		return unidadSeleccionada;
	}
	
	public static Cuadro[][] getCuadro(){
		return cuadro;
	}
	
	public static void quitar(int x, int y){		
		cuadro[y][x].setImgPiso("pisoConquistado"+unidadSeleccionada.getIdJugador()); //aqui debe cambiar el color de la barricada segun la unidad que conquisto este cuadro
		cuadro[y][x].setPisoLibre(true);
		cuadro[y][x].setUnidad(null);
		cuadro[y][x].repaint();
		System.out.println("se quito: ("+x+","+y+")");		
	}
	
	
public static void mover(int idX, int idY, int direccionMovimiento){
		
		System.out.println("funcion mover() desde " + Mapa.getUnidadSeleccionada().getPosX() + "," + Mapa.getUnidadSeleccionada().getPosY());
		
		Unidad unidad=getUnidadSeleccionada();			
		
		switch (direccionMovimiento) {
		case 1:						
			for (int i = unidad.getPosY()-1; i > idY-1; i--) {				
				if(cuadro[i][idX].getPisoLibre()){		
					moverVertical(unidad,cuadro[i][idX], idX, i);					
				}else{
					i=idY;
				}														
			}
			Juego.cambiarTurno();
			break;
		case 2:			
			for (int i = unidad.getPosY()+1; i < idY+1; i++) {				
				if(cuadro[i][idX].getPisoLibre()){					
					moverVertical(unidad,cuadro[i][idX], idX, i);
				}else{
					i=idY;
				}														
			}
			Juego.cambiarTurno();
			break;
		case 3:			
			for (int i = unidad.getPosX()-1; i > idX-1; i--) {				
				if(cuadro[idY][i].getPisoLibre()){					
					moverHorizontal(unidad, cuadro[idY][i], idY, i);
				}else{
					i=idX;
				}														
			}
			Juego.cambiarTurno();
			break;
			
		case 4:
			for (int i = unidad.getPosX()+1; i < idX+1; i++) {				
				if(cuadro[idY][i].getPisoLibre()){					
					moverHorizontal(unidad, cuadro[idY][i], idY, i);
				}else{
					i=idX;
				}														
			}
			Juego.cambiarTurno();
			break;
		case 5:
			if(unidad.getPosX()<idX && unidad.getPosY()>idY){
				for (int i = unidad.getPosX()+1; i < idX+1; i++) {
					for (int j = unidad.getPosY()-1; j > idY-1; j--) {						
						if(cuadro[j][i].getPisoLibre()){								
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i++;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()>idX && unidad.getPosY()>idY){
				
				for (int i = unidad.getPosX()-1; i > idX-1; i--) {
					for (int j = unidad.getPosY()-1; j > idY-1; j--) {						
						if(cuadro[j][i].getPisoLibre()){								
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i--;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()>idX && unidad.getPosY()<idY){
				for (int i = unidad.getPosX()-1; i > idX-1; i--) {
					for (int j = unidad.getPosY()+1; j < idY+1; j++) {						
						if(cuadro[j][i].getPisoLibre()){								
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i--;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()<idX && unidad.getPosY()<idY){
				for (int i = unidad.getPosX()+1; i < idX+1; i++) {
					for (int j = unidad.getPosY()+1; j < idY+1; j++) {						
						if(cuadro[j][i].getPisoLibre()){							
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i++;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			Juego.cambiarTurno();
			break;

		default:
			System.out.println("Movimiento Erroneo");
			break;
		}					
		
		Mapa.setSeleccion(false);		
	}

public static void moverVertical(Unidad unidad, Cuadro cuadro, int idX,  int i){
	Mapa.quitar(unidad.getPosX(), unidad.getPosY());
	unidad.avanzar(idX, i);
	cuadro.ocupar(unidad);	
	try {		
		Thread.sleep(500);		
		
	} catch (InterruptedException e) {					
		System.out.println("Error en hilo ");
		e.printStackTrace();
	}
}

public static void moverHorizontal(Unidad unidad, Cuadro cuadro, int idY,  int i){
	Mapa.quitar(unidad.getPosX(), unidad.getPosY());
	unidad.avanzar(i, idY);
	cuadro.ocupar(unidad);	
	try {		
		Thread.sleep(500);		
		
	} catch (InterruptedException e) {					
		System.out.println("Error en hilo ");
		e.printStackTrace();
	}
}

public static void moverDiagonal(Unidad unidad, Cuadro cuadro, int idX, int idY,  int i, int j){
	Mapa.quitar(unidad.getPosX(), unidad.getPosY());
	unidad.avanzar(i, j);
	cuadro.ocupar(unidad);	
	try {		
		Thread.sleep(500);		
		
	} catch (InterruptedException e) {					
		System.out.println("Error en hilo ");
		e.printStackTrace();
	}
}
	

public static void disparar(int idX, int idY, int direccionFuego){
		
		System.out.println("funcion disparar() desde " + Mapa.getUnidadSeleccionada().getPosX() + "," + Mapa.getUnidadSeleccionada().getPosY());
		
		Unidad unidad=getUnidadSeleccionada();
		
		switch (direccionFuego) {
		case 1:			
			for (int i = unidad.getPosY()-1; i > idY-1; i--) {
				
				if(!cuadro[i][idX].getPisoObstaculo()){
					if(cuadro[i][idX].getUnidad()!=null){
						atacarVertical(unidad, cuadro[i][idX], idX, i);
						/*
						unidad.atacar(cuadro[i][idX].getUnidad());															
						System.out.println("Salud de esta unidad "+cuadro[i][idX].getUnidad().getSalud());																																											
						
						if(cuadro[i][idX].getUnidad().getSalud()<1){							
							//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
							System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
							quitar(cuadro[i][idX].getUnidad().getPosX(),cuadro[i][idX].getUnidad().getPosY());
						}
						*//*
					}					
				}
				else{
					i=idY;
				}
			}
			Juego.cambiarTurno();
			break;
		case 2:			
			for (int i = unidad.getPosY()+1; i < idY+1; i++) {				
				if(!cuadro[i][idX].getPisoObstaculo()){	
					if(cuadro[i][idX].getUnidad()!=null){
						atacarVertical(unidad, cuadro[i][idX], idX, i);
						/*
					unidad.atacar(cuadro[i][idX].getUnidad());															
					System.out.println("Salud de esta unidad "+cuadro[i][idX].getUnidad().getSalud());																																											
					if(cuadro[i][idX].getUnidad().getSalud()<1){						
						//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
						System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
						quitar(cuadro[i][idX].getUnidad().getPosX(),cuadro[i][idX].getUnidad().getPosY());
					}
					*/	/*				
					}					
				}else{
					i=idY;
				}														
			}	
			Juego.cambiarTurno();
			break;
		case 3:			
			for (int i = unidad.getPosX()-1; i > idX-1; i--) {
				if(!cuadro[idY][i].getPisoObstaculo()){									
					if(cuadro[idY][i].getUnidad()!=null){
						atacarHorizontal(unidad, cuadro[idY][i], idY, i);
						/*
					unidad.atacar(cuadro[idY][i].getUnidad());															
					System.out.println("Salud de esta unidad "+cuadro[idY][i].getUnidad().getSalud());																																											
					if(cuadro[idY][i].getUnidad().getSalud()<1){						
						//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
						System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
						quitar(cuadro[idY][i].getUnidad().getPosX(),cuadro[idY][i].getUnidad().getPosY());
					}
					*//*		
					}
				}else{
					i=idX;
				}														
			}	
			Juego.cambiarTurno();
			break;
			
		case 4:
			for (int i = unidad.getPosX()+1; i < idX+1; i++) {				
				if(!cuadro[idY][i].getPisoObstaculo()){
					if(cuadro[idY][i].getUnidad()!=null){	
						atacarHorizontal(unidad, cuadro[idY][i], idY, i);
						/*
						unidad.atacar(cuadro[idY][i].getUnidad());															
						System.out.println("Salud de esta unidad "+cuadro[idY][i].getUnidad().getSalud());																																											
					if(cuadro[idY][i].getUnidad().getSalud()<1){						
						//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
						System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
						quitar(cuadro[idY][i].getUnidad().getPosX(),cuadro[idY][i].getUnidad().getPosY());
					}		
					*/		/*					
					}
				}else{
					i=idX;
				}														
			}	
			Juego.cambiarTurno();
			break;
		case 5:
			if(unidad.getPosX()<idX && unidad.getPosY()>idY){
				for (int i = unidad.getPosX()+1; i < idX+1; i++) {
					for (int j = unidad.getPosY()-1; j > idY-1; j--) {
						
						if(!cuadro[j][i].getPisoObstaculo()){
							if(cuadro[j][i].getUnidad()!=null){
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
								/*
								unidad.atacar(cuadro[j][i].getUnidad());
							System.out.println("Salud de esta unidad "+cuadro[j][i].getUnidad().getSalud());
							if(cuadro[j][i].getUnidad().getSalud()<1){								
								//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
								System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
								quitar(cuadro[j][i].getUnidad().getPosX(),cuadro[j][i].getUnidad().getPosY());
							}
							*//*
						}
							i++;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()>idX && unidad.getPosY()>idY){
				
				for (int i = unidad.getPosX()-1; i > idX-1; i--) {
					for (int j = unidad.getPosY()-1; j > idY-1; j--) {						
						if(!cuadro[j][i].getPisoObstaculo()){
							if(cuadro[j][i].getUnidad()!=null){
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
								/*
								unidad.atacar(cuadro[j][i].getUnidad());
							System.out.println("Salud de esta unidad "+cuadro[j][i].getUnidad().getSalud());
							if(cuadro[j][i].getUnidad().getSalud()<1){								
								//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
								System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
								quitar(cuadro[j][i].getUnidad().getPosX(),cuadro[j][i].getUnidad().getPosY());
							}
							*//*
						}
							i--;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()>idX && unidad.getPosY()<idY){
				for (int i = unidad.getPosX()-1; i > idX-1; i--) {
					for (int j = unidad.getPosY()+1; j < idY+1; j++) {						
						if(!cuadro[j][i].getPisoObstaculo()){
							if(cuadro[j][i].getUnidad()!=null){
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
								/*
								unidad.atacar(cuadro[j][i].getUnidad());
							System.out.println("Salud de esta unidad "+cuadro[j][i].getUnidad().getSalud());
							if(cuadro[j][i].getUnidad().getSalud()<1){								
								//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
								System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
								quitar(cuadro[j][i].getUnidad().getPosX(),cuadro[j][i].getUnidad().getPosY());
							}
							*//*
						}
							i--;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}
			else if(unidad.getPosX()<idX && unidad.getPosY()<idY){
				for (int i = unidad.getPosX()+1; i < idX+1; i++) {
					for (int j = unidad.getPosY()+1; j < idY+1; j++) {						
						if(!cuadro[j][i].getPisoObstaculo()){
							if(cuadro[j][i].getUnidad()!=null){
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
								/*
								unidad.atacar(cuadro[j][i].getUnidad());
							System.out.println("Salud de esta unidad "+cuadro[j][i].getUnidad().getSalud());
							if(cuadro[j][i].getUnidad().getSalud()<1){								
								//Juego.darPuntos(Juego.jugadorEnTurno, cuadro[i][idX].getUnidad().getIdUnidad());
								System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
								quitar(cuadro[j][i].getUnidad().getPosX(),cuadro[j][i].getUnidad().getPosY());
							}
							*//*
						}
							i++;
						}else{
							j=idY;
							i=idX;
						}
					}
																			
				}
			}		
			Juego.cambiarTurno();
			break;

		default:
			break;
		}					
		
		Mapa.setSeleccion(false);		
	}

public static void atacarVertical(Unidad unidad, Cuadro cuadro, int idX,  int i){
	unidad.atacar(cuadro.getUnidad());															
	System.out.println("Salud de esta unidad "+cuadro.getUnidad().getSalud());																																											
	
	if(cuadro.getUnidad().getSalud()<1){							
		Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());
		System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
		quitar(cuadro.getUnidad().getPosX(),cuadro.getUnidad().getPosY());
	}
}

public static void atacarHorizontal(Unidad unidad, Cuadro cuadro, int idY,  int i){
	unidad.atacar(cuadro.getUnidad());															
	System.out.println("Salud de esta unidad "+cuadro.getUnidad().getSalud());																																											
	if(cuadro.getUnidad().getSalud()<1){						
		Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());
		System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
		quitar(cuadro.getUnidad().getPosX(),cuadro.getUnidad().getPosY());
	}
}

public static void atacarDiagonal(Unidad unidad, Cuadro cuadro, int idX, int idY,  int i, int j){
	unidad.atacar(cuadro.getUnidad());
	System.out.println("Salud de esta unidad "+cuadro.getUnidad().getSalud());
	if(cuadro.getUnidad().getSalud()<1){								
		Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());
		System.out.println(Juego.jugadorEnTurno.getNombre()+ " tiene "+Juego.jugadorEnTurno.getPuntaje() + " Puntos");
		quitar(cuadro.getUnidad().getPosX(),cuadro.getUnidad().getPosY());
	}
}

public int getFilas() {
	return filas;
}

public void setFilas(int filas) {
	this.filas = filas;
}

public int getColumnas() {
	return columnas;
}

public void setColumnas(int columnas) {
	this.columnas = columnas;
}

public int getTamañoCuadroX() {
	return tamañoCuadroX;
}

public void setTamañoCuadroX(int tamañoCuadroX) {
	this.tamañoCuadroX = tamañoCuadroX;
}

public int getTamañoCuadroY() {
	return tamañoCuadroY;
}

public void setTamañoCuadroY(int tamañoCuadroY) {
	this.tamañoCuadroY = tamañoCuadroY;
}

public JFrame getFrmCampo(){
	return this.frmCampo;
}

public static void setSeleccion(boolean aSeleccion) {
	seleccion=aSeleccion;			
}

public static boolean getSeleccion(){
	return seleccion;
}

}
*/