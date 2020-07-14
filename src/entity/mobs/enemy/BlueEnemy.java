package entity.mobs.enemy;

import graphics.Animation;
import graphics.Assets;

public class BlueEnemy extends Enemy{

	public BlueEnemy(int x, int y, char direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=0.8; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		damage=1; //The amount of damage the enemy will do if it collides with the player
		health = 50;
		reloadTime = 130;
		
		//using the blue enemy animations so it looks different
		animDown = new Animation(Assets.enemyBlueD,4); //Different animations depending on the direction the enemy is facing
		animLeft = new Animation(Assets.enemyBlueL,4);
		animUp = new Animation(Assets.enemyBlueU,4);
		animRight = new Animation(Assets.enemyBlueR,4);
	}
}
