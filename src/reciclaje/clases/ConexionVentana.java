package reciclaje.clases;
/*
package conexion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConexionVentana {
	JFrame frmConexion;
	JPanel pnlConexion;
	
	JButton btnServidor;
	JButton btnCliente;
	
	JTextField txtIpHost;
	
	public ConexionVentana() {
		inicializar();
		acciones();
	}
	
	public void inicializar(){
		frmConexion = new JFrame("Conexion");
		pnlConexion = new JPanel();
		btnServidor= new JButton("Servidor");
		btnCliente = new JButton("Cliente");
		txtIpHost = new JTextField("",10);
		
		pnlConexion.add(btnServidor);
		pnlConexion.add(txtIpHost);
		pnlConexion.add(btnCliente);
		
		frmConexion.setSize(200, 200);
		frmConexion.add(pnlConexion);
		frmConexion.setVisible(true);
	}
	
	public void acciones(){
		btnServidor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("btnServidor");
				new Servidor();				
			}
		});
		
		btnCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("btnCliente");
				new Cliente(txtIpHost.getText()); 				
			}
		});
	}
}
*/