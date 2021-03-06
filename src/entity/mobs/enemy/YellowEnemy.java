package entity.mobs.enemy;

import graphics.Animation;
import graphics.Assets;

public class YellowEnemy extends Enemy{

	public YellowEnemy(int x, int y, int direction) {
		super(x, y, direction);
		//changing the animations to the right colour
		anims[DOWN] = new Animation(Assets.enemyYellowD,4); //Different animations depending on the direction the enemy is facing
		anims[LEFT] = new Animation(Assets.enemyYellowL,4);
		anims[UP] = new Animation(Assets.enemyYellowU,4);
		anims[RIGHT] = new Animation(Assets.enemyYellowR,4);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		setDefaultBounds();
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		reloadTime = 30;
		health = 3;
	}
}
