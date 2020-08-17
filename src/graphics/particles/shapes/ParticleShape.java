package graphics.particles.shapes;

import java.awt.Graphics;

import graphics.Camera;
import graphics.particles.shapes.colourers.ParticleColourer;


/**
 * things that extend this class represent the different shapes of particles that can be 
 * created like circles, squares, or pictures
 * @author The Computer Man
 *
 */
public abstract class ParticleShape {
	protected ParticleColourer colourer;
	protected boolean remove=false;
	
	public ParticleShape(ParticleColourer colourer) {
		this.colourer=colourer;
	}
	
	public void update() {
		colourer.update();
		if(colourer.isRemove()) {
			remove=true;
		}
	}
	
	public abstract void render(Graphics g, Camera camera, int x, int y);
	
	public abstract ParticleShape copy();
	
	public boolean isRemove() {
		return remove;
	}
}
