package entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class Factory extends Statics{
	private Animation anim= new Animation(Assets.robotFactory);
	private BufferedImage currentPic;
	
	public Factory (int x, int y) {
		this.x=x;
		this.y=y;
		width=Assets.robotFactory[0].getWidth();
		height=Assets.robotFactory[0].getHeight();
		health=4;
		friendly=false;
		updateBounds();
	}
	
	@Override
	public void damage() {
		int initialHealth=health;
		super.damage();
		if(health!=initialHealth) {
			//making the enemy flash white when it gets hit
			currentPic=damageFlash(currentPic);
		}
	}
	
	@Override
	public void update() {
		anim.update();
		currentPic=anim.getCurrentFrame();
		if(health==1) {
			GameState.newFloor();
		}
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(currentPic, x-camera.getxOffset(), y-camera.getyOffset(), null);
		
	}

}
