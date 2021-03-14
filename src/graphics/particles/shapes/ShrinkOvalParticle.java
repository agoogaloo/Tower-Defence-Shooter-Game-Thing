package graphics.particles.shapes;

import java.awt.Graphics;

import graphics.Camera;
import graphics.particles.shapes.colourers.ParticleColourer;

/**using this particle shape will unsuprisingly make the particles ovals
 * @author The Computer Man */
public class ShrinkOvalParticle extends ParticleShape{

	private double width, height,xShrink,yShrink;
	
	public ShrinkOvalParticle(ParticleColourer colourer, double width, double height, double xShrink, double yShrink) {
		super(colourer);
		this.width = width;
		this.height = height;
		this.xShrink = xShrink;
		this.yShrink = yShrink;
	}

	/**
	 * this constructor is used to make perfect circles so it only takes 2 paramiters
	 * @param diamiter - how big the circle will be
	 * @param colourer - used to tell the particle its colour/transparancy and how it will change
	 */
	public ShrinkOvalParticle(ParticleColourer colourer,double diamiter,double shrinkAmount) {
		this(colourer,diamiter,diamiter, shrinkAmount,shrinkAmount);
	}

	@Override
	public void render(Graphics g, Camera camera, int x, int y) {
		g.setColor(colourer.getColour());
		g.fillOval(x-camera.getxOffset()-(int)width/2, y-camera.getyOffset()-(int)width/2, (int)width,(int) height);
		width-=xShrink;
		height-=yShrink;
	}

	@Override
	/** this returns a copy of this particle shape*/
	public ParticleShape copy() {
		return new ShrinkOvalParticle(colourer, width, height, xShrink, yShrink);
	}

}
