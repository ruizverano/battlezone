package campoBatalla;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import imagenes.Imagenes;
import juego.Juego;
import juego.Jugador;
import personajes.Infanteria;
import personajes.Lancero;
import personajes.LanzaCohete;
import personajes.Tanque;
import personajes.Unidad;
import principal.JugarContraHumano;
import sonidos.Audio;

public class Cuadro extends JPanel implements Serializable{

	private Jugador jugador;
	
	private int idX;
	private int idY;
	
	private int tamañoAncho;
	private int tamañoAlto;
	
	private String imagenOrg;
	
	private boolean pisoLibre=true;
	private boolean pisoObstaculo=false;
	private boolean pisoOcupado=false;
	
	private Mapa mapa;
	
	private Unidad unidad=null;
	
	private boolean click=false;	
	
	private transient ImageIcon imgPiso;
	
	private int idJugador_Conquista;
		
	public Cuadro(int tW, int tH, int i,int j) {
		this.idX=j;
		this.idY=i;
		
		this.tamañoAncho = tW;
		this.tamañoAlto = tH;
				
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent coordenada) {
				accionarClick();														
			}			
		});
		
		this.getPreferredSize().setSize(this.tamañoAncho, this.tamañoAlto);		
	}	
		
public void accionarClick(){
		
		if(Control.getReclutar()){			
			if(pisoLibre){				
				Accion accion=new Accion(this);
				accion.crearUnidad();
				
				/*
				 * if(Juego.juego.getJugador()[1].getHumano()){
					enviarAccion(accion);
				}
				 * */
				
				Mapa.getMapa().enviarAccion(accion);
				accion.cambiarTurno();
				Mapa.getMapa().enviarAccion(accion);
				/*
				 * if(Juego.juego.getJugador()[1].getHumano()){
					enviarAccion(accion);
				}
				 * */
				
				
				new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
			}else{
				if(pisoObstaculo){
					new Accion(this).imprimirZonaObstaculo();					
					Control.lblInfoClicks.setText("Zona obztaculizada...");
					new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
					System.out.println("piso obstaculo "+"("+this.getIdX()+","+getIdY()+")");
				}else{
					if(pisoOcupado){
						new Accion(this).imprimirZonaOcupada();
						Control.lblInfoClicks.setText("Esta zona ya esta ocupada...");
						System.out.println("piso ocupado "+"("+this.getIdX()+","+getIdY()+")");
						new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
					}else{
						System.out.println("no existe");
					}
				}
			}
		}else{
			if(pisoLibre){
				if(Mapa.getSeleccion()){ 
					if(Mapa.getUnidadSeleccionada().getIdJugador()==Juego.jugadorEnTurno.getIdJugador()){
						
						this.setCursor(null);
						Accion accion=new Accion(this);
						accion.moverUnidad();	
						
						Mapa.getMapa().enviarAccion(accion);
						/*
						if(Juego.juego.getJugador()[1].getHumano()){
							enviarAccion(accion);
						}
						*/
						accion.cambiarTurno();						
						
						Mapa.getMapa().enviarAccion(accion);
						/*
						if(Juego.juego.getJugador()[1].getHumano()){
							enviarAccion(accion);
						}						
						*/
						new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
						new Imagenes().limpiarPunterosCuadros();
					}else{
						new Accion(this).imprimirNoTurno();
						new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
					}
				}else{
					new Accion(this).imprimirZonaLibre();					
					Control.lblInfoClicks.setText("Zona despejada...");					
					System.out.println("piso libre "+"("+this.getIdX()+","+getIdY()+")");	
					new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
				}
			}else{
				if(pisoObstaculo){
					new Accion(this).imprimirZonaObstaculo();
					Control.lblInfoClicks.setText("Zona obztaculizada...");
					System.out.println("piso obstaculo "+"("+this.getIdX()+","+getIdY()+")");
					new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
				}else{
					if(pisoOcupado){							

							if(Mapa.getSeleccion()){
								if(Mapa.getUnidadSeleccionada().getIdJugador()==Juego.jugadorEnTurno.getIdJugador()){
									
									if(this.unidad.getIdJugador()!=Mapa.getUnidadSeleccionada().getIdJugador()){	
										this.setCursor(null);
										Accion accion=new Accion(this);
										accion.atacarUnidad();
										
										Mapa.getMapa().enviarAccion(accion);
										/*
										if(Juego.juego.getJugador()[1].getHumano()){
											enviarAccion(accion);
										}
										*/
										accion.cambiarTurno();
										
										Mapa.getMapa().enviarAccion(accion);
										/*
										if(Juego.juego.getJugador()[1].getHumano()){
											enviarAccion(accion);
										}
										*/
										new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
										new Imagenes().limpiarPunterosCuadros();

									}else{
										new Accion(this).imprimirAutoAtaque();
										new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
										new Imagenes().limpiarPunterosCuadros();
										new Accion().cambiarTurno();
									}
								}else{
									//new Accion(this).imprimirNoTurno();	
									new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
									new Imagenes().limpiarPunterosCuadros();
								}
							}else{
								if(
										(this.getUnidad().getIdJugador()==Juego.juego.getIdJugadorEnTurno()
										&&
										this.getUnidad().getIdJugador()==Juego.juego.idJugador)
										||
										!Juego.juego.getJugador()[1].getHumano()
										)
								{
									Accion accion=new Accion(this);
									accion.seleccionarUnidad();
									
									new Audio("seleccion", 800).start();
									new Imagenes(Mapa.getUnidadSeleccionada().getIdJugador(),this.unidad.getNombre()).setPuntero_Unidad(Mapa.getMapa().pnlCampo);
									this.setCursor(null);
									
									Mapa.getMapa().enviarAccion(accion);
									/*
									if(Juego.juego.getJugador()[1].getHumano()){
										enviarAccion(accion);
									}
									*/
									new Imagenes().rastrearUnidades();
								}else{
									System.out.println("no es tu unidad");
								}
							}						
					}else{
						System.out.println("no existe");
					}
				}
			}
		}
		//
	}
	
	@Override
	public void paint(Graphics g) {		
		g.drawImage((this.imgPiso).getImage(), 0, 0, getWidth(),getHeight(),this);
		
		setOpaque(false);
		super.paint(g);					
	}	

	public void setImgPiso(ImageIcon imagen){					
		this.imgPiso = imagen;
		repaint();					
	}
	
	public void setPisoLibre(boolean pisoLibre){
		this.pisoLibre=pisoLibre;
		if(pisoLibre){
			this.pisoObstaculo=false;
			this.pisoOcupado=false;
		}
	}
	
	public boolean getPisoLibre(){
		return this.pisoLibre;
	}
	
	public void setPisoObstaculo(boolean pisoObstaculo){
		this.pisoObstaculo=pisoObstaculo;
		if(pisoObstaculo){
			this.pisoLibre=false;
			this.pisoOcupado=false;
			//this.setImgPiso("obstaculo");
			this.setImgPiso(new Imagenes(Juego.idJugador).getImagen(Imagenes.RUTA_OBSTACULIZADA));
		}
	}
	
	public boolean getPisoOcupado(){
		return this.pisoOcupado;
	}
	
	public void setPisoOcupado(boolean pisoOcupado){
		this.pisoOcupado=pisoOcupado;
		if(pisoOcupado){
			this.pisoLibre=false;
			this.pisoObstaculo=false;
		}
	}
	
	public boolean getPisoObstaculo(){
		return this.pisoObstaculo;
	}
	
	public void crearUnidad(String nombreUnidad){
		
		switch (nombreUnidad) {
		case "infante":
			Unidad infante = new Infanteria(Juego.jugadorEnTurno.getIdJugador());			
			reclutarUnidad(infante);
			System.out.println("Se creo" +infante.getNombre());			
			break;
		case "lancero":
			Unidad lancero = new Lancero(Juego.jugadorEnTurno.getIdJugador());						
			reclutarUnidad(lancero);
			System.out.println("Se creo" +lancero.getNombre());
			break;
		case "lanzaCohetes":
			Unidad lanzaCohete = new LanzaCohete(Juego.jugadorEnTurno.getIdJugador());			
			reclutarUnidad(lanzaCohete);
			System.out.println("Se creo" +lanzaCohete.getNombre());
			break;
		case "tanque":
			Unidad tanque= new Tanque(Juego.jugadorEnTurno.getIdJugador());			
			reclutarUnidad(tanque);
			System.out.println("Se creo" +tanque.getNombre());			
			break;			
		default:
			JOptionPane.showMessageDialog(null,"Existe un error en el metodo ocupar, verifique",
					"Error de programador",JOptionPane.ERROR_MESSAGE);
			break;
		}
		
	}
	
	public void reclutarUnidad(Unidad unidad){		
		
		if(unidad != null){
			new Audio("reclutar",1850).start();
			if(Juego.jugadorEnTurno.getPresupuesto()>=unidad.getCosto()){							
				
				Juego.jugadorEnTurno.setPresupuesto(Juego.jugadorEnTurno.getPresupuesto()-unidad.getCosto());
			Juego.jugadorEnTurno.getControl().lblCredito.setText("-Credito: $"+Juego.jugadorEnTurno.getPresupuesto());						
			
			ocupar(unidad);						
			
			}else{
				JOptionPane.showMessageDialog(null,Juego.jugadorEnTurno.getNombre()+" No tienes presupuesto suficiente para reclutar esta unidad militar, \n"+
						"un "+unidad.getNombre()+ " tiene un costo de $"+unidad.getCosto()+" \n"+
						"tu posees $"+Juego.jugadorEnTurno.getPresupuesto()+" de Presupuesto en Credito, \n"+
						"rediseña tu estrategia Comandante!","Sin Presupuesto",JOptionPane.INFORMATION_MESSAGE);								
			}
		}		
	}
	
	public void ocupar(Unidad unidad){
		
		Imagenes imagenUnidad= new Imagenes(unidad.getIdJugador(), unidad);
		
		unidad.setPosicion(this.idX, this.idY);
		
		this.unidad=unidad;		
		
		//this.setImgPiso(unidad.getLogo());
		this.setImgPiso(imagenUnidad.getImagenUnidad());
		
		repaint();
		
		this.setPisoOcupado(true);						
		
		System.out.println("ocupado "+this.idX + ","+this.idY );		
	}

	
	public void seleccionarUnidad(){					
		Mapa.setUnidaSeleccionada(this.unidad);
	}		
	
	public int getIdX(){
		return this.idX;
	}
	
	public int getIdY(){
		return this.idY;
	}
	
	public void setUnidad(Unidad unidad){
		this.unidad=unidad;
	}
	
	public Unidad getUnidad(){
		return this.unidad;
	}
	
	public void setIdJugador_Conquista(int idJugador){
		this.idJugador_Conquista=idJugador;
	}
	
	public int getIdJugador_Conquista(){
		return this.idJugador_Conquista;
	}
	
}
