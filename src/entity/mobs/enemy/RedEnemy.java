package entity.mobs.enemy;

import java.awt.Rectangle;

public class RedEnemy extends Enemy{

	public RedEnemy(int x, int y, int direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		setDefaultBounds();
		health=110;
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		reloadTime = 60;
		directionBounds[UP]=new Rectangle(0,0,20,24);
		directionBounds[LEFT]=new Rectangle(3,0,14,24);
		directionBounds[DOWN]=new Rectangle(0,0,20,24);
		directionBounds[RIGHT]=new Rectangle(3,0,14,24);
		init();
	}
}
