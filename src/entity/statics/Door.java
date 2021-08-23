package entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.RenderLayer;
import graphics.Assets;
import graphics.Camera;

public class Door extends Statics{
	
	private char direction;
	private BufferedImage[] imgs;
	private int spawnX, spawnY;
	
	
	public Door(int x, int y, char direction) {
		solid=true;
		spawnX=x;
		spawnY=y;
		layer=RenderLayer.BACK;
		this.direction=direction;
		
		
		if(direction=='l'||direction=='r') {
			imgs=Assets.lv1DoorHor;
			width=16;
			height=imgs[0].getHeight();
			this.x=x;
			this.y=y-height+32;
			
		}else {
			imgs=Assets.lv1DoorVert;
			width=imgs[0].getWidth();
			height=imgs[0].getHeight();
			this.x=x-16;
			this.y=y-height+16;
		}
		updateBounds();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		if(solid) {
			g.drawImage(imgs[0], x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else {
			g.drawImage(imgs[1], x-camera.getxOffset(), y-camera.getyOffset(), null);
		}
	}
	@Override
	public void damage(double amount) {
		unlock();
	}
	
	public void unlock() {
		solid=false;
	}
	public char getDirection() {
		return direction;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}

}
