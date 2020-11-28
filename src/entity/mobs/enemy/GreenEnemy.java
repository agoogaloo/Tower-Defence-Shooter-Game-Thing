package entity.mobs.enemy;

import graphics.Animation;
import graphics.Assets;

public class GreenEnemy extends Enemy {

	
	
	public GreenEnemy(int x, int y, char direction) {
		super(x, y, direction);
		//changing the animations to the right pictures
		animDown = new Animation(Assets.enemyGreenD,4); //Different animations depending on the direction the enemy is facing
		animLeft = new Animation(Assets.enemyGreenL,4);
		animUp = new Animation(Assets.enemyGreenU,4);
		animRight = new Animation(Assets.enemyGreenR,4);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=2; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		health=5;
		reloadTime = 40;
	}
}
