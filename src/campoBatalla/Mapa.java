package campoBatalla;

import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import imagenes.Imagenes;
import juego.Juego;
import personajes.Unidad;
import principal.JugarContraHumano;
import sonidos.Audio;

public class Mapa extends JFrame implements Serializable {

	private int filas;

	private int columnas;

	private int tamañoCuadroX;
	private int tamañoCuadroY;

	public JPanel pnlCampo;

	public static Cuadro[][] cuadro;

	private static boolean seleccion;

	private static Unidad unidadSeleccionada;

	private Obstaculos obstaculos;

	public static Mapa mapa;

	private transient Imagenes imagenes;

	public Mapa(int filas, int columnas, int tamañoCuadroX, int tamañoCuadroY) {
		super("Battle Zone 1.0...Jugador # " + Juego.idJugador);
		this.pnlCampo = new JPanel();
		this.filas = filas;
		this.columnas = columnas;
		this.tamañoCuadroX = tamañoCuadroX;
		this.tamañoCuadroY = tamañoCuadroY;
		
		new Audio("sonidoFondo", 100000000).start();

		mapa = this;

		this.obstaculos = new Obstaculos(100, this.filas, this.columnas);

		this.imagenes = new Imagenes(Juego.idJugador);

		GridLayout layout = new GridLayout();
		layout.setColumns(columnas);
		layout.setRows(filas);

		pnlCampo.setLayout(layout);
		pnlCampo.removeAll();
				
		this.setSize(this.tamañoCuadroX * this.columnas, this.tamañoCuadroY * this.filas);		
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		cargarEscenario();
	}

	public void montarEscenario() {
		this.setContentPane(this.pnlCampo);
		this.setVisible(true);
	}

	public void cargarEscenario() {
		cuadro = new Cuadro[this.filas][this.columnas];

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				cuadro[i][j] = new Cuadro(this.tamañoCuadroX, this.tamañoCuadroY, i, j);
				cuadro[i][j].setImgPiso(imagenes.getImagen(Imagenes.RUTA_LIBRE));
				pnlCampo.add(cuadro[i][j]);
			}
		}
		montarEscenario();
	}

	public void obstaculizarPisos(Obstaculos obstaculos) {
		for (int i = 0; i < obstaculos.getNArreglo(); i++) {
			cuadro[obstaculos.getArregloX()[i]][obstaculos.getArregloY()[i]].setPisoObstaculo(true);
		}
	}

	public Obstaculos getObstaculos() {
		return this.obstaculos;
	}

	public static void setUnidaSeleccionada(Unidad unidad) {
		unidadSeleccionada = unidad;
		System.out.println("unidad seleccionada " + unidad.getPosX() + "," + unidad.getPosY());
		seleccion = true;

		Control.lblNombreUnidad.setText("-Unidad: " + unidad.getNombre());
		Control.lblSaludUnidad.setText("-Salud: " + unidad.getSalud());
		Control.lblCostoUnidad.setText("-Costo: " + unidad.getCosto());
		Control.lblDesplazamientoUnidad.setText("-Desplazamiento: " + unidad.getDesplazamiento() + " sectores");
		Control.lblAlcanceFuegoUnidad.setText("-Alcance Fuego: " + unidad.getAlcanceFuego() + " sectores");
	}

	public static Unidad getUnidadSeleccionada() {
		return unidadSeleccionada;
	}

	public static Cuadro[][] getCuadro() {
		return cuadro;
	}

	public void conquistar(int x, int y) {
		cuadro[y][x].setImgPiso(this.imagenes.getImagen(this.imagenes.getRutaConquista(unidadSeleccionada.getIdJugador())));
		System.out.println("piso Conquistado");
		cuadro[y][x].setIdJugador_Conquista(unidadSeleccionada.getIdJugador());
		evaluarConquistas();
	}
	
	public void evaluarConquistas(){
		int contadorPisosLibres=0;
		int contadorConquistasJugador1=0;
		int contadorConquistasJugador2=0;
		
		for (int i = 0; i < cuadro.length; i++) {
			for (int j = 0; j < cuadro.length; j++) {
				if(cuadro[i][j].getPisoLibre()){
					contadorPisosLibres++;
					if(cuadro[i][j].getIdJugador_Conquista()==1){
						contadorConquistasJugador1++;
					}
					
					if(cuadro[i][j].getIdJugador_Conquista()==2){
						contadorConquistasJugador2++;
					}
				}				
			}
		}		
		System.out.println("pisos libres: "+contadorPisosLibres);
		System.out.println("pisos conquistados jugador1: "+contadorConquistasJugador1);
		System.out.println("pisos conquistados jugador2: "+contadorConquistasJugador2);
		
		if(contadorConquistasJugador1==contadorPisosLibres){
			System.out.println("Ganó Jugador 1");
			Accion accion = new Accion(Juego.juego.getJugador()[0]);
			enviarAccion(accion);
		}
		
		if(contadorConquistasJugador2==contadorPisosLibres){
			System.out.println("Ganó Jugador 2");
			Accion accion = new Accion(Juego.juego.getJugador()[1]);
			enviarAccion(accion);
		}
	}

	public void enviarAccion(Accion accion){
		if(Juego.juego.getJugador()[1].getHumano()){
			if(Juego.idJugador==1){
				JugarContraHumano.servidor.enviarObjeto(accion);
			}else{
				JugarContraHumano.cliente.enviarObjeto(accion);
			}
		}		
	}
	
	public void quitar(int x, int y) {
		cuadro[y][x].setPisoLibre(true);
		cuadro[y][x].setUnidad(null);
		cuadro[y][x].repaint();
		conquistar(x, y);
		System.out.println("se quito: (" + x + "," + y + ")");
	}

	public void matar(int x, int y) {
		new Audio("explosion", 3000).start();
		new Thread(new Runnable() {
			@Override
			public void run() {		
				cuadro[y][x].setImgPiso(imagenes.getImagen(Imagenes.RUTA_EXPLOSION));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					System.out.println("error en hilo explosion");
					e.printStackTrace();
				}
				quitar(x, y);
			}
		}).start();
		
		Juego.jugadorEnTurno.getControl().lblPuntos.setText("-Puntos: "+Juego.jugadorEnTurno.getPuntaje());
		
		System.out.println(Juego.jugadorEnTurno.getNombre() + " tiene " + Juego.jugadorEnTurno.getPuntaje() + " Puntos");
		
		System.out.println("se mató: (" + x + "," + y + ")");
	}

	public void mover(int idX, int idY, int direccionMovimiento) {

		System.out.println("funcion mover() desde " + Mapa.getUnidadSeleccionada().getPosX() + ","
				+ Mapa.getUnidadSeleccionada().getPosY());

		Unidad unidad = getUnidadSeleccionada();

		switch (direccionMovimiento) {
		case 1:
			for (int i = unidad.getPosY() - 1; i > idY - 1; i--) {
				if (cuadro[i][idX].getPisoLibre()) {
					moverVertical(unidad, cuadro[i][idX], idX, i);
				} else {
					i = idY;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 2:
			for (int i = unidad.getPosY() + 1; i < idY + 1; i++) {
				if (cuadro[i][idX].getPisoLibre()) {
					moverVertical(unidad, cuadro[i][idX], idX, i);
				} else {
					i = idY;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 3:
			for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
				if (cuadro[idY][i].getPisoLibre()) {
					moverHorizontal(unidad, cuadro[idY][i], idY, i);
				} else {
					i = idX;
				}
			}
			//Juego.cambiarTurno();
			break;

		case 4:
			for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
				if (cuadro[idY][i].getPisoLibre()) {
					moverHorizontal(unidad, cuadro[idY][i], idY, i);
				} else {
					i = idX;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 5:
			if (unidad.getPosX() < idX && unidad.getPosY() > idY) {
				for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
					for (int j = unidad.getPosY() - 1; j > idY - 1; j--) {
						if (cuadro[j][i].getPisoLibre()) {
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i++;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() > idX && unidad.getPosY() > idY) {

				for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
					for (int j = unidad.getPosY() - 1; j > idY - 1; j--) {
						if (cuadro[j][i].getPisoLibre()) {
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i--;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() > idX && unidad.getPosY() < idY) {
				for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
					for (int j = unidad.getPosY() + 1; j < idY + 1; j++) {
						if (cuadro[j][i].getPisoLibre()) {
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i--;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() < idX && unidad.getPosY() < idY) {
				for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
					for (int j = unidad.getPosY() + 1; j < idY + 1; j++) {
						if (cuadro[j][i].getPisoLibre()) {
							moverDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							i++;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			}
			//Juego.cambiarTurno();
			break;

		default:
			System.out.println("Movimiento Erroneo");
			break;
		}

		Mapa.setSeleccion(false);
	}

	public void moverVertical(Unidad unidad, Cuadro cuadro, int idX, int i) {
		new Audio("marcha", 500).start();
		quitar(unidad.getPosX(), unidad.getPosY());
		unidad.avanzar(idX, i);
		cuadro.ocupar(unidad);
		try {
			Thread.sleep(500);

		} catch (InterruptedException e) {
			System.out.println("Error en hilo ");
			e.printStackTrace();
		}
	}

	public void moverHorizontal(Unidad unidad, Cuadro cuadro, int idY, int i) {
		new Audio("marcha", 500).start();
		quitar(unidad.getPosX(), unidad.getPosY());
		unidad.avanzar(i, idY);
		cuadro.ocupar(unidad);
		try {
			Thread.sleep(500);

		} catch (InterruptedException e) {
			System.out.println("Error en hilo ");
			e.printStackTrace();
		}
	}

	public void moverDiagonal(Unidad unidad, Cuadro cuadro, int idX, int idY, int i, int j) {
		new Audio("marcha", 500).start();
		quitar(unidad.getPosX(), unidad.getPosY());
		unidad.avanzar(i, j);
		cuadro.ocupar(unidad);
		try {
			Thread.sleep(500);

		} catch (InterruptedException e) {
			System.out.println("Error en hilo ");
			e.printStackTrace();
		}
	}

	public void disparar(int idX, int idY, int direccionFuego) {

		System.out.println("funcion disparar() desde " + Mapa.getUnidadSeleccionada().getPosX() + ","
				+ Mapa.getUnidadSeleccionada().getPosY());

		Unidad unidad = getUnidadSeleccionada();

		switch (direccionFuego) {
		case 1:
			for (int i = unidad.getPosY() - 1; i > idY - 1; i--) {

				if (!cuadro[i][idX].getPisoObstaculo()) {
					if (cuadro[i][idX].getUnidad() != null) {
						atacarVertical(unidad, cuadro[i][idX], idX, i);
					}
				} else {
					i = idY;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 2:
			for (int i = unidad.getPosY() + 1; i < idY + 1; i++) {
				if (!cuadro[i][idX].getPisoObstaculo()) {
					if (cuadro[i][idX].getUnidad() != null) {
						atacarVertical(unidad, cuadro[i][idX], idX, i);
					}
				} else {
					i = idY;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 3:
			for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
				if (!cuadro[idY][i].getPisoObstaculo()) {
					if (cuadro[idY][i].getUnidad() != null) {
						atacarHorizontal(unidad, cuadro[idY][i], idY, i);
					}
				} else {
					i = idX;
				}
			}
			//Juego.cambiarTurno();
			break;

		case 4:
			for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
				if (!cuadro[idY][i].getPisoObstaculo()) {
					if (cuadro[idY][i].getUnidad() != null) {
						atacarHorizontal(unidad, cuadro[idY][i], idY, i);
					}
				} else {
					i = idX;
				}
			}
			//Juego.cambiarTurno();
			break;
		case 5:
			if (unidad.getPosX() < idX && unidad.getPosY() > idY) {
				for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
					for (int j = unidad.getPosY() - 1; j > idY - 1; j--) {

						if (!cuadro[j][i].getPisoObstaculo()) {
							if (cuadro[j][i].getUnidad() != null) {
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							}
							i++;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() > idX && unidad.getPosY() > idY) {

				for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
					for (int j = unidad.getPosY() - 1; j > idY - 1; j--) {
						if (!cuadro[j][i].getPisoObstaculo()) {
							if (cuadro[j][i].getUnidad() != null) {
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							}
							i--;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() > idX && unidad.getPosY() < idY) {
				for (int i = unidad.getPosX() - 1; i > idX - 1; i--) {
					for (int j = unidad.getPosY() + 1; j < idY + 1; j++) {
						if (!cuadro[j][i].getPisoObstaculo()) {
							if (cuadro[j][i].getUnidad() != null) {
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							}
							i--;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			} else if (unidad.getPosX() < idX && unidad.getPosY() < idY) {
				for (int i = unidad.getPosX() + 1; i < idX + 1; i++) {
					for (int j = unidad.getPosY() + 1; j < idY + 1; j++) {
						if (!cuadro[j][i].getPisoObstaculo()) {
							if (cuadro[j][i].getUnidad() != null) {
								atacarDiagonal(unidad, cuadro[j][i], idX, idY, i, j);
							}
							i++;
						} else {
							j = idY;
							i = idX;
						}
					}

				}
			}
			//Juego.cambiarTurno();
			break;

		default:
			break;
		}

		Mapa.setSeleccion(false);
	}

	public void atacarVertical(Unidad unidad, Cuadro cuadro, int idX, int i) {
		unidad.atacar(cuadro.getUnidad());
		new Audio("disparo", 1300).start();
		System.out.println("Salud de esta unidad " + cuadro.getUnidad().getSalud());
		if (cuadro.getUnidad().getSalud() < 1) {
			Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());			
			matar(cuadro.getUnidad().getPosX(), cuadro.getUnidad().getPosY());
		}
	}

	public void atacarHorizontal(Unidad unidad, Cuadro cuadro, int idY, int i) {
		unidad.atacar(cuadro.getUnidad());
		new Audio("disparo", 1300).start();
		System.out.println("Salud de esta unidad " + cuadro.getUnidad().getSalud());
		if (cuadro.getUnidad().getSalud() < 1) {
			Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());			
			matar(cuadro.getUnidad().getPosX(), cuadro.getUnidad().getPosY());
		}
	}

	public void atacarDiagonal(Unidad unidad, Cuadro cuadro, int idX, int idY, int i, int j) {
		unidad.atacar(cuadro.getUnidad());
		new Audio("disparo", 1300).start();
		System.out.println("Salud de esta unidad " + cuadro.getUnidad().getSalud());
		if (cuadro.getUnidad().getSalud() < 1) {
			Juego.darPuntos(Juego.jugadorEnTurno, cuadro.getUnidad().getIdUnidad());			
			matar(cuadro.getUnidad().getPosX(), cuadro.getUnidad().getPosY());
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

	public static void setSeleccion(boolean aSeleccion) {
		seleccion = aSeleccion;
	}

	public static boolean getSeleccion() {
		return seleccion;
	}

	public static Mapa getMapa() {
		return mapa;
	}

}