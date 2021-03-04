package entity.mobs.enemy;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class TankBot extends Enemy{

	private int volleyTime=0, volleyShots=0;
	private double targetX, targetY;
	public TankBot(int x, int y, char direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=0.7; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		health = 50;
		reloadTime = 240;
		
		//using the blue enemy animations so it looks different
		animDown = new Animation(Assets.tankBotD,4); //Different animations depending on the direction the enemy is facing
		animLeft = new Animation(Assets.tankBotL,4);
		animUp = new Animation(Assets.tankBotU,4);
		animRight = new Animation(Assets.tankBotR,4);
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
