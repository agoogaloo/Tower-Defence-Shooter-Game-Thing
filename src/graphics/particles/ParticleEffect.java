package graphics.particles;

import java.util.ArrayList;

import graphics.particles.movers.ParticleMover;
import graphics.particles.shapes.ParticleShape;


/**
 * creates a group of particles that will share the same movement, spawning, shapes, and colouring systems 
 * allowing them to all folow the same rules but still be separate from each other with randomizations
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
