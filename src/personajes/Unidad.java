package personajes;

import java.io.Serializable;

import campoBatalla.Mapa;
import juego.Juego;

public abstract class Unidad implements Serializable{

  private int idUnidad;	
  private String nombre;
  private int costo;
  private int posX;
  private int posY;
  private int alcanceFuego;
  private int dañoAtaque;
  private int salud;
  private int desplazamiento;
  private String logo;
  
  private int idJugador;
  
  private boolean seleccionado=false;
    
  public void setPosicion(int posX, int posY) {
	  this.posX=posX;
	  this.posY=posY;
  }  
  
  public void avanzar(int posX, int posY){	  
	  System.out.println(this.nombre + " avanzo a "+ posX +"," + posY);
	  this.setPosicion(posX, posY);
  }   
  
  public int validarAvance(int posX_Nuevo, int posY_Nuevo){
	  
	  int posX_Actual=this.posX;
	  int posY_Actual=this.posY;
	  int avance = this.desplazamiento;
	  
	  System.out.println("pos actual ("+posX_Actual+","+posY_Actual+")");
	  System.out.println("pos recibido ("+posX_Nuevo+","+posY_Nuevo+")");		  
	  
	  if(			 						
			(Math.abs(posX_Actual-posX_Nuevo) == Math.abs(posY_Actual-posY_Nuevo))
			&&
			(Math.abs(posX_Actual-posX_Nuevo)<=avance) //Avance diagonal
		){
		  System.out.println("movimiento diagonal");
		  return 5;
	  }
		  
	  else if(posY_Actual>posY_Nuevo && posY_Actual-posY_Nuevo<=avance && posX_Actual==posX_Nuevo)
	  {
		  System.out.println("movimiento arriba");
		  return 1;//movimiento arriba
	  }
	  
	  else if(posY_Actual<posY_Nuevo && posY_Nuevo-posY_Actual<=avance && posX_Actual==posX_Nuevo)
	  {
		  System.out.println("movimiento abajo");
		  return 2;//movimiento abajo
	  }
	  else if(posX_Actual>posX_Nuevo && posX_Actual-posX_Nuevo<=avance && posY_Actual==posY_Nuevo)
	  {
		  System.out.println("movimiento izquierda");
		  return 3; //movimiento izquierda
	  }
	  else if(posX_Actual<posX_Nuevo && posX_Nuevo-posX_Actual<=avance && posY_Actual==posY_Nuevo)
	  {
		  System.out.println("movimiento derecha");
		  return 4; //movimiento derecha								
	  }
	  else{
		  System.out.println("avance no valido");
		  return 0;	
	  }	
  }

public int validarAtaque(Unidad unidad){
	  
	  int posX_Actual=this.posX;
	  int posY_Actual=this.posY;
	  int posX_Enemigo=unidad.getPosX();
	  int posY_Enemigo=unidad.getPosY();
	  int alcance = this.alcanceFuego;
	  
	  System.out.println("pos actual ("+posX_Actual+","+posY_Actual+")");
	  System.out.println("pos recibido ("+posX_Enemigo+","+posY_Enemigo+")");	  

	  if(			 						
			(Math.abs(posX_Actual-posX_Enemigo) == Math.abs(posY_Actual-posY_Enemigo))
			&&
			(Math.abs(posX_Actual-posX_Enemigo)<=alcance)
		){
		  return 5;
	  }
		  
	  else if(posY_Actual>posY_Enemigo && posY_Actual-posY_Enemigo<=alcance && posX_Actual==posX_Enemigo){
		  //apuntar arriba
		  return 1;
	  }
	  else if(posY_Actual<posY_Enemigo && posY_Enemigo-posY_Actual<=alcance && posX_Actual==posX_Enemigo){
		  //apuntar abajo
		  return 2;
	  }
	  else if(posX_Actual>posX_Enemigo && posX_Actual-posX_Enemigo<=alcance && posY_Actual==posY_Enemigo){
		  //apuntar izquierda
		  return 3;
	  }
	  else if(posX_Actual<posX_Enemigo && posX_Enemigo-posX_Actual<=alcance && posY_Actual==posY_Enemigo){
		  //apuntar derecha
		  return 4;
	  }			  		
	  else{
		  System.out.println("direccion de ataque no valido");
		  return 0;	
	  }
	  
  } 

  public int matrizDaños(Unidad unidad){
	  int daño=0;
	  int[][] unidadA_Vs_UnidadE= new int[4][4];
	  int idUnidadActual=this.idUnidad;
	  int idUnidadEnemiga=unidad.getIdUnidad();	  	  	  
	  
	  //ataques infantes
	  unidadA_Vs_UnidadE[0][0]=5;
	  unidadA_Vs_UnidadE[0][1]=3;
	  unidadA_Vs_UnidadE[0][2]=2;
	  unidadA_Vs_UnidadE[0][3]=2;
	  //ataques lanceros
	  unidadA_Vs_UnidadE[1][0]=8;
	  unidadA_Vs_UnidadE[1][1]=5;
	  unidadA_Vs_UnidadE[1][2]=5;
	  unidadA_Vs_UnidadE[1][3]=5;
	  //ataques lanza cohetes
	  unidadA_Vs_UnidadE[2][0]=10;
	  unidadA_Vs_UnidadE[2][1]=10;
	  unidadA_Vs_UnidadE[2][2]=5;
	  unidadA_Vs_UnidadE[2][3]=8;
	  //ataques tanques
	  unidadA_Vs_UnidadE[3][0]=10;
	  unidadA_Vs_UnidadE[3][1]=8;
	  unidadA_Vs_UnidadE[3][2]=5;
	  unidadA_Vs_UnidadE[3][3]=5;
	  
	  for (int i = 0; i < unidadA_Vs_UnidadE.length; i++) {
		for (int j = 0; j < unidadA_Vs_UnidadE.length; j++) {
			if(i==idUnidadActual && j==idUnidadEnemiga){
				System.out.println("ataque encontrado, unidad atacante: "+i+" unidad atacada "+j);
				daño=unidadA_Vs_UnidadE[i][j];
			}			
		}
	}
	  	System.out.println("Daño causado -"+daño);  
	  return daño;
  }
  
  public void atacar(Unidad unidad) {
	  System.out.println(this.nombre + " ubicado en " + this.posX + "," +this.posY +
			  " atacó a" + unidad.getNombre() + " ubicado en " +unidad.getPosX() + "," + unidad.getPosY());
	  unidad.setSalud(unidad.getSalud()-matrizDaños(unidad));
  }
  
  public void setNombre(String nombre){
	  this.nombre=nombre;
  }
  
  public String getNombre(){
	  return this.nombre;
  }
  
  public void setLogo(String rutaLogo){
	  this.logo=rutaLogo;
  }
  
  public String getLogo(){
	  return this.logo;
  }
  
  public void setCosto(int costo){
	  this.costo=costo;
  }
  
  public int getCosto(){
	  return this.costo;
  }
  
  public void setSalud(int salud){
	  this.salud=salud;
  }
  
  public int getSalud(){
	  return this.salud;
  }
  
  public void setAtaque(Unidad unidad){
	  
  }
  
  public void setDañoAtaque(int dañoAtaque){
	  this.dañoAtaque= dañoAtaque;
  }
  
  public int getDañoAtaque(){
	  return this.dañoAtaque;
  }
  
  public void setAlcanceFuego(int alcanceFuego){
	  this.alcanceFuego=alcanceFuego;
  }
  
  public int getAlcanceFuego(){
	  return this.alcanceFuego;
  }
  
  public void setDesplazamiento(int desplazamiento){
	  this.desplazamiento=desplazamiento;
  }
  
  public int getDesplazamiento(){
	  return this.desplazamiento;
  }
  
  public void setSeleccionado(boolean seleccion){
	  this.seleccionado=seleccion;
	  	  
  }
  
  public boolean getSeleccionado(){
	  return this.seleccionado;
  }
  
  public int getPosX(){
	  return this.posX;
  }
  
  public int getPosY(){
	  return this.posY;
  }
  
  public int getIdUnidad(){
	  return this.idUnidad;
  }
  
  public void setIdUnidad(int id){
	  this.idUnidad=id;
  }
  
  public int getIdJugador(){
	  return this.idJugador;
  }
  
  public void setIdJugador(int idJugador){
	  this.idJugador=idJugador;
  }  

}