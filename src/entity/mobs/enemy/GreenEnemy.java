package entity.mobs.enemy;

import java.awt.Rectangle;

import graphics.Animation;
import graphics.Assets;

public class GreenEnemy extends Enemy {

	
	
	public GreenEnemy(int x, int y, int direction) {
		super(x, y, direction);
		//changing the animations to the right pictures
		anims[DOWN] = new Animation(Assets.enemyGreenD,4); //Different animations depending on the direction the enemy is facing
		anims[LEFT] = new Animation(Assets.enemyGreenL,4);
		anims[UP] = new Animation(Assets.enemyGreenU,4);
		anims[RIGHT] = new Animation(Assets.enemyGreenR,4);
		
		directionBounds[UP]=new Rectangle(0,0,20,24);
		directionBounds[LEFT]=new Rectangle(3,0,14,24);
		directionBounds[DOWN]=new Rectangle(0,0,20,24);
		directionBounds[RIGHT]=new Rectangle(3,0,14,24);
		init();
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=2; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		health=50;
		reloadTime = 40;
	}
}
