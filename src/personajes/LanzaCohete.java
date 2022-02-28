package personajes;

public class LanzaCohete extends Unidad {
	
	public LanzaCohete(int idJugador) {
		super.setIdUnidad(2);
		super.setNombre("lanzaCohetes");
		//super.setLogo("lanzaCohetesTerreno"+idJugador);
		super.setCosto(125);
		super.setDesplazamiento(8);
		super.setAlcanceFuego(5);
		super.setSalud(10);			
		super.setIdJugador(idJugador);
		}
		
}