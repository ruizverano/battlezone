package personajes;

public class Tanque extends Unidad {
	public Tanque(int idJugador) {
		super.setIdUnidad(3);
		super.setNombre("tanque");
		//super.setLogo("tanqueTerreno"+idJugador);
		super.setCosto(100);
		super.setDesplazamiento(5);
		super.setAlcanceFuego(5);
		super.setSalud(10);		
		super.setIdJugador(idJugador);
	}
	
}