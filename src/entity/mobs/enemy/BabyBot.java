package entity.mobs.enemy;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class BabyBot extends Enemy{

	public BabyBot(int x, int y, int direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		setDefaultBounds();
		health=50;
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		reloadTime = 45;

		anims[DOWN]=new Animation(Assets.babyBotD);
		anims[UP]=new Animation(Assets.babyBotU);
		anims[LEFT]=new Animation(Assets.babyBotL);
		anims[RIGHT]=new Animation(Assets.babyBotR);
		directionBounds[UP]=new Rectangle(3,0,8,13);
		directionBounds[LEFT]=new Rectangle(0,1,13, 10);
		directionBounds[DOWN]=new Rectangle(3,0,8,14);
		directionBounds[RIGHT]=new Rectangle(4,1,13,10);
		init();
	}
	protected void shoot() {
		double targetX, targetY; 
		targetX = x+width/2+ThreadLocalRandom.current().nextDouble()-0.5; 
		targetY = y+height/2+ThreadLocalRandom.current().nextDouble()-0.5;
		entityManager.addEntity(new Bullet (x+width/2,y+height/2, targetX,targetY,
		Assets.enemyBullet, 3,75, false));
	 }

	@Override
	public Enemy createNew(int x, int y, int direction) {
		
		return new BabyBot(x, y, direction);	
	}
}
