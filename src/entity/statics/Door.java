package entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Camera;

public class Door extends Statics{
	
	private char direction;
	private BufferedImage[] imgs;
	
	public Door(int x, int y, char direction) {
		solid=true;
		entityManager.addSolid(this);
		
		this.direction=direction;
		
		if(direction=='l'||direction=='r') {
			imgs=Assets.lv1DoorHor;
			width=imgs[0].getWidth();
			height=imgs[0].getHeight();
			this.x=x;
			this.y=y-height/2;
			
		}else {
			imgs=Assets.lv1DoorVert;
			width=imgs[0].getWidth();
			height=imgs[0].getHeight();
			this.x=x-width/2;
			this.y=y;
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
	
	public void unlock() {
		solid=false;
	}

}
