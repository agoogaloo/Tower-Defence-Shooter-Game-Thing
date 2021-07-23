package graphics.particles;

import graphics.particles.movers.ParticleMover;
import graphics.particles.shapes.ParticleShape;


/**
 * creates a group of particles that will share the same movement, spawning, shapes, and colouring systems 
 * allowing them to all folow the same rules but still be separate from each other with randomizations
 * @author The Computer Man
 */
public class InstantEffect {
	
	//ArrayList<Particle> particles=new ArrayList<Particle>();
	
	
	public InstantEffect(int amount, ParticleMover mover, ParticleShape shape, boolean isOnTop) {
		
		for(int i=0;i<amount;i++) {
			new Particle(mover.copy(),shape.copy(), isOnTop);
		}
	}
}
