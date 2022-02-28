package almacenamiento;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import campoBatalla.Cuadro;
import campoBatalla.Mapa;
import juego.Juego;
import juego.Jugador;

public class GuardarXML {
	private Document documento = null;
	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	private Date fecha;

	private Juego juego;
	private Mapa mapa;
	private Cuadro[][] cuadro;
	private Jugador[] jugador;

	public GuardarXML(Juego juego, Mapa mapa, Cuadro[][] cuadro, Jugador[] jugador) {
		this.fecha = new Date();
		this.juego = juego;
		this.mapa = mapa;
		this.cuadro = cuadro;
		this.jugador = jugador;
		formatearXml();
	}

	public void formatearXml() {
		DocumentBuilder builder;
		DOMImplementation implementacion;
		String fechaActual = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss").format(this.fecha);
		try {
			builder = this.factory.newDocumentBuilder();
			implementacion = builder.getDOMImplementation();
			this.documento = implementacion.createDocument(null, "xml", null);

			Element juego = this.documento.createElement("Juego");
			juego.setAttribute("idJuego", String.valueOf(this.juego.getIdJuego()));
			juego.setAttribute("jugadorEnTurno", String.valueOf(this.juego.getIdJugadorEnTurno()));
			
			Element jugadores = this.documento.createElement("jugadores");
			
			Element jugador1 = this.documento.createElement("jugador1");
			jugador1.setAttribute("idJugador", String.valueOf(this.jugador[0].getIdJugador()));
			jugador1.setAttribute("nombre", String.valueOf(this.jugador[0].getNombre()));
			jugador1.setAttribute("humano", String.valueOf(this.jugador[0].getHumano()));
			jugador1.setAttribute("turno", String.valueOf(this.jugador[0].getTurno()));
			jugador1.setAttribute("puntaje", String.valueOf(this.jugador[0].getPuntaje()));
			jugador1.setAttribute("presupuesto", String.valueOf(this.jugador[0].getPresupuesto()));
			
			Element jugador2 = this.documento.createElement("jugador2");
			jugador2.setAttribute("idJugador", String.valueOf(this.jugador[1].getIdJugador()));
			jugador2.setAttribute("nombre", String.valueOf(this.jugador[1].getNombre()));
			jugador2.setAttribute("humano", String.valueOf(this.jugador[1].getHumano()));
			jugador2.setAttribute("turno", String.valueOf(this.jugador[1].getTurno()));
			jugador2.setAttribute("puntaje", String.valueOf(this.jugador[1].getPuntaje()));
			jugador2.setAttribute("presupuesto", String.valueOf(this.jugador[1].getPresupuesto()));
			
			Element mapa = this.documento.createElement("mapa");
			mapa.setAttribute("filas", String.valueOf(this.mapa.getFilas()));
			mapa.setAttribute("columnas", String.valueOf(this.mapa.getColumnas()));
			mapa.setAttribute("tamañoCuadroX", String.valueOf(this.mapa.getTamañoCuadroX()));
			mapa.setAttribute("tamañoCuadroY", String.valueOf(this.mapa.getTamañoCuadroY()));
			
			//Element cuadros = this.documento.createElement("cuadros");
			
			Element[][] cuadro = new Element[this.mapa.getFilas()][this.mapa.getColumnas()];			
			
			Element[][] unidad = new Element[this.mapa.getFilas()][this.mapa.getColumnas()];
			
			for (int i = 0; i < cuadro.length; i++) {
				for (int j = 0; j < cuadro.length; j++) {
					System.out.println("Creando cuadro: ("+i+","+j+")");
					cuadro[i][j] = this.documento.createElement("cuadro");
					cuadro[i][j].setAttribute("idX", String.valueOf(this.cuadro[i][j].getIdX()));
					cuadro[i][j].setAttribute("idY", String.valueOf(this.cuadro[i][j].getIdY()));
					cuadro[i][j].setAttribute("pisoLibre", String.valueOf(this.cuadro[i][j].getPisoLibre()));
					cuadro[i][j].setAttribute("pisoObstaculo", String.valueOf(this.cuadro[i][j].getPisoObstaculo()));
					cuadro[i][j].setAttribute("pisoOcupado", String.valueOf(this.cuadro[i][j].getPisoOcupado()));
					cuadro[i][j].setAttribute("IdJugadorConquista", String.valueOf(this.cuadro[i][j].getIdJugador_Conquista()));
					
					if(this.cuadro[i][j].getPisoOcupado()){
						unidad[i][j]=this.documento.createElement("unidad");
						unidad[i][j].setAttribute("posX",String.valueOf(this.cuadro[i][j].getUnidad().getPosX()));
						unidad[i][j].setAttribute("posY",String.valueOf(this.cuadro[i][j].getUnidad().getPosY()));						
						unidad[i][j].setAttribute("idJugador",String.valueOf(this.cuadro[i][j].getUnidad().getIdJugador()));
						unidad[i][j].setAttribute("unidadNombre",String.valueOf(this.cuadro[i][j].getUnidad().getNombre()));
						unidad[i][j].setAttribute("idUnidad",String.valueOf(this.cuadro[i][j].getUnidad().getIdUnidad()));
						unidad[i][j].setAttribute("unidadSalud",String.valueOf(this.cuadro[i][j].getUnidad().getSalud()));
												
						cuadro[i][j].appendChild(unidad[i][j]);
					}
					

				}
			}
			
			this.documento.setXmlVersion("1.0");
			this.documento.getDocumentElement().appendChild(juego);
			
			juego.appendChild(jugadores);
			jugadores.appendChild(jugador1);
			jugadores.appendChild(jugador2);
			juego.appendChild(mapa);
			//mapa.appendChild(cuadros);

			for (int i = 0; i < this.cuadro.length; i++) {
				for (int j = 0; j < this.cuadro.length; j++) {
					//cuadros.appendChild(cuadro[i][j]);
					mapa.appendChild(cuadro[i][j]);
					/*
					if (this.cuadro[i][j] != null) {
						cuadros.appendChild(cuadro[i][j]);
					}*/
				}
			}

		} catch (ParserConfigurationException e) {
			System.err.println("Error guardando XML "+e.getMessage());
			e.printStackTrace();
		}
		guardarConFormato(documento, "src/almacenamiento/archivosJuego/archivo_" + fechaActual + ".xml");
	}

	public static void guardarConFormato(Document documento, String URI) {
		System.out.println("guardando..");
		try {
			TransformerFactory transFact = TransformerFactory.newInstance();

			transFact.setAttribute("indent-number", new Integer(3));
			Transformer trans = transFact.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			DOMSource domSource = new DOMSource(documento);
			trans.transform(domSource, sr);

			try {
				PrintWriter writer = new PrintWriter(new FileWriter(URI));
				writer.println(sw.toString());
				writer.close();
			} catch (IOException e) {
				System.out.println("error en guardar 1 " + e.getMessage());
			}
		} catch (Exception ex) {
			System.out.println("error en guardar 2 " + ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println("guardado!");
	}
}