package entity.mobs.enemy;

import entity.mobs.Enemy;

public class DefaultEnemy extends Enemy{

	public DefaultEnemy(int x, int y, char direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		damage=1; //The amount of damage the enemy will do if it collides with the player
		shotDelayAmount = 60;
	}
}
