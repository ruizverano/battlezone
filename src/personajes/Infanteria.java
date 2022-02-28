package personajes;

public class Infanteria extends Unidad{
	
	public Infanteria(int idJugador) {
		super.setIdUnidad(0);
		super.setNombre("infante");
		//super.setLogo("infanteTerreno"+idJugador);		
		super.setCosto(25);
		super.setDesplazamiento(3);
		super.setAlcanceFuego(2);
		super.setSalud(10);
		super.setIdJugador(idJugador);
	}
	
}