package graphics.particles;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleEffect {
	ArrayList<Particle> particles=new ArrayList<Particle>();
	private double speed;
	
	public ParticleEffect(int x, int y, int size, int amount, double speed, int time, boolean isOnTop) {
		for(int i=0;i<amount;i++) {
			//this.speed=ThreadLocalRandom.current().nextDouble(speed/3,speed);
			particles.add(new Particle(x, y, size, speed, time, isOnTop));
		}
	}
	
	public ParticleEffect(int x, int y, boolean isOnTop) {
		this(x,y,5,5,0.5,10,isOnTop);
	}
}
