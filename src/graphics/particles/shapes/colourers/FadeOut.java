package graphics.particles.shapes.colourers;

import java.awt.Color;

public class FadeOut extends ParticleColourer{
	
	private double alpha, fadeSpeed;
	
	public FadeOut(Color colour, double fadeSpeed, int startAlhpa) {
		super(colour);
		this.fadeSpeed=fadeSpeed;
		alpha=startAlhpa;	
	}
	
	public FadeOut(Color colour, double fadeSpeed) {
		this(colour, fadeSpeed,255);
	}
	
	public FadeOut(double fadeSpeed) {
		this(Color.WHITE, fadeSpeed,255);
	}
	
	@Override
	public void update() {
		alpha-=fadeSpeed;
		if(alpha<=0) {
			remove=true;
			alpha=0;
		}
		//setting the colour to a new colour with the updateded transparancy
		colour=new Color(colour.getRed(),colour.getGreen(),colour.getBlue(),(int)alpha);
	}

	@Override
	public ParticleColourer copy() {
		return new FadeOut(colour, fadeSpeed, (int)Math.round(alpha));
	}
}
