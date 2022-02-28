package almacenamiento;

import java.io.File;
import java.text.BreakIterator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import juego.Juego;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CargarXML {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document documento = null;

	private Node nodoRaiz;
	private Node nodoJugador1;
	private Node nodoJugador2;
	private Node nodoMapa;
	private NodeList lstNodosCuadro;
	private NodeList lstNodosUnidad;

	public CargarXML(String archivo) {
		try {

			this.factory = DocumentBuilderFactory.newInstance();
			this.factory.setNamespaceAware(true);

			this.builder = this.factory.newDocumentBuilder();
			this.documento = this.builder.parse(new File("src/almacenamiento/archivosJuego/" + archivo));

		} catch (Exception e) {
			System.out.println("Error en cargar archivo " + e.getMessage());
		}

		this.nodoRaiz = this.documento.getFirstChild();

		this.nodoJugador1 = this.nodoRaiz.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(1);
		this.nodoJugador2 = this.nodoRaiz.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(3);
		this.nodoMapa = this.nodoRaiz.getChildNodes().item(1).getChildNodes().item(3);

		this.lstNodosCuadro = this.documento.getElementsByTagName("cuadro");
		this.lstNodosUnidad = this.documento.getElementsByTagName("unidad");				
	}
	
	public String getAtributosNodo_Juego(String atributo){		
		return this.nodoRaiz.getChildNodes().item(1).getAttributes().getNamedItem(atributo).getNodeValue();		
	}
	
	public String getAtributosNodo_Jugadores(int nJugador,String atributo){
		if(nJugador==1){
			return this.nodoJugador1.getAttributes().getNamedItem(atributo).getNodeValue();
		}	
		if(nJugador==2){
			return this.nodoJugador2.getAttributes().getNamedItem(atributo).getNodeValue();
		}
		return "Error: Jugador no existe";
	}
	
	public String getAtributosNodo_Mapa(String atributo){
		return this.nodoMapa.getAttributes().getNamedItem(atributo).getNodeValue();
	}
	
	public String getAtributosNodo_Cuadro(int X, int Y, String atributo){		
		
		String idX=String.valueOf(X);
		String idY=String.valueOf(Y);
		
		for (int i = 0; i < lstNodosCuadro.getLength(); i++) {
			if(
					lstNodosCuadro.item(i).getAttributes().getNamedItem("idX").getNodeValue().equals(idX)
					&&
					lstNodosCuadro.item(i).getAttributes().getNamedItem("idY").getNodeValue().equals(idY)){
				return lstNodosCuadro.item(i).getAttributes().getNamedItem(atributo).getNodeValue();
			}			
		}
		return null;
	}
	
	public String getAtributosNodo_Unidad(int X, int Y, String atributo){		
		
		String posX=String.valueOf(X);
		String posY=String.valueOf(Y);
		
		for (int i = 0; i < lstNodosUnidad.getLength(); i++) {
			if(
					lstNodosUnidad.item(i).getAttributes().getNamedItem("posX").getNodeValue().equals(posX)
					&&
					lstNodosUnidad.item(i).getAttributes().getNamedItem("posY").getNodeValue().equals(posY)){
				return lstNodosUnidad.item(i).getAttributes().getNamedItem(atributo).getNodeValue();
			}			
		}
		return null;
	}
	
	public void cargarJuego(){
		Juego.juego=new Juego();
		Juego.juego.crearMapa();
		Juego.juego.iniciarJugadorHumano1(getAtributosNodo_Jugadores(1, "nombre"));
		Juego.juego.iniciarJugadorHumano2(getAtributosNodo_Jugadores(2, "nombre"));
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				
				if(getAtributosNodo_Cuadro(i, j, "pisoObstaculo").equals("true")){
					Juego.juego.getMapa().cuadro[j][i].setPisoObstaculo(true);									
				}
				
				if(getAtributosNodo_Cuadro(i, j, "pisoOcupado").equals("true")){
										
					if(getAtributosNodo_Unidad(i, j, "idJugador").equals("1")){
						Juego.juego.setJugadorEnTurno(Juego.juego.getJugador()[0]);
					}else{
						Juego.juego.setJugadorEnTurno(Juego.juego.getJugador()[1]);
					}
											
					Juego.juego.getMapa().cuadro[j][i].crearUnidad(getAtributosNodo_Unidad(i, j, "unidadNombre"));			
					Juego.juego.getMapa().cuadro[j][i].getUnidad().setSalud(Integer.parseInt(getAtributosNodo_Unidad(i, j, "unidadSalud")));					
				}
				
				//falta cargar pisos conquistas
			}			
		}					
	}
}