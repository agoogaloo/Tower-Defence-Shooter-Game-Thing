package entity.mobs.enemy;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class HamburgerBot extends Enemy{
	
	public HamburgerBot(int x, int y, int direction) {
		super(x, y, direction);
		health=100;
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		reloadTime = 14;
		rangeWidth=300;
		rangeHeight=300;
		
		anims[DOWN]=new Animation(Assets.hamburgerBotD);
		anims[UP]=new Animation(Assets.hamburgerBotU);
		anims[LEFT]=new Animation(Assets.hamburgerBotL);
		anims[RIGHT]=new Animation(Assets.hamburgerBotR);
		
		for(int i=0;i<directionBounds.length;i++) {
			directionBounds[i]=new Rectangle(5,3,23,20);
		}
		init();
	}
	@Override
	protected void shoot() {
		double targetX=1, targetY=0; 
		switch (anims[0].getFrameIndex()) {
		//switch(0) {
		case 0:
			targetX=1;
			targetY=0;
			break;
		case 1:
			targetX=1;
			targetY=-1;
			break;
		case 2:
			targetX=0;
			targetY=1;
			break;
		case 3:
			targetX=1;
			targetY=1;
			break;
		}
		
		entityManager.addEntity(new Bullet (x+width/2,y+height/2, targetX+width/2+x, targetY+height/2+y,Assets.enemyBullet, 3, false)); //Creates red bullets that shoot towards the player
		entityManager.addEntity(new Bullet (x+width/2,y+height/2, -targetX+width/2+x, -targetY+height/2+y,Assets.enemyBullet, 3, false)); //Creates red bullets that shoot towards the player
	}
}
