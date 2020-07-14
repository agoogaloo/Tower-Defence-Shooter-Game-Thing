package entity.mobs.enemy;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class HeliBot extends Enemy  {
	
	
	
	public HeliBot(int x, int y, char direction) {
		super(x, y, direction);
		health=1;
		reloadTime=15;
		bounds.x=x;
		bounds.y=y;
		bounds.width=0;
		bounds.height=0;
		animUp=new Animation(Assets.heliBotU);
		animDown=new Animation(Assets.heliBotD);
		animLeft=new Animation(Assets.heliBotL);
		animRight=new Animation(Assets.heliBotR);
	}

	@Override
	public void updateDirection() {
		int xDiff=entityManager.getPlayer().getX()-x;
		int yDiff=entityManager.getPlayer().getY()-y;
		if(Math.abs(xDiff)>Math.abs(yDiff)) {
			if(xDiff>0) {
				direction='r';
			}else {
				direction='l';
			}
		}else {
			if(yDiff>0) {
				direction='d';
			}else {
				direction='u';
			}
		}
	}
	@Override
	public void update() {
		animDown.update();
		animLeft.update();
		animRight.update();
		animUp.update();
		
		updateDirection();
		switch(direction) {
		case 'd':
			currentPic=animDown.getCurrentFrame();
			break;
		case 'u':
			currentPic=animUp.getCurrentFrame();
			break;
		case 'l':
			currentPic=animLeft.getCurrentFrame();
			break;
		case 'r':
			currentPic=animRight.getCurrentFrame();
		}
		
		if (shotDelay >= reloadTime) { //If the enemy hasn't attacked in the last 30 frames and it detects the player's box it will shoot
			shoot();
			shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
		}
		shotDelay++;
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(currentPic, x-camera.getxOffset()-15, y-camera.getyOffset()-13, null);
	}
	
	

	

}
