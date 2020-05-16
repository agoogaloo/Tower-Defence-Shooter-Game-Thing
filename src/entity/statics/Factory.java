package entity.statics;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Factory extends Statics{
	private Animation anim= new Animation(Assets.robotFactory);
	
	public Factory (int x, int y) {
		this.x=x;
		this.y=y;
		width=Assets.robotFactory[0].getWidth();
		height=Assets.robotFactory[0].getHeight();
		health=3;
		friendly=false;
		updateBounds();
	}
	

	@Override
	public void update() {
		anim.update();
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		
	}

}
