package juego;

import java.io.Serializable;

import campoBatalla.Control;
import campoBatalla.Mapa;
import personajes.Unidad;

public class Jugador implements Serializable{

  private String nombre;
  private int idJugador;
  private boolean humano;
  private boolean turno;  
  
  private int presupuesto;
  private int puntaje; 
  
  private int conquistas=0;
  
  private Control control;    
  
  public String getNombre() {
      return this. nombre;
  }

  public void setNombre(String nombre) {
      this.nombre = nombre;
  }

  public int getnJugador() {
      return this. idJugador;
  }

  public void setIdJugador(int idJugador) {
      this.idJugador = idJugador;
  }
  
  public int getIdJugador(){
	  return this.idJugador;
  }

  public boolean getHumano() {
      return this. humano;
  }

  public void setHumano(boolean humano) {
      this.humano = humano;
  }

  public boolean getTurno() {
      return this.turno;
  }

  public void setTurno(boolean turno) {
      this.turno = turno;      
  }

  public int getPuntaje() {
      return this. puntaje;
  }

  public void setPuntaje(int puntaje) {
      this.puntaje = puntaje;
  }

  public void setPresupuesto(int presupuesto){
	  this.presupuesto=presupuesto;
  }
  
  public int getPresupuesto(){
	  return this.presupuesto;
  }
  
  public void setControl(Control control){
	  this.control=control;
  }
  
  public Control getControl(){
	  return this.control;
  }
  
  public void setConquistas(int conquista){
	  this.conquistas=conquista;
  }
  
  public int getConquistas(){
	  return this.conquistas;
  } 
  
}