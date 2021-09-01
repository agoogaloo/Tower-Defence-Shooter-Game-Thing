package entity.statics.towers.laser;

import java.awt.Graphics;

import entity.RenderLayer;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class TowerSpawn extends Statics{
	private Animation anim = new Animation(Assets.towerSpawn);
	int spawnX, spawnY;
	
	public TowerSpawn(int x, int y) {		
		this.width=anim.getCurrentFrame().getWidth();
		this.height=anim.getCurrentFrame().getHeight();
		spawnX=x;
		spawnY=y;
		this.x=x-width/2+8;
		this.y=y-height/2+8;
		updateBounds();
		layer=RenderLayer.BACK;	
		
		
	}

	@Override
	public void update() {
		//anim.update();
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}

}
