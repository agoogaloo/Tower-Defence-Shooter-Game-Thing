package graphics.particles.shapes;

import java.awt.Graphics;

import graphics.Camera;
import graphics.particles.shapes.colourers.ParticleColourer;

/**using this particle shape will unsuprisingly make the particles ovals
 * @author The Computer Man */
public class OvalParticle extends ParticleShape{

	private int width, height;
	
	/**
	 * 
	 * @param width - the width of the oval
	 * @param height - the height of the oval
	 * @param colourer - used to tell the particle its colour/transparancy and how it will change
	 */
	public OvalParticle(int width, int height, ParticleColourer colourer) {
		super(colourer);
		this.width=width;
		this.height=height;
	}
	
	/**
	 * this constructor is used to make perfect circles so it only takes 2 paramiters
	 * @param diamiter - how big the circle will be
	 * @param colourer - used to tell the particle its colour/transparancy and how it will change
	 */
	public OvalParticle(int diamiter, ParticleColourer colourer) {
		this(diamiter,diamiter,colourer);
	}

	@Override
	public void render(Graphics g, Camera camera, int x, int y) {
		g.setColor(colourer.getColour());
		g.fillOval(x-camera.getxOffset()-width/2, y-camera.getyOffset()-width/2, width, height);
	}

	@Override
	/** this returns a copy of this particle shape*/
	public ParticleShape copy() {
		return new OvalParticle(width, height, colourer.copy());
	}

}
