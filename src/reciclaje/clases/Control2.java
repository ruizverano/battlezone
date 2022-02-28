/*
package campoBatalla;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RepaintManager;

import conexion.Servidor;
import imagenes.Imagenes;
import juego.Juego;
import juego.Jugador;
import personajes.Infanteria;
import personajes.Lancero;
import personajes.LanzaCohete;
import personajes.Tanque;
import personajes.Unidad;
import principal.JugarContraHumano;

public class Control2 implements Serializable {
	
	public Jugador jugador;						
		
	private JFrame frmControl;
	
	private JPanel pnlControl;
	private JPanel pnlControlUnidades;	
	private JPanel pnlControlInfo;
	private JPanel pnlControlBtnPrincipal;
	private JPanel pnlCrearPresupuesto;
	private JPanel pnlComprarSalud;
	
	private JButton btnPrincipales;
	
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
	private JButton btnRentirar;
	
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
	
	private JButton btnCancelar;
	
	private JButton btnGuardar;	
	
	private int puntosCanje;	
	
	protected static String nombreUnidad;
			
	public void inicializar(){
		
		this.frmControl = new JFrame("Battle Zone_1.0");
		
		this.pnlControl= new JPanel(new BorderLayout());		
		this.pnlControlUnidades= new JPanel();
		this.pnlControlInfo= new JPanel(new GridLayout(0,1));
		this.pnlControlBtnPrincipal= new JPanel(new GridLayout(0,1));
		this.pnlCrearPresupuesto= new JPanel(new GridLayout(0, 1));		
		this.pnlComprarSalud= new JPanel(new GridLayout(0, 1));			
		
		this.btnPrincipales= new JButton("Combatir");
		
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
		this.btnRentirar = new JButton("Retirada");
		
		
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
		this.txtCanjePuntos=new JTextField(String.valueOf(jugador.getPuntaje()),10);
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
		
		this.btnCancelar= new JButton("Cancelar");
		
		this.btnGuardar = new JButton("Guardar Batalla");		
		
		informacionJugador();
		visibilizarBotonesPrincipales(true);
		visibilizarCrearUnidad(false);
		visibilizarCrearPresupuesto(false);
		visibilizarComprarSalud(false);		
	}
							
	public Control2(Jugador jugador){
		
		this.jugador= jugador;
		
		inicializar();
				
		frmControl.setVisible(true);
		frmControl.setSize(250, 600);
		frmControl.add(pnlControl);
		
		frmControl.setEnabled(true);
		
		//habilitarControl(jugador.getTurno());				
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
			pnlControlBtnPrincipal.add(btnRentirar);			
		
			pnlControl.add(pnlControlBtnPrincipal,BorderLayout.CENTER);						
			
			accionesBotonesPrincipales();
		}
	}
	
	public void visibilizarCrearUnidad(boolean visible){
		pnlControlUnidades.setVisible(visible);
		
		if(visible){					
			pnlControlUnidades.add(btnInfante);
			pnlControlUnidades.add(btnLancero);
			pnlControlUnidades.add(btnLanzaCohete);
			pnlControlUnidades.add(btnTanque);
			pnlControlUnidades.add(btnPrincipales);			
			
			pnlControl.add(pnlControlUnidades,BorderLayout.CENTER);
			
			accionesCrearUnidad();
		}
	}
	
	public void visibilizarCrearPresupuesto(boolean visible){
		pnlCrearPresupuesto.setVisible(visible);
		
		if(visible){
			pnlCrearPresupuesto.add(lblCrearPresupuesto);
			pnlCrearPresupuesto.add(lblPuntosCanje);
			pnlCrearPresupuesto.add(btnMasPresupuesto);
			pnlCrearPresupuesto.add(txtCanjePuntos);
			pnlCrearPresupuesto.add(btnMenosPresupuesto);			
			pnlCrearPresupuesto.add(btnAceptarPresupuesto);
			pnlCrearPresupuesto.add(btnCancelar);
			
			pnlControl.add(pnlCrearPresupuesto, BorderLayout.CENTER);
		}
		accionesCrearPresupuesto();		
	}		
	
	public void visibilizarComprarSalud(boolean visible){
		pnlComprarSalud.setVisible(visible);		
		if(visible){			
			pnlComprarSalud.add(lblComprarSalud);
			pnlComprarSalud.add(btnAceptarComprarSalud);
			pnlComprarSalud.add(btnCancelar);
			
			pnlControl.add(pnlComprarSalud, BorderLayout.CENTER);
		}		
		accionesComprarSalud();
	}
	
	public void setHabilitarControl(boolean turno){
		frmControl.setEnabled(turno);
	}
	
	public void accionesBotonesPrincipales(){
		
		btnCrearPresupuesto.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				visibilizarBotonesPrincipales(false);
				visibilizarCrearPresupuesto(true);	
			}			
		});		
		
		btnCrearUnidades.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				visibilizarBotonesPrincipales(false);
				visibilizarCrearUnidad(true);				
			}
		});
		
		btnComprarSalud.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
					visibilizarBotonesPrincipales(false);								
					visibilizarComprarSalud(true);					
			}
		});
		
		ActionListener accion = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent q) {
				JOptionPane.showMessageDialog(null,"numero de eventos",
						"Retirada",JOptionPane.INFORMATION_MESSAGE);				
			}
		};
		btnRentirar.addActionListener(accion);
		/*
		btnRentirar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.gc();
				//System.exit(0);
				JOptionPane.showMessageDialog(null,"Te acabas de rendir, eres un cobarde!",
						"Retirada",JOptionPane.INFORMATION_MESSAGE);
				frmControl.dispose();
			}
		});*/
/*		
		btnGuardar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Accion accion = new Accion();
				accion.solicitarJugador();
				JugarContraHumano.servidor.enviarObjeto(accion);				
			}
		});
	}

	
	public void accionesCrearUnidad(){
		
		btnInfante.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				reclutar=true;
				nombreUnidad="infante";	
				setCursorUnidad(nombreUnidad);
			}
		});		
		
		btnLancero.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				reclutar=true;
				nombreUnidad="lancero";
				setCursorUnidad(nombreUnidad);
			}
		});
		
		btnLanzaCohete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				reclutar=true;
				nombreUnidad="lanzaCohetes";
				setCursorUnidad(nombreUnidad);
			}
		});
		
		btnTanque.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				reclutar=true;
				nombreUnidad="tanque";		
				setCursorUnidad(nombreUnidad);
			}
		});
		
		btnPrincipales.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				reclutar=false;
				nombreUnidad=null;
				visibilizarCrearUnidad(false);
				visibilizarBotonesPrincipales(true);
			}
		});							
	}
		
	
	public void accionesCrearPresupuesto(){					
		
		btnMasPresupuesto.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(txtCanjePuntos.getText())<jugador.getPuntaje())
				{
					txtCanjePuntos.setText(String.valueOf(Integer.parseInt(txtCanjePuntos.getText())+1));					
				}
				int mas=Integer.parseInt(txtCanjePuntos.getText())+1;				
			}			
			
		});
		
		btnMenosPresupuesto.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(txtCanjePuntos.getText())>0)
				{
					txtCanjePuntos.setText(String.valueOf(Integer.parseInt(txtCanjePuntos.getText())-1));					
				}	
				int menos=Integer.parseInt(txtCanjePuntos.getText())+1;
			}
		});
		
		btnAceptarPresupuesto.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Accion().canjearPuntos(Integer.parseInt(txtCanjePuntos.getText()));	
				int aceptado=Integer.parseInt(txtCanjePuntos.getText());
				visibilizarCrearPresupuesto(false);
				visibilizarBotonesPrincipales(true);
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				visibilizarCrearPresupuesto(false);
				visibilizarBotonesPrincipales(true);
			}
		});
	}
	
	public void accionesComprarSalud(){
		
		btnAceptarComprarSalud.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Accion().comprarSalud();
				visibilizarComprarSalud(false);
				visibilizarBotonesPrincipales(true);
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				visibilizarComprarSalud(false);
				visibilizarBotonesPrincipales(true);
			}
		});
				
	}
	
	public static void setReclutar(boolean aReclutar){
		reclutar=aReclutar;
	}
	
	public static boolean getReclutar(){
		return reclutar;
	}
	
	public void setCursorUnidad(String nombreUnidad){	
		new Imagenes(this.jugador.getIdJugador(),nombreUnidad).setPuntero_Unidad(Mapa.getMapa().pnlCampo);		
	}

}
*/