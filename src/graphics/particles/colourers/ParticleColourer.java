package graphics.particles.colourers;

import java.awt.Color;

/**a particle colourer is used by the different particle effects to tell it what the 
 * shape/colour/transparency is and how it will update
 */
public abstract class ParticleColourer {
	
	//if the particle has turned invisible and wont turn visible again so it can be removed safely
	protected boolean remove=false;
	protected Color colour=Color.WHITE;
	
	public ParticleColourer(Color colour) {
		this.colour = colour;
	}
	
	public abstract void update();
	
	//needed so that the particle effects can give a different instance on the colourer to each particle
	public abstract ParticleColourer copy();
	
	public Color getColour() {
		return colour;
	}
	public boolean isRemove() {
		return remove;
	}
	
	
 
}
