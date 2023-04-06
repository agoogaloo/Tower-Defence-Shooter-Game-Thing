package audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AudioManager{
    public static final SoundEffect hit = new SoundEffect("res/sound/hit.wav");
    public static final SoundEffect heal = new SoundEffect("res/sound/heal.wav");

    public static final SoundEffect gunSwitch = new SoundEffect("res/sound/gunSwitch.wav");
    public static final SoundEffect[] walking = loadSoundList("res/sound/walk",4,".wav");
    public static final SoundEffect[] spin = loadSoundList("res/sound/spin",3,".wav");

	public static final SoundEffect[] pistol = loadSoundList("res/sound/pistol",3,".wav");
	public static final SoundEffect[] cannon = loadSoundList("res/sound/cannon",2,".wav");
	public static final SoundEffect[] fireBall = loadSoundList("res/sound/fireball",3,".wav");

	public static final SoundEffect[] enemyHit = loadSoundList("res/sound/enemyHit",5,".wav");
	public static final SoundEffect[] enemyDie= loadSoundList("res/sound/enemyDie",2,".wav");

	public static final SoundEffect[] coin = loadSoundList("res/sound/coin",3,".wav");

	public static final SoundEffect[] decorSmash = loadSoundList("res/sound/break",5,".wav");



	public static void load(){
		
	}
	private static SoundEffect[] loadSoundList(String path, int number, String end){
		SoundEffect[] list = new SoundEffect[number];
		for(int i=1;i<=number;i++){
			list[i-1]= new SoundEffect(path+i+end);
		}
		return list;
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