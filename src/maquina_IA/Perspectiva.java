package maquina_IA;

public class Perspectiva {
	
	private int tamaño;
	
	private String tipo;
	private int posX;
	private int posY;		
	
	public Perspectiva( int tamaño, String tipo, int posX, int posY) {
		this.tamaño=tamaño;
		
		this.tipo=tipo;
		this.posX=posX;
		this.posY=posY;				
	}
	
	public int getTamaño(){
		return tamaño;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}

}
