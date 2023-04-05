package audio;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Sound {
	AudioInputStream audioStream;
	protected boolean playing = false;
	protected double volume;
	
	public abstract void update();
	public abstract void play();
	public abstract void stop();
	public abstract void close();
	
	protected void loadAudioStream(String path) {
		
		try {
			audioStream = AudioSystem.getAudioInputStream(
			        new File(path).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e) {
			 System.out.println(path+"is not supported.");
			e.printStackTrace();
		}		       
	}
	
	protected static float volumeToDb(double volume) {
		float value = 20f * (float) Math.log10(volume);
		value = Math.min(6, value);
		value = Math.max(-80, value);
		
		return value;
	}
	public boolean isPlaying() {
		return playing;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
