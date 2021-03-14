package entity.mobs.enemy;

import java.awt.Rectangle;

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
		
		directionBounds[UP]=new Rectangle(0,0,20,24);
		directionBounds[LEFT]=new Rectangle(3,0,14,24);
		directionBounds[DOWN]=new Rectangle(0,0,20,24);
		directionBounds[RIGHT]=new Rectangle(3,0,14,24);
		init();
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		reloadTime = 15;
		health = 30;
		
		
	}
}
