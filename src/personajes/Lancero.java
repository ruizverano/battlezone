package personajes;

public class Lancero extends Unidad {
	 public Lancero(int idJugador) {
		super.setIdUnidad(1);
		super.setNombre("lancero");
		//super.setLogo("lanceroTerreno"+idJugador);		
		super.setCosto(50);
		super.setDesplazamiento(3);
		super.setAlcanceFuego(3);
		super.setSalud(10);	
		super.setIdJugador(idJugador);
		}
		
}
