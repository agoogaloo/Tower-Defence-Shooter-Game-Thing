//@author Kevin

package entity.mobs;

import java.awt.Rectangle;

import Main.Main;
import entity.Entity;
import java.awt.Graphics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Enemy extends Mobs {
	private char direction;
	private int rangeWidth = 100, rangeHeight = 100;
	private int shotDelay = 0;
	private boolean attack = false;
	

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
	private void updateDirection() {
		switch (Main.getWindow().getDisplay().getFloor().getTile((x+Assets.enemyD[0].getWidth()/2)/16,
				(y+Assets.enemyD[0].getHeight()/2)/16)){
		case 5:
			direction='r';
			break;
		case 6:
			direction='u';
			break;
		case 7:
			direction='u';
			break;
		case 8:
			direction='r';
			break;
		case 9:
			direction='d';
			break;
		case 10:
			direction='d';
			break;
		case 11:
			direction='l';
			break;
		case 12:
			direction='l';
		}
	}
	private void move() {
		switch(direction) {
		case 'u':
			y-=speed;
			break;
		case 'd':
			y+=speed;
			break;
		case 'l':
			x-=speed;
			break;
		case 'r':
			x+=speed;
			break;
		}
	}
	private void shoot() {
		double playerX, playerY;
		playerX = entityManager.getPlayer().getX();
		playerY = entityManager.getPlayer().getY();


			entityManager.addEntity(new Bullet (x,y, playerX, playerY,0));
			shotDelay = 0;
		

	}
	@Override
	public void update() {
		Rectangle attackRange = new Rectangle(x,y,rangeWidth,rangeHeight);
		Rectangle playerBox = new Rectangle(entityManager.getPlayer().getX(),entityManager.getPlayer().getY(),rangeWidth,rangeHeight);
	
		System.out.println(attack);
		for(Entity e:entityManager.getEntities()) {
			if(playerBox.intersects(attackRange)) {
				attack = true;
			}else{
				attack = false;
			}
		}
		if (shotDelay == 5 && attack == true) {
			shoot();
		}
		updateDirection();
		move();
		animationDown.update();
		animationLeft.update();
		animationUp.update();
		animationRight.update();
		
		shotDelay+=1;
		if (shotDelay>10) { //When 60 frames pass reset shot buffer
			shotDelay=0;
		}
	}
	
	public void render(Graphics g, Camera camera) {
		g.drawRect(entityManager.getPlayer().getX()-camera.getxOffset(),entityManager.getPlayer().getY()-camera.getyOffset(),rangeWidth,rangeHeight);
		g.drawRect(x-camera.getxOffset(),y-camera.getyOffset(),rangeWidth,rangeHeight);
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

