package audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AudioManager{
    public static final SoundEffect hit = new SoundEffect("res/sound/hit.wav");
    public static final SoundEffect[] walking = new SoundEffect[] {
        new SoundEffect("res/sound/walking1.wav"),new SoundEffect("res/sound/walking2.wav"),new SoundEffect("res/sound/walking3.wav"),
    
    };
	public static void load(){
		
	}

    public static void playSound(SoundEffect sound) {
		sound.play();
	}

	public static void playSound(SoundEffect[] sounds) {
		ArrayList<SoundEffect> soundArr =new ArrayList<>(Arrays.asList(sounds));
		boolean playable=false;
		int index=0;
		while(soundArr.size()>=1&&!playable) {
			
			index=ThreadLocalRandom.current().nextInt(soundArr.size());
			
			if(soundArr.get(index).isPlaying()) {
				soundArr.remove(index);
			}else{
				playable=true;
			}
		}
		
		if(soundArr.size()==0) {
			sounds[ThreadLocalRandom.current().nextInt(sounds.length)].play();
		}else {
			soundArr.get(index).play();
		}
	
	
	}

}