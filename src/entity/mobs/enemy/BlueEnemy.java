package entity.mobs.enemy;

import entity.mobs.Enemy;
import graphics.Animation;
import graphics.Assets;

public class BlueEnemy extends Enemy{

	public BlueEnemy(int x, int y, char direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		damage=1; //The amount of damage the enemy will do if it collides with the player
		health = 100;
		shotDelayAmount = 120;
		
		//using the blue enemy animations so it looks different
		animationDown = new Animation(Assets.enemyBlueD,4); //Different animations depending on the direction the enemy is facing
		animationLeft = new Animation(Assets.enemyBlueL,4);
		animationUp = new Animation(Assets.enemyBlueU,4);
		animationRight = new Animation(Assets.enemyBlueR,4);
	}
}
