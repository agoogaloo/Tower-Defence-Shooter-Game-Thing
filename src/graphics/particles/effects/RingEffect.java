package graphics.particles.effects;

import java.awt.Color;
import java.util.ArrayList;

import graphics.particles.Particle;
import graphics.particles.colourers.ParticleColourer;
import graphics.particles.colourers.Timed;

/**
 * this particle effect makes a particles that move outwards in a circle shape
 * @author The Computer Man
 *
 */
public class RingEffect {
	
	ArrayList<Particle> particles=new ArrayList<Particle>();
	private double speed;
	
	public RingEffect(int x, int y, int size, int amount, double speed,ParticleColourer colourer, boolean isOnTop) {
		for(int i=0;i<amount;i++) {
			particles.add(new Particle(x, y, size,speed,colourer.copy(), isOnTop));
		}
	}
	
	public RingEffect(int x, int y, boolean isOnTop) {
		this(x,y,6,15,0.25,new Timed(Color.white,20,10),false);
	}
}
