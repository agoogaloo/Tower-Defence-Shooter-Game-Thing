package entity.statics.towers;

import java.awt.Color;
import java.awt.Graphics;

import entity.RenderLayer;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class TowerSpawn extends Statics{
	public boolean buildable=true;
	private Animation anim = new Animation(Assets.towerSpawn);
	private int spawnX, spawnY;
	private boolean hovered ;
	
	
	public TowerSpawn(int x, int y, boolean particles) {	
		collisions=false;
		width=anim.getCurrentFrame().getWidth();
		height=anim.getCurrentFrame().getHeight();
		spawnX=x;
		spawnY=y;
		this.x=x-width/2+8;
		this.y=y-height/2+8;
		updateBounds();
		layer=RenderLayer.BACK;	
		if(particles) {
			new InstantEffect(7, new Straight(new RectangleSpawner(this.x,this.y,width/2,height),220,80,0.3), 
					new ShrinkOvalParticle(new Timed(60) , 6,0.15), true);
			new InstantEffect(7, new Straight(new RectangleSpawner(this.x+width/2,this.y,width/2,height),-40,80,0.3), 
					new ShrinkOvalParticle(new Timed(60) , 6,0.15), true);
			new InstantEffect(6, new Straight(new Point(this.x+width/2,this.y+height/2),0.4), 
					new ShrinkOvalParticle(new Timed(new Color(181,181,181),60), 7,0.15), true);
		}
	}

	@Override
	public void update() {
		anim.update();		
	}
	
	

	@Override
	public void render(Graphics g, Camera camera) {
		if(!buildable) {
			return;
		}
		if(hovered) {
			g.drawImage(ImageUtils.outline(anim.getCurrentFrame(),Color.WHITE), x-camera.getxOffset()-1, y-camera.getyOffset()-1, null);
		}else {
			g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}
		hovered=false;
		
	}
	public void hover() {
		hovered=true;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}

}
