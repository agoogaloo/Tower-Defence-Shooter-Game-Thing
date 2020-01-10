//@author Kevin

package entity.mobs;

import java.awt.Rectangle;
import entity.Entity;
import java.awt.Graphics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Enemy extends Mobs {
	private char direction;
	private int rangeWidth, rangeHeight = 100;
	private int shotDelay = 0;
	private boolean attack = false;
	private Rectangle attackRange = new Rectangle(x,y,rangeWidth,rangeHeight);
	
	private Camera camera;
	Animation animationDown = new Animation(Assets.enemyD,4);
	Animation animationLeft = new Animation(Assets.enemyL,4);
	Animation animationUp = new Animation(Assets.enemyU,4);
	Animation animationRight = new Animation(Assets.enemyR,4);
	
	public Enemy(int x, int y, char direction) {
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	
	private void shoot() {
		double playerX, playerY;
		playerX = entityManager.getPlayer().getX();
		playerY = entityManager.getPlayer().getY();
//		for(Entity e:entityManager.getEntities()) {
//			if(e.getBounds().intersects(attackRange)) {
//				playerX = entityManager.getPlayer().getX();
//				playerY = entityManager.getPlayer().getX();
//				attack = true;
//			}
//		}

			entityManager.addEntity(new Bullet (x,y, playerX, playerY,0));
			shotDelay = 0;
		
		if (shotDelay>10) { //When 60 frames pass reset shot buffer
			shotDelay=0;
		}
	}
	@Override
	public void update() {
		if (shotDelay == 5) {
			shoot();
		}
		animationDown.update();
		animationLeft.update();
		animationUp.update();
		animationRight.update();
		
		shotDelay+=1;
	}
	
	public void render(Graphics g, Camera camera) {
		if (direction == 'd'){
			g.drawImage(animationDown.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'l') {
			g.drawImage(animationLeft.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'u') {
			g.drawImage(animationUp.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'r') {
			g.drawImage(animationRight.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}
	}
}

