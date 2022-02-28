package principal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import almacenamiento.CargarXML;
import campoBatalla.Accion;
import imagenes.Imagenes;
import juego.Juego;

public class Principal extends JFrame implements ActionListener{	
	
	private JPanel pnlPrincipal;
	private JPanel pnlNombreJugador;
	private JPanel pnlBotones;
	private JPanel pnlAcciones;
	private JPanel pnlCombatir;
	
	private JFileChooser abrirArchivo;
	
	private ImageIcon imagenPrincipal;
	
	private JLabel lblImagen;
	private JLabel lblNombreJugador;
	
	private JTextField txtNombreJugador;
	private static String nombreJugador;
	
	private JButton btnJugarContraHumano;
	private JButton btnJugarContraMaquina;
	private JButton btnCargarJuego;
	private JButton btnVerRanking;
	private JButton btnVerCreditos;
	private JButton btnSalir;
	
	public void inicializar(){			

		this.imagenPrincipal= new ImageIcon(new Imagenes().RUTA_PRINCIPAL);
	
		this.lblImagen= new JLabel();
		this.lblImagen.setIcon(this.imagenPrincipal);
		
		this.pnlPrincipal= new JPanel();
		this.pnlBotones=new JPanel(new BorderLayout());		
		this.pnlNombreJugador=new JPanel();
		this.pnlCombatir=new JPanel();
		this.pnlAcciones=new JPanel(new BorderLayout());
		
		this.abrirArchivo= new JFileChooser();
		
		this.lblNombreJugador= new JLabel("Tu nombre, Soldado!");
		this.txtNombreJugador= new JTextField("",15);
		
		this.btnJugarContraHumano = new JButton("Otro Humano");
		this.btnJugarContraMaquina = new JButton("Esta Maquina");
		this.btnCargarJuego = new JButton("Cargar Combate");
		this.btnVerRanking = new JButton("Mejores Soldados");
		this.btnVerCreditos= new JButton("Creditos");
		this.btnSalir= new JButton("Salir");
		
		this.btnJugarContraHumano.addActionListener(this);
		this.btnJugarContraMaquina.addActionListener(this);
		this.btnCargarJuego.addActionListener(this);
		this.btnVerRanking.addActionListener(this);
		this.btnVerCreditos.addActionListener(this);
		this.btnSalir.addActionListener(this);
		
		cargarPnlCombatir();
		cargarPnlAcciones();
		cargarPnlNombreJugador();
		cargarPnlBotones();				
		cargarPnlPrincipal();
	}
	
	public Principal() {
		super("Battle Zone_1.0");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(530, 360);	
		inicializar();								
		add(pnlPrincipal);
		setVisible(true);	
		setLocationRelativeTo(null);
		new Imagenes().setPuntero_PorDefecto(pnlPrincipal);
	}
	
	public void cargarPnlCombatir(){
		pnlCombatir.add(btnJugarContraHumano);
		pnlCombatir.add(btnJugarContraMaquina);
		pnlCombatir.setBorder(BorderFactory.createTitledBorder("Combatir contra:"));
	}
	
	public void cargarPnlAcciones(){
		pnlAcciones.add(btnCargarJuego,BorderLayout.WEST);
		pnlAcciones.add(btnVerRanking,BorderLayout.EAST);
		pnlAcciones.add(btnVerCreditos,BorderLayout.CENTER);
		pnlAcciones.add(btnSalir,BorderLayout.SOUTH);
	}
	
	public void cargarPnlNombreJugador(){
		pnlNombreJugador.add(lblNombreJugador);
		pnlNombreJugador.add(txtNombreJugador);		
	}
	
	public void cargarPnlBotones(){	
		pnlBotones.add(pnlNombreJugador,BorderLayout.NORTH);
		pnlBotones.add(pnlCombatir,BorderLayout.CENTER);
		pnlBotones.add(pnlAcciones,BorderLayout.SOUTH);
	}		
			
	public void cargarPnlPrincipal(){				
		
		pnlPrincipal.add(lblImagen);
		
		pnlPrincipal.add(pnlBotones);		
	}
	
	public static String getNombreJugador(){
		return nombreJugador;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==btnJugarContraHumano){
			if(txtNombreJugador.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Debes escribir tu nombre, soldado!",
						"Comunicado",JOptionPane.ERROR_MESSAGE);				
			}else{
				nombreJugador= txtNombreJugador.getText();					
				new JugarContraHumano();
				dispose();
			}
		}
		
		if (e.getSource()==btnJugarContraMaquina){
			if(txtNombreJugador.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Debes escribir tu nombre, soldado!",
						"Comunicado",JOptionPane.ERROR_MESSAGE);				
			}else{
				nombreJugador= txtNombreJugador.getText();					
				//new JugarContraMaquina();
				
				Juego.juego = new Juego();
				Juego.juego.crearMapa();								
				Juego.juego.getMapa().obstaculizarPisos(Juego.juego.getMapa().getObstaculos());				
				
				Juego.juego.iniciarJugadorHumano1(Principal.getNombreJugador());
				
				Juego.juego.iniciarJugadorMaquina();				
				
				Juego.juego.getJugador()[0].setTurno(true);	
				Juego.juego.getJugador()[0].getControl().setHabilitarControl(true);				
				
				Juego.setJugadorEnTurno(Juego.juego.getJugador()[0]);
				
				dispose();
			}
		}
		
		if (e.getSource()==btnCargarJuego){
			abrirArchivo.showOpenDialog(pnlPrincipal);
			String nomArchivo= abrirArchivo.getSelectedFile().getName();			
			new CargarXML(nomArchivo).cargarJuego();
			System.out.println("se cargó: "+nomArchivo);
		}
		
		if (e.getSource()==btnVerCreditos){			
			new Accion().imprimirCreditos();
		}
		
		if (e.getSource()==btnVerRanking){			
			new Accion().imprimirRanking();
		}
		
		if (e.getSource()==btnSalir){			
			System.exit(0);
		}		
	}
}
