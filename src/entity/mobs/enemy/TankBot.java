package entity.mobs.enemy;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class TankBot extends Enemy{

	private int volleyTime=0, volleyShots=0;
	private double targetX, targetY;
	public TankBot(int x, int y, int direction) {
		super(x, y, direction);
		speed=0.7; //The speed which the enemy travels, higher number resuts in higher speeds
		health = 175;
		reloadTime = 240;
		
		//using the blue enemy animations so it looks different
		anims[DOWN] = new Animation(Assets.tankBotD,4); //Different animations depending on the direction the enemy is facing
		anims[LEFT] = new Animation(Assets.tankBotL,4);
		anims[UP] = new Animation(Assets.tankBotU,4);
		anims[RIGHT] = new Animation(Assets.tankBotR,4);
		
		directionBounds[UP]=new Rectangle(-1,6,22,24);
		directionBounds[LEFT]=new Rectangle(6,0,27,26);
		directionBounds[DOWN]=new Rectangle(-1,0,22,24);
		directionBounds[RIGHT]=new Rectangle(0,0,27,26);
		init();
	}
	@Override
	public void update() {
		super.update();
		if(volleyShots>0) {
			volleyTime--;
			if(volleyTime<=0) {
				volleyTime=3;
				entityManager.addEntity(new Bullet (x+10,y+10, targetX, targetY,Assets.enemyBullet, 4, false));
				volleyShots--;
			}
			
		}
	}
	@Override
	protected void shoot() {
		targetX = entityManager.getPlayer().getX(); //Sets the players x location as the targetX
		targetY = entityManager.getPlayer().getY(); //Sets the players y location as the targetY
		volleyShots=3;
	}
}
