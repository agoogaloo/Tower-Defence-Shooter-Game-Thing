package entity.mobs.enemy;

import entity.mobs.Enemy;
import graphics.Animation;
import graphics.Assets;

public class GreenEnemy extends Enemy {

	
	
	public GreenEnemy(int x, int y, char direction) {
		super(x, y, direction);
		//changing the animations to the right pictures
		animationDown = new Animation(Assets.enemyGreenD,4); //Different animations depending on the direction the enemy is facing
		animationLeft = new Animation(Assets.enemyGreenL,4);
		animationUp = new Animation(Assets.enemyGreenU,4);
		animationRight = new Animation(Assets.enemyGreenR,4);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=2; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		damage=1; //The amount of damage the enemy will do if it collides with the player
		health=5;
		shotDelayAmount = 40;
	}
}
