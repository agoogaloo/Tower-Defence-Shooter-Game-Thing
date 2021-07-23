package graphics.particles;

import java.util.ArrayList;

import graphics.particles.movers.ParticleMover;
import graphics.particles.shapes.ParticleShape;


/**
 * creates a group of particles that will share the same movement, spawning, shapes, and colouring systems 
 * allowing them to all folow the same rules but still be separate from each other with randomizations
 * @author The Computer Man
 */
public class EffectOverTime {
	
	ArrayList<Particle> particles=new ArrayList<Particle>();
	int amount, delay, timeLeft;
	ParticleMover mover;
	ParticleShape shape;
	boolean isOnTop, timed;
	
	public EffectOverTime(double amount, double time, ParticleMover mover, ParticleShape shape,boolean isOnTop) {	
		this(amount/time,mover,shape,isOnTop);

		this.timeLeft=(int) Math.round(time);
		timed=true;
	}
	
	public EffectOverTime(double amountPerFrame, ParticleMover mover, ParticleShape shape,boolean isOnTop) {		
		this.mover = mover;
		this.shape = shape;
		this.isOnTop = isOnTop;
		timed=false;
		
		double numPerFrame=amountPerFrame;
		if(numPerFrame>=1) {
			this.amount=(int) Math.round(numPerFrame);
			delay=1;
		}else {
			this.amount=1;
			delay=(int) Math.round(1/numPerFrame);
		}
		
	}


	public void update() {
		if(timeLeft%delay==0&&!isFinished()) {
			new InstantEffect(amount, mover.copy(), shape.copy(), isOnTop);
		}
		timeLeft--;
	}
	public boolean isFinished() {
		return timeLeft<0&&timed;
	}
}
