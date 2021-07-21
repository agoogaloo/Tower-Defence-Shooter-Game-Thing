package graphics.particles.shapes;

import java.awt.Graphics;

import graphics.Camera;
import graphics.particles.shapes.colourers.ParticleColourer;

/**using this particle shape will unsuprisingly make the particles ovals
 * @author The Computer Man */
public class RectangleShape extends ParticleShape{

	private int width, height;
	
	/**
	 * 
	 * @param width - the width of the rectangle
	 * @param height - the height of the rectangle
	 * @param colourer - used to tell the particle its colour/transparancy and how it will change
	 */
	public RectangleShape(int width, int height, ParticleColourer colourer) {
		super(colourer);
		this.width=width;
		this.height=height;
	}
	
	/**
	 * this constructor is used to make perfect squares so it only takes 2 paramiters
	 * @param diamiter - how big the circle will be
	 * @param colourer - used to tell the particle its colour/transparancy and how it will change
	 */
	public RectangleShape(int width, ParticleColourer colourer) {
		this(width,width,colourer);
	}

	@Override
	public void render(Graphics g, Camera camera, int x, int y) {
		g.setColor(colourer.getColour());
		g.fillRect(x-camera.getxOffset()-width/2, y-camera.getyOffset()-width/2, width, height);
	}

	@Override
	/** this returns a copy of this particle shape*/
	public ParticleShape copy() {
		return new RectangleShape(width, height, colourer.copy());
	}

}
