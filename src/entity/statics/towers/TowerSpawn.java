package entity.statics.towers;

import java.awt.Color;
import java.awt.Graphics;

import entity.RenderLayer;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;

public class TowerSpawn extends Statics{
	public boolean buildable=true;
	private Animation anim = new Animation(Assets.towerSpawn);
	private int spawnX, spawnY;
	private boolean hovered ;
	
	
	public TowerSpawn(int x, int y) {	
		collisions=false;
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
