package audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class SoundEffect extends Sound{
    private Clip clip;

	public SoundEffect(String path) { 
		try {
			loadAudioStream(path);
			// create clip reference
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		}catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void update() {
		if(clip.getFramePosition()>=clip.getFrameLength()) {
		
			playing=false;
			clip.stop();
		}
	}

	public void play() {
		playing=true;
        volume = 1;
		//volume = SantaJam.getGame().getSettings().getSounds()/100f;
		
		FloatControl gainControl;
		clip.setFramePosition(0);
		clip.start();
		
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		
	}

	public void close() {
		clip.stop();
		clip.close();
	}
	@Override
	public void stop() {
		clip.stop();
	}
}
