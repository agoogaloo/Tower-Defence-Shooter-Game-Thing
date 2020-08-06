package graphics.particles.colourers;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Timed extends ParticleColourer{
	
	private int time,range;
	
	/**
	 * makes particles that disapear after a set amount of time 
	 * colour is the colour of the particle, time is how long it stays in frames, and 
	 * range determines how big of a range the particle will stay for 
	 * this means that the particle will stay for time +/- range frames
	 */
	public Timed(Color colour, int time, int range) {
		super(colour);
		this.range=range;
		if(range==0) {
			this.time=time;
		}else{
			this.time=ThreadLocalRandom.current().nextInt(time-range,time+range);	
		}
	}
	
	public Timed(Color colour,int time) {
		super(colour);
		this.range=0;
		this.time=time;	
	}
	
	public Timed(int time) {
		this(Color.WHITE, time,0);
	}
	
	@Override
	public void update() {
		time--;
		if(time<=0) {
			remove=true;
		}
	}

	@Override
	/**returns a particleColourer with this colourers current colour, time, and range*/
	public ParticleColourer copy() {
		
		return new Timed(colour, time,range);
	}
	
}
