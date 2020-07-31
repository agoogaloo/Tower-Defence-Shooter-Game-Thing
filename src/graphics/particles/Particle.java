package graphics.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import graphics.Camera;

public class Particle {
	
	private static ParticleManager particleManager=new ParticleManager();
	private int x, y, size, alpha=255, time;
	private double trueX,trueY,xMove,yMove;
	private Color colour=Color.white;
	private boolean remove=false;
	
	public Particle(int x, int y, int size, double speed,int time, boolean isOnTop) {
		particleManager.addParticle(this,isOnTop);
		this.x=x;
		this.y=y;
		trueX=x;
		trueY=y;
		this.size=size;
		this.time=time;
		
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
		alpha-=time;
		if(alpha<0) {
			alpha=0;
			remove=true;
		}
		colour=new Color(255,255,255,alpha);
	}
	
	public void render(Graphics g, Camera camera) {
		g.setColor(colour);//setting the colour to white
		g.fillOval(x-camera.getxOffset()-size/2,y-camera.getyOffset()-size/2,size, size);//drawign the circle
	}
	
	public static ParticleManager getParticleManager() {
		return particleManager;
	}
	
	public boolean isRemove() {
		return remove;
	}

}
