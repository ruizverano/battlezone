package imagenes;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import campoBatalla.Cuadro;
import campoBatalla.Mapa;
import juego.Juego;
import juego.Jugador;
import personajes.Unidad;

public class Imagenes implements Serializable{
	
	private transient ImageIcon imagen;	
	
	public static final String RUTA_PRINCIPAL="src/imagenes/estaticas/imagenInicial.jpg";
	
	public static final String RUTA_SI_TURNO="src/imagenes/estaticas/siTurno.png";
	public static final String RUTA_NO_TURNO="src/imagenes/estaticas/noTurno.png";	
	
	public static final String RUTA_LIBRE="src/imagenes/animados/zonaLibreBarricada.gif";		
	public static final String RUTA_OBSTACULIZADA="src/imagenes/animados/obstaculo.gif";
	public static final String RUTA_EXPLOSION="src/imagenes/animados/explosionUnidad.gif";
	
	public static final String RUTA_PUNTERO_DEFECTO="src/imagenes/iconos/cursorDefault.png";
	public static final String RUTA_PUNTERO_MIRA="src/imagenes/iconos/cursorAtaque.png";
	
	public String rutaOcupada;
	public int idJugador;
	
	private String rutaImagenUnidad;
	public String rutaIconoUnidad;	
	
	public String rutaConquista;			
		
	public Imagenes() {				
		
	}
	
	public Imagenes (int idJugador){
		this.idJugador=idJugador;
	}
	
	public Imagenes(int idJugador,Unidad unidad) {			
		this.rutaImagenUnidad="src/imagenes/animados/jugador"+idJugador+"/"+unidad.getNombre()+".gif";
	}	
	
	public Imagenes(int idJugador, String iconoUnidad){	
		
		this.idJugador=idJugador;
		
		switch (iconoUnidad) {
		case "infante":
			this.rutaIconoUnidad="src/imagenes/iconos/jugador"+this.idJugador+"/infante.png";
			break;
		case "lancero":
			this.rutaIconoUnidad="src/imagenes/iconos/jugador"+this.idJugador+"/lancero.png";
			break;
		case "lanzaCohetes":
			this.rutaIconoUnidad="src/imagenes/iconos/jugador"+this.idJugador+"/lanzaCohetes.png";
			break;
		case "tanque":
			this.rutaIconoUnidad="src/imagenes/iconos/jugador"+this.idJugador+"/tanque.png";
			break;
		default:
			break;
		}
	}	
	
	public void rastrearUnidades(){		
		
		for (int i = 0; i < Mapa.getCuadro().length; i++) {
			for (int j = 0; j < Mapa.getCuadro().length; j++) {				
				if (Mapa.getCuadro()[i][j].getUnidad() != null) {
					System.out.println("se encontro unidades en " + Mapa.getCuadro()[i][j].getIdX() + "," + Mapa.getCuadro()[i][j].getIdY());
					if (Mapa.getCuadro()[i][j].getUnidad().getIdJugador() != Mapa.getUnidadSeleccionada().getIdJugador()) {
						System.out.println("se encontro unidades enemigas en " + Mapa.getCuadro()[i][j].getIdX() + ","+ Mapa.getCuadro()[i][j].getIdY());							
						setPuntero_MiraAtaque(Mapa.getCuadro()[i][j]);
					}
				}
			}
		}
	}
	
	public void limpiarPunterosCuadros(){
		for (int i = 0; i < Mapa.getCuadro().length; i++) {
			for (int j = 0; j < Mapa.getCuadro().length; j++) {				
				Mapa.getCuadro()[i][j].setCursor(null);
			}
		}
	}
	
	public void establecerPuntero_Defecto(JPanel panel) {
		//setPuntero_PorDefecto(Mapa.getMapa().pnlCampo);
		setPuntero_PorDefecto(panel);
	}	

	public ImageIcon getImagenUnidad(){
		this.imagen= new ImageIcon(this.rutaImagenUnidad);
		return this.imagen;
	}	
	
	public ImageIcon getIconoUnidad(){
		this.imagen= new ImageIcon(this.rutaIconoUnidad);
		return this.imagen;
	}
	
	public ImageIcon getImagen(String ruta){
		this.imagen= new ImageIcon(ruta);
		return this.imagen;
	}		
	
	public String getRutaConquista(int idJugador){
		this.rutaConquista="src/imagenes/animados/jugador"+idJugador+"/zonaConquistada.gif";
		return this.rutaConquista;
	}	
	
	public void setPuntero_PorDefecto(JPanel panel){		
		panel.setCursor(
				Toolkit.getDefaultToolkit().createCustomCursor(
						this.getImagen(RUTA_PUNTERO_DEFECTO).getImage(), new Point(12, 0), "Puntero_Defecto")
				);		
	}
	
	public void setPuntero_Unidad(JPanel panel){
		panel.setCursor(
				Toolkit.getDefaultToolkit().createCustomCursor(
						getIconoUnidad().getImage(), new Point(25, 25), "Puntero_Unidad")
				);		
	}
	
	public void setPuntero_MiraAtaque(JPanel panel){
		panel.setCursor(
				Toolkit.getDefaultToolkit().createCustomCursor(
						this.getImagen(RUTA_PUNTERO_MIRA).getImage(), new Point(25, 25), "Puntero_MiraAtaque")
				);		
	}	
}
