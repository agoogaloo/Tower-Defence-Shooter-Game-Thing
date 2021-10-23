package entity.statics.breakables;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.statics.Statics;
import graphics.Camera;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;

public abstract class Breakable extends Statics{
	
	protected BufferedImage pic;
	private int spawnX, spawnY;
	
	public Breakable(BufferedImage pic, int x, int y) {
		this.pic=pic;
		this.width=pic.getWidth();
		this.height=pic.getHeight();
		spawnX=x;
		spawnY=y;
		this.x=x;
		this.y=y-height+16;
		updateBounds();		
	}
	@Override
	public void update() {
	}
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(pic, x-camera.getxOffset(), y-camera.getyOffset(), null);
		
	}
	
	@Override
	public void damage() {
		if(entityCollide().size()>=1) {
			destroy();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		new InstantEffect(10, new Straight(new Point(x+width/2,y+height/2),0.75), 
				new ShrinkOvalParticle(new Timed(15),5,0.25), false);
		
	}
	
	public static Breakable getProperBreakable(int floorID, int x, int y) {
		switch(floorID) {
		case GameState.FLOOR1: case GameState.TUTORIALINDEX:
			return new Floor1Breakable(x,y);
			
		default:
			return new Floor1Breakable(x,y);
		
		}
		
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}

}
