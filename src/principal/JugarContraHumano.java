package principal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import campoBatalla.Accion;
import campoBatalla.Mapa;
import conexion.Cliente;
import conexion.Servidor;
import imagenes.Imagenes;
import juego.Juego;

public class JugarContraHumano extends JFrame implements ActionListener{
	
	private JFrame frmSorteo;
	
	private JPanel pnlPrincipal;
	private JPanel pnlHumano;
	private JPanel pnlConexion;
	
	private JLabel lblDireccionIp;
	private JTextField txtDireccionIp;
	
	private JButton btnConectarAServidor;
	private JButton btnAceptarConexion;
	private JButton btnJuegoNuevo;
	private JButton btnVolver;
	private JButton btnSortear;
	
	public static Juego juego;
	
	public static Servidor servidor;
	public static Cliente cliente;
	
	public void inicializar(){				
		
		pnlPrincipal=new JPanel(new BorderLayout());
		pnlHumano= new JPanel(new BorderLayout());
		pnlConexion= new JPanel(new BorderLayout());
		
		btnJuegoNuevo = new JButton("Iniciar combate");
		btnConectarAServidor= new JButton("Entrar en un combate");
		
		lblDireccionIp= new JLabel("Direccion");		
		txtDireccionIp = new JTextField("192.168.1.8",10);		
		btnAceptarConexion= new JButton("Aceptar");
					
		btnVolver = new JButton("Volver");
		
		btnJuegoNuevo.addActionListener(this);
		btnConectarAServidor.addActionListener(this);
		btnAceptarConexion.addActionListener(this);
		btnVolver.addActionListener(this);
		
		cargarPnlPrincipal();
		cargarPnlHumano();
		cargarPnlConexion();
	}
	
	public JugarContraHumano() {
		super("Battle Zone_1.0");		
		inicializar();		
		this.setVisible(true);
		this.setSize(340, 125);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pnlPrincipal);
		new Imagenes().setPuntero_PorDefecto(pnlPrincipal);			
	}
	
	public void cargarPnlPrincipal(){
		pnlPrincipal.add(pnlHumano, BorderLayout.NORTH);
		pnlPrincipal.add(pnlConexion, BorderLayout.CENTER);
		pnlPrincipal.add(btnVolver,BorderLayout.SOUTH);
	}
	
	public void cargarPnlHumano(){							
		pnlHumano.add(btnJuegoNuevo,BorderLayout.WEST);
		pnlHumano.add(btnConectarAServidor,BorderLayout.EAST);											
	}
	
	public void cargarPnlConexion(){
		pnlConexion.setVisible(false);
		pnlConexion.add(lblDireccionIp,BorderLayout.WEST);
		pnlConexion.add(txtDireccionIp,BorderLayout.EAST);
		pnlConexion.add(btnAceptarConexion,BorderLayout.SOUTH);		
	}
	
	public void abrirSorteoTurno(){
		frmSorteo = new JFrame("Sorteo Turno");
		btnSortear= new JButton("Sortear");
		btnSortear.addActionListener(this);
		frmSorteo.add(btnSortear);
		
		frmSorteo.setSize(100, 100);
		frmSorteo.setVisible(true);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		if(e.getSource()==btnJuegoNuevo){
			abrirSorteoTurno();
			
			Juego.juego = new Juego();
			Juego.juego.crearMapa();								
			Juego.juego.getMapa().obstaculizarPisos(Juego.juego.getMapa().getObstaculos());				
			Juego.juego.iniciarJugadorHumano1(Principal.getNombreJugador());								
			
			servidor=new Servidor();
			servidor.enviarObjeto(Juego.juego);	
			
			new Imagenes().establecerPuntero_Defecto(Mapa.getMapa().pnlCampo);
			dispose();
		}
		
		if(e.getSource()==btnConectarAServidor){
			pnlConexion.setVisible(true);
		}
		
		if(e.getSource()==btnAceptarConexion){
			cliente=new Cliente(txtDireccionIp.getText());
			dispose();
		}		
		
		if(e.getSource()==btnVolver){
			new Principal();
			dispose();
		}
		
		if(e.getSource()==btnSortear){
			Accion accion = new Accion();
			accion.sortearTurno();
			servidor.enviarObjeto(accion);
			accion.otorgarTurnoSorteado();
			frmSorteo.dispose();
		}
	}	
}
