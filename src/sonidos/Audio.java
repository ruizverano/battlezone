package sonidos;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//sonidos wav
//http://www.hispasonic.com/foros/archivos-wav-gratis/151339
//http://www.findsounds.com/ISAPI/search.dll

public class Audio implements Serializable, Runnable{
	
	private Clip sonido;
	private String audio;	
	private Thread hilo;
	private int tiempo;
	
	public Audio(String audio, int tiempo) {
		this.hilo= new Thread(this, "hilo audio");
		this.audio=audio;		
		this.tiempo=tiempo;
		try {            
			this.sonido=AudioSystem.getClip();   			         
        } catch (Exception e) {
            System.out.println("Error en sonido" + e);
        }
	}
	
	public void start(){
		this.hilo.start();
	}
	
	@Override
	public void run() {
		try {
			this.sonido.open(AudioSystem.getAudioInputStream(new File("src/sonidos/audios/"+this.audio+".wav")));
	        this.sonido.start();
	        this.sonido.loop(sonido.LOOP_CONTINUOUSLY);                                    	        
	        Thread.sleep(this.tiempo);	        
	        this.sonido.close();
		} catch (Exception e) {
			System.out.println("Error en reproducirSonido "+e);
			e.printStackTrace();
		}
	}		        
}