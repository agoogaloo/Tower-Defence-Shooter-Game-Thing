package graphics.particles.effects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import graphics.particles.Particle;
import graphics.particles.colourers.ParticleColourer;
import graphics.particles.colourers.Timed;

public class CloudEffect {
	ArrayList<Particle> particles=new ArrayList<Particle>();
	private double speed;
	
	public CloudEffect(int x, int y, int size, int amount, double speed,ParticleColourer colourer, boolean isOnTop) {
		for(int i=0;i<amount;i++) {
			this.speed=ThreadLocalRandom.current().nextDouble(speed/5,speed);
			particles.add(new Particle(x, y, size, this.speed,colourer.copy(), isOnTop));
		}
	}
	
	public CloudEffect(int x, int y, boolean isOnTop) {
		this(x,y,6,15,0.25,new Timed(Color.white,30,10),false);
	}
}
