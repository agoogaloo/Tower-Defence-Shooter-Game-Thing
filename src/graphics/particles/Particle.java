package graphics.particles;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import graphics.Camera;
import graphics.particles.colourers.ParticleColourer;

public class Particle {
	
	private static ParticleManager particleManager=new ParticleManager();
	private ParticleColourer colourer;
	private int x, y, size;
	private double trueX,trueY,xMove,yMove;
	private boolean remove=false;
	
	public Particle(int x, int y, int size, double speed, ParticleColourer colourer, boolean isOnTop) {
		particleManager.addParticle(this,isOnTop);
		this.colourer=colourer;
		this.x=x;
		this.y=y;
		trueX=x;
		trueY=y;
		this.size=size;
		
		xMove=ThreadLocalRandom.current().nextDouble(-speed,speed);
		yMove=speed-Math.abs(xMove);
		if(ThreadLocalRandom.current().nextBoolean()) {
			yMove*=-1;//making it move down instead of up 1/2 of the time 
		}
	}
	
	public void update() {
		trueX+=xMove;
		trueY+=yMove;
		x=(int) Math.round(trueX);
		y=(int) Math.round(trueY);
		
		colourer.update();
		if(!colourer.isVisible()) {
			remove=true;
		}
	}
	
	public void render(Graphics g, Camera camera) {
		g.setColor(colourer.getColour());//setting the colour to white
		g.fillOval(x-camera.getxOffset()-size/2,y-camera.getyOffset()-size/2,size, size);//drawign the circle
	}
	
	public static ParticleManager getParticleManager() {
		return particleManager;
	}
	
	public boolean isRemove() {
		return remove;
	}

}
