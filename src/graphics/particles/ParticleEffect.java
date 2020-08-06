package graphics.particles;

import java.util.ArrayList;

import graphics.particles.movers.ParticleMover;
import graphics.particles.shapes.ParticleShape;

/**
 * this particle effect makes a particles that move outwards in a circle shape
 * @author The Computer Man
 */
public class ParticleEffect {
	
	ArrayList<Particle> particles=new ArrayList<Particle>();
	
	
	public ParticleEffect(int amount, ParticleMover mover, ParticleShape shape, boolean isOnTop) {
		for(int i=0;i<amount;i++) {
			particles.add(new Particle(mover.copy(),shape.copy(), isOnTop));
		}
	}
}
