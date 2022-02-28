package juego;

import java.util.Random;

import campoBatalla.Control;
import campoBatalla.Cuadro;
import campoBatalla.Mapa;
import maquina_IA.Perspectiva;

public class Maquina extends Jugador {

	private static Random rn;
	private static String nomUnidad;
	private static int costoUnidad;
	private static int pasos;
	private static int fuego;
	private static int[][] estadoCuadros;
	
	private static Perspectiva[] libres;
	private static Perspectiva[] obstaculos;
	private static Perspectiva[] propios;
	private static Perspectiva[] enemigos;

	public Maquina(boolean turno, int puntaje, int presupuesto) {
		super.setNombre("SoldadoCyborg");
		super.setIdJugador(2);
		super.setHumano(false);
		super.setTurno(turno);
		super.setPuntaje(puntaje);
		super.setPresupuesto(presupuesto);

		super.setControl(new Control(this));

		rn = new Random();	
		estadoCuadros=new int[Mapa.getMapa().cuadro.length][Mapa.getMapa().cuadro.length];
	}
	
	public static void accionMaquina() {
		System.out.println("entro a accionMaquina");
		if (Juego.juego.getJugador()[1].getPresupuesto() > 24) {
			reclutarUnidad();
		} else {
			System.out.println("maquina tiene menos de 25 presupuesto");
			Juego.juego.getJugador()[1].getControl().setReclutar(false);
			//seleccionarUnidad();
			identificarSituacion();
		}
	}
	
	public static void reclutarUnidad() {
		System.out.println("Maquina: reclutar_Unidad");

		switch (rn.nextInt(4) + 1) {
		case 1:
			nomUnidad = "infante";
			costoUnidad = 25;
			break;
		case 2:
			nomUnidad = "lancero";
			costoUnidad = 50;
			break;
		case 3:
			nomUnidad = "tanque";
			costoUnidad = 100;
			break;
		case 4:
			nomUnidad = "lanzaCohetes";
			costoUnidad = 125;
			break;
		default:
			nomUnidad = "";
			costoUnidad = 0;
			break;
		}

		Juego.juego.getJugador()[1].getControl().reclutar(nomUnidad);

		crearUnidad();

	}
	
	public static void crearUnidad() {

		for (int i = rn.nextInt(Mapa.getMapa().cuadro.length); i < Mapa.getMapa().cuadro.length; i++) {
			for (int j = rn.nextInt(Mapa.getMapa().cuadro.length); j < Mapa.getMapa().cuadro.length; j++) {

				if (Mapa.getMapa().cuadro[i][j].getPisoLibre()) {
					if (Juego.juego.getJugador()[1].getPresupuesto() >= costoUnidad) {

						clickearMapa(Mapa.getMapa().cuadro[i][j]);

						j = Mapa.getMapa().cuadro.length;
						i = Mapa.getMapa().cuadro.length;
					} else {
						j = Mapa.getMapa().cuadro.length;
						i = Mapa.getMapa().cuadro.length;
						reclutarUnidad();
					}
				}

			}
		}
		System.out.println("Presupuesto maquina " + Juego.juego.getJugador()[1].getPresupuesto());
	}
	
	public static void identificarSituacion(){
		
		int contadorPisosLibre=0;
		int contadorPisosObstaculo=0;
		int contadorPisosPropio=0;
		int contadorPisosEnemigo=0;
		
		for (int i = 0; i < estadoCuadros.length; i++) {
			for (int j = 0; j < estadoCuadros.length; j++) {
				
				if(Mapa.getMapa().getCuadro()[i][j].getPisoLibre()){
					estadoCuadros[i][j]=0;//pisos libres
					contadorPisosLibre++;
				}else if(Mapa.getMapa().getCuadro()[i][j].getPisoObstaculo()){
					estadoCuadros[i][j]=1;//pisos obstaculo
					contadorPisosObstaculo++;
				}else if(Mapa.getMapa().getCuadro()[i][j].getPisoOcupado()){
					if(Mapa.getMapa().getCuadro()[i][j].getUnidad().getIdJugador()==2){
						estadoCuadros[i][j]=2;//pisos con unidad amiga
						contadorPisosPropio++;
					}else if(Mapa.getMapa().getCuadro()[i][j].getUnidad().getIdJugador()==1){
						estadoCuadros[i][j]=3;// pisos con unidad enemiga
						contadorPisosEnemigo++;
					}
				}else{
					estadoCuadros[i][j]=4;//piso no existente
				}
			}
		}
		analizarPerspectiva(contadorPisosLibre, contadorPisosObstaculo, contadorPisosPropio, contadorPisosEnemigo);
	}
	
	public static void analizarPerspectiva(
			int tamañoPisosLibre, 
			int tamañoPisosObstaculo, 
			int tamañoPisosPropio, 
			int tamañoPisosEnemigo){
		
		libres = new Perspectiva[tamañoPisosLibre];
		obstaculos = new Perspectiva[tamañoPisosObstaculo];
		propios = new Perspectiva[tamañoPisosPropio];
		enemigos = new Perspectiva[tamañoPisosEnemigo];
		
		int iteradorLibre=0;
		int iteradorObstaculo=0;
		int iteradorPropio=0;
		int iteradorEnemigo=0;
		
		for (int i = 0; i < estadoCuadros.length; i++) {
			for (int j = 0; j < estadoCuadros.length; j++) {
					if(estadoCuadros[i][j]==0){
						libres[iteradorLibre]=new Perspectiva(tamañoPisosLibre, "pisosLibres", j, i);
						iteradorLibre++;
					}else if(estadoCuadros[i][j]==1){
						obstaculos[iteradorObstaculo]=new Perspectiva(tamañoPisosObstaculo, "pisosObstaculos", j, i);
						iteradorObstaculo++;
					}else if(estadoCuadros[i][j]==2){
						propios[iteradorPropio]=new Perspectiva(tamañoPisosPropio, "pisosPropios", j, i);
						iteradorPropio++;
					}else if(estadoCuadros[i][j]==3){
						enemigos[iteradorEnemigo]=new Perspectiva(tamañoPisosEnemigo, "pisosEnemigos", j, i);
						iteradorEnemigo++;
					}else{
						System.out.println("algo malo paso en anailizarPerspectiva()");
					}
			}
		}
		
		//mostrarAnalisis(libres);
		//mostrarAnalisis(obstaculos);
		//mostrarAnalisis(propios);
		//mostrarAnalisis(enemigos);
		
		seleccionarUnidad(propios);
	}	
	
	public static void mostrarAnalisis(Perspectiva[] perspectiva){
		for (int i = 0; i < perspectiva.length; i++) {
			if(perspectiva[i].getTipo().equals("pisosLibres")){
				System.out.println("pisos libres ubicados en : ("+perspectiva[i].getPosX()+","+perspectiva[i].getPosY()+")");
			}else if(perspectiva[i].getTipo().equals("pisosObstaculos")){
				System.out.println("obstaculos ubicados en : ("+perspectiva[i].getPosX()+","+perspectiva[i].getPosY()+")");
			}else if(perspectiva[i].getTipo().equals("pisosPropios")){
				System.out.println("unidades propias ubicadas en : ("+perspectiva[i].getPosX()+","+perspectiva[i].getPosY()+")");
			}else if(perspectiva[i].getTipo().equals("pisosEnemigos")){
				System.out.println("unidades enemigas ubicadas en : ("+perspectiva[i].getPosX()+","+perspectiva[i].getPosY()+")");
			}else{
				System.out.println("algo malo paso en mostrarAnalisis()");
			}			
		}
	}
	
	public static void seleccionarUnidad(Perspectiva[] propios){
		System.out.println("entro a seleccionar unidad maquina");
		
		for (int i = rn.nextInt(propios.length); i < propios.length; i++) {
			
			System.out.println("escogió a: "+propios[i].getTipo()+" en ("+propios[i].getPosX()+","+propios[i].getPosY()+")");
			
			Mapa.getMapa().getCuadro()[propios[i].getPosY()][propios[i].getPosX()].accionarClick();
			
			pasos=Mapa.getMapa().getUnidadSeleccionada().getDesplazamiento();
			fuego=Mapa.getMapa().getUnidadSeleccionada().getAlcanceFuego();
			i=propios.length;			
		}	
		
		if(localizarEnemigos(enemigos)){
			System.out.println("localizo enemigo");
		}else if(!moverUnidad(propios)){
			System.out.println("movio unidad "+moverUnidad(propios));
		}
	}
	
	public static boolean localizarEnemigos(Perspectiva[] enemigos){
		
		for (int i = 0; i < enemigos.length; i++) {
			int distanciaX=Math.abs(Mapa.getMapa().getUnidadSeleccionada().getPosX()-enemigos[i].getPosX());
			int distanciaY=Math.abs(Mapa.getMapa().getUnidadSeleccionada().getPosY()-enemigos[i].getPosY());
			
			System.out.println("fuego = "+fuego);
			System.out.println("distanciaX = "+distanciaX);
			System.out.println("distanciaY = "+distanciaY);
			
			if(
					distanciaX<=fuego
					&&
					distanciaY<=fuego
					){
				if(
						Mapa.getMapa().getUnidadSeleccionada().validarAtaque(
								Mapa.getMapa().getCuadro()
								[enemigos[i].getPosY()][enemigos[i].getPosX()].
								getUnidad()
								)>0																
				  ){
					System.out.println("ataque de maquina validado para "+Mapa.getMapa().getCuadro()
							[enemigos[i].getPosY()][enemigos[i].getPosX()].getUnidad().getNombre());
					
					Mapa.getMapa().getCuadro()[enemigos[i].getPosY()][enemigos[i].getPosX()].accionarClick();
				
				System.out.println("enemigo localizado en: ("+enemigos[i].getPosY()+","+enemigos[i].getPosX()+")");
				return true;
				}
			}
		}		
		return false;
	}

	public static boolean moverUnidad(Perspectiva[] libres){
		for (int i = 0; i < libres.length; i++) {			
			
			int pasosX=rn.nextInt(pasos);
			int pasosY=rn.nextInt(pasos);

				if(
						libres[i].getPosX()+pasosX<20 && libres[i].getPosY()+pasosY<20
						&&
						libres[i].getPosX()+pasosX>-1 && libres[i].getPosY()+pasosY>-1
						&&
						Mapa.getMapa().getUnidadSeleccionada().validarAvance(
						libres[i].getPosX()+pasosX, libres[i].getPosY()+pasosY)>0
					)
				{
					Mapa.getMapa().getCuadro()[libres[i].getPosY()+pasosY][libres[i].getPosX()+pasosX].accionarClick();	
					return true;
				}
		}
		return false;
	}

	public static void moverUnidad2() {
		System.out.println("Maquina: mover unidad");

		int posAX = Mapa.getMapa().getUnidadSeleccionada().getPosX();
		int posAY = Mapa.getMapa().getUnidadSeleccionada().getPosY();

		int nAleatorioX = rn.nextInt(pasos) + 1;
		int nAleatorioY = rn.nextInt(pasos) + 1;

		System.out.println("numeros aleatorios " + nAleatorioX + " " + nAleatorioY);

		for (int i = nAleatorioX; i < pasos + 1; i++) {
			for (int j = nAleatorioY; j < pasos + 1; j++) {

				if ((Mapa.getUnidadSeleccionada().validarAvance(i + posAX, j + posAY) > 0
						&& Mapa.getUnidadSeleccionada().validarAvance(i + posAX, j + posAY) < 6) && i + posAX > -1
						&& j + posAY > -1 && i + posAX < 20 && j + posAY < 20) {
					System.out.println("valido avance");

					clickearMapa(Mapa.getMapa().getCuadro()[j + posAY][i + posAX]);

					j = pasos + 1;
					i = pasos + 1;
				} else {
					//seleccionarUnidad();
				}
			}
		}

	}

	public static void clickearMapa(Cuadro cuadro) {
		System.out.println("clickeando a cuadro "+cuadro.getIdX() + ","+cuadro.getIdY());
		cuadro.accionarClick();
	}
}