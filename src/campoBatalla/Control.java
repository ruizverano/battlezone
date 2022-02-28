package campoBatalla;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import imagenes.Imagenes;
import juego.Juego;
import juego.Jugador;
import principal.JugarContraHumano;

public class Control extends JFrame implements Serializable,ActionListener {
	
	public Jugador jugador;									
	
	private JPanel pnlControl;
	private JPanel pnlControlUnidades;	
	private JPanel pnlControlInfo;
	private JPanel pnlControlBtnPrincipal;
	private JPanel pnlCrearPresupuesto;
	private JPanel pnlComprarSalud;				
	
	private JLabel lblTitulo;	
	private JLabel lblJugador;	
	
	public JLabel lblPuntos;
	
	public JLabel lblCredito;
	
	public static JLabel lblInfoClicks;	
	public static JLabel lblNombreUnidad;
	public static JLabel lblSaludUnidad;
	public static JLabel lblCostoUnidad;
	public static JLabel lblDesplazamientoUnidad;
	public static JLabel lblAlcanceFuegoUnidad;
	
	private static boolean reclutar=false;
	
	private JButton btnCrearPresupuesto;
	private JButton btnCrearUnidades;
	private JButton btnComprarSalud;
	private JButton btnRetirar;
	
	private JButton btnInfante;
	private JButton btnLancero;
	private JButton btnLanzaCohete;
	private JButton btnTanque;
	
	private JLabel lblCrearPresupuesto;
	private JLabel lblPuntosCanje;	
	private JButton btnMasPresupuesto;
	private JButton btnMenosPresupuesto;	
	private JButton btnAceptarPresupuesto;
	private JTextField txtCanjePuntos;	
	
	private JLabel lblComprarSalud;
	private JButton btnAceptarComprarSalud;
	
	private JButton btnVolver;
	
	private JButton btnGuardar;		
	
	protected static String nombreUnidad;
			
	public void inicializar(){
				
		this.pnlControl= new JPanel(new BorderLayout());		
		this.pnlControlUnidades= new JPanel();
		this.pnlControlInfo= new JPanel(new GridLayout(0,1));
		this.pnlControlBtnPrincipal= new JPanel(new GridLayout(0,1));
		this.pnlCrearPresupuesto= new JPanel(new GridLayout(0, 1));		
		this.pnlComprarSalud= new JPanel(new GridLayout(0, 1));											
		
		this.lblTitulo = new JLabel("*Panel de Control*");		
		
		this.lblJugador = new JLabel("-Jugador: " + jugador.getNombre());
		this.lblPuntos = new JLabel("-Puntos: " + jugador.getPuntaje());
		this.lblCredito = new JLabel("-Credito: $" + jugador.getPresupuesto());		
		
		lblInfoClicks = new JLabel();					
		lblNombreUnidad = new JLabel();
		lblSaludUnidad = new JLabel();
		lblCostoUnidad = new JLabel();				
		lblDesplazamientoUnidad = new JLabel();
		lblAlcanceFuegoUnidad = new JLabel();
		
		this.btnCrearUnidades = new JButton("Reclutar Unidades");					
		
		this.btnCrearPresupuesto = new JButton("Presupuesto de Guerra");		
		
		this.btnComprarSalud = new JButton("Recargar Salud");		
		
		this.btnRetirar = new JButton("Retirada");		
		
		
		this.btnInfante = new JButton("Infante $25");
		this.btnInfante.setIcon(new Imagenes(this.jugador.getIdJugador(),"infante").getIconoUnidad());		
		
		this.btnLancero = new JButton("Lancero $50");
		this.btnLancero.setIcon(new Imagenes(this.jugador.getIdJugador(),"lancero").getIconoUnidad());
		
		
		this.btnLanzaCohete = new JButton("Lanza Cohetes $125");
		this.btnLanzaCohete.setIcon(new Imagenes(this.jugador.getIdJugador(),"lanzaCohetes").getIconoUnidad());
		
		
		this.btnTanque = new JButton("Tanque $100");
		this.btnTanque.setIcon(new Imagenes(this.jugador.getIdJugador(),"tanque").getIconoUnidad());
				
		this.lblCrearPresupuesto= new JLabel(
						"<html>"
						+ "Canjea tus puntos ganados en batalla..."				
						+ "<br>"
						+ "valor de $ credito: 1/3 punto"
						+ "</html>"
				);
		
		this.lblPuntosCanje= new JLabel(lblPuntos.getText());				
		
		this.btnMasPresupuesto=new JButton("+");
		
		this.btnMenosPresupuesto=new JButton("-");
		
		this.btnAceptarPresupuesto=new JButton("Aceptar Canje");
		
		this.txtCanjePuntos=new JTextField(10);
		this.txtCanjePuntos.setEditable(false);
		
		this.lblComprarSalud= new JLabel(
				"<html>"
				+ "Recarga la salud de tus unidades"
				+ "<br>"
				+ "heridas en combate..."
				+ "<br>"
				+ "<br>"
				+ "valor de botiquin $125 creditos"
				+ "</html>"
				);
		this.btnAceptarComprarSalud= new JButton("Aceptar");
		
		this.btnVolver= new JButton("Volver");
		
		this.btnGuardar = new JButton("Guardar Batalla");		
		
		this.btnCrearUnidades.addActionListener(this);
		this.btnCrearPresupuesto.addActionListener(this);
		this.btnComprarSalud.addActionListener(this);
		this.btnGuardar.addActionListener(this);
		this.btnRetirar.addActionListener(this);		
		this.btnInfante.addActionListener(this);
		this.btnLancero.addActionListener(this);
		this.btnLanzaCohete.addActionListener(this);
		this.btnTanque.addActionListener(this);
		this.btnMasPresupuesto.addActionListener(this);
		this.btnMenosPresupuesto.addActionListener(this);
		this.btnAceptarPresupuesto.addActionListener(this);	
		this.btnVolver.addActionListener(this);
		
		informacionJugador();
		visibilizarBotonesPrincipales(true);
		visibilizarCrearUnidad(false);
		visibilizarCrearPresupuesto(false);
		visibilizarComprarSalud(false);		
	}
							
	public Control(Jugador jugador){
		super("Battle Zone_1.0 -"+jugador.getNombre());
		this.jugador= jugador;
		
		inicializar();
		
		if(jugador.getHumano()){
			this.setVisible(true);
			this.setSize(250, 600);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
			this.add(pnlControl);
			
			this.setEnabled(true);			
			setHabilitarControl(jugador.getTurno());
		}		
									
	}
	
	public void informacionJugador(){		
				
		pnlControlInfo.add(lblTitulo);

		pnlControlInfo.add(lblJugador);
		pnlControlInfo.add(lblPuntos);
		pnlControlInfo.add(lblCredito);				
						
		pnlControlInfo.add(lblInfoClicks);
		pnlControlInfo.add(lblNombreUnidad);
		pnlControlInfo.add(lblSaludUnidad);
		pnlControlInfo.add(lblCostoUnidad);
		pnlControlInfo.add(lblDesplazamientoUnidad);
		pnlControlInfo.add(lblAlcanceFuegoUnidad);
		
		pnlControl.add(pnlControlInfo,BorderLayout.NORTH);
	}
	
	public void visibilizarBotonesPrincipales(boolean visible){
		pnlControlBtnPrincipal.setVisible(visible);
		
		if(visible){
			pnlControlBtnPrincipal.add(btnCrearUnidades);
			pnlControlBtnPrincipal.add(btnCrearPresupuesto);			
			pnlControlBtnPrincipal.add(btnComprarSalud);
			pnlControlBtnPrincipal.add(btnGuardar);
			pnlControlBtnPrincipal.add(btnRetirar);			
		
			pnlControl.add(pnlControlBtnPrincipal,BorderLayout.CENTER);						
						
		}
	}
	
	public void visibilizarCrearUnidad(boolean visible){
		pnlControlUnidades.setVisible(visible);
		
		if(visible){					
			pnlControlUnidades.add(btnInfante);
			pnlControlUnidades.add(btnLancero);
			pnlControlUnidades.add(btnLanzaCohete);
			pnlControlUnidades.add(btnTanque);
			pnlControlUnidades.add(btnVolver);			
			
			pnlControl.add(pnlControlUnidades,BorderLayout.CENTER);

		}
	}
	
	public void visibilizarCrearPresupuesto(boolean visible){
		pnlCrearPresupuesto.setVisible(visible);
		txtCanjePuntos.setText(String.valueOf(jugador.getPuntaje()));
		if(visible){
			pnlCrearPresupuesto.add(lblCrearPresupuesto);
			pnlCrearPresupuesto.add(lblPuntosCanje);
			pnlCrearPresupuesto.add(btnMasPresupuesto);
			pnlCrearPresupuesto.add(txtCanjePuntos);
			pnlCrearPresupuesto.add(btnMenosPresupuesto);			
			pnlCrearPresupuesto.add(btnAceptarPresupuesto);
			pnlCrearPresupuesto.add(btnVolver);
			
			pnlControl.add(pnlCrearPresupuesto, BorderLayout.CENTER);
		}		
	}		
	
	public void visibilizarComprarSalud(boolean visible){
		pnlComprarSalud.setVisible(visible);		
		if(visible){			
			pnlComprarSalud.add(lblComprarSalud);
			pnlComprarSalud.add(btnAceptarComprarSalud);
			pnlComprarSalud.add(btnVolver);
			
			pnlControl.add(pnlComprarSalud, BorderLayout.CENTER);
		}				
	}
	
	public void setHabilitarControl(boolean turno){
		this.setEnabled(turno);		
		if(turno){
			this.lblTitulo.setIcon(new Imagenes().getImagen(Imagenes.RUTA_SI_TURNO));
		}else{
			this.lblTitulo.setIcon(new Imagenes().getImagen(Imagenes.RUTA_NO_TURNO));
		}		
	}									
	
	public static void setReclutar(boolean aReclutar){
		reclutar=aReclutar;
	}
	
	public static boolean getReclutar(){
		return reclutar;
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnCrearUnidades){
			visibilizarBotonesPrincipales(false);
			visibilizarCrearUnidad(true);
		}
		
		if(e.getSource()==btnCrearPresupuesto){
			visibilizarBotonesPrincipales(false);
			visibilizarCrearPresupuesto(true);
		}		
		
		if(e.getSource()==btnComprarSalud){
			visibilizarBotonesPrincipales(false);								
			visibilizarComprarSalud(true);
		}
		
		
		if(e.getSource()==btnGuardar){

			Juego.juego.guardarPartida_Xml();
		}
		
		if(e.getSource()==btnRetirar){
			retirada();
		}
		
		if(e.getSource()==btnInfante){
			reclutar("infante");	
		}
		
		if(e.getSource()==btnLancero){
			reclutar("lancero");	
		}
		
		if(e.getSource()==btnLanzaCohete){
			reclutar("lanzaCohetes");	
		}
		
		if(e.getSource()==btnTanque){
			reclutar("tanque");	
		}
		
		if(e.getSource()==btnMasPresupuesto){
			if(Integer.parseInt(txtCanjePuntos.getText())<jugador.getPuntaje())
			{
				txtCanjePuntos.setText(String.valueOf(Integer.parseInt(txtCanjePuntos.getText())+1));					
			}			
		}
		
		if(e.getSource()==btnMenosPresupuesto){
			if(Integer.parseInt(txtCanjePuntos.getText())>0)
			{
				txtCanjePuntos.setText(String.valueOf(Integer.parseInt(txtCanjePuntos.getText())-1));					
			}				
		}
		
		if(e.getSource()==btnAceptarPresupuesto){
			new Accion().canjearPuntos(Integer.parseInt(txtCanjePuntos.getText()));				
			visibilizarCrearPresupuesto(false);
			visibilizarBotonesPrincipales(true);
		}			
		
		if(e.getSource()==btnAceptarComprarSalud){
			new Accion().comprarSalud();
			visibilizarComprarSalud(false);
			visibilizarBotonesPrincipales(true);
		}
		
		if(e.getSource()==btnVolver){			
			reclutar=false;
			nombreUnidad=null;
			visibilizarCrearPresupuesto(false);
			visibilizarComprarSalud(false);
			visibilizarCrearUnidad(false);
			visibilizarBotonesPrincipales(true);						
		}
	}
	
	public void reclutar(String nombreUnd){
		reclutar=true;	
		nombreUnidad=nombreUnd;
		setCursorUnidad(nombreUnd);
	}
	
	public void setCursorUnidad(String nombreUnidad){	
		new Imagenes(this.jugador.getIdJugador(),nombreUnidad).setPuntero_Unidad(Mapa.getMapa().pnlCampo);		
	}
	
	public void setVisivilisar(boolean visible){
		this.setVisible(visible);
	}
	
	public void retirada(){
		new Accion().imprimirRetirada();		
		if(Juego.jugadorEnTurno.getIdJugador()==2){			
			Accion accion = new Accion(Juego.juego.getJugador()[0]);
			accion.ganar();
			Mapa.getMapa().enviarAccion(accion);			
		}else{			
			Accion accion = new Accion(Juego.juego.getJugador()[1]);
			accion.ganar();
			Mapa.getMapa().enviarAccion(accion);			
		}						
		System.exit(0);
	}
		
}
