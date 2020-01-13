package entity.mobs.enemy;

import java.awt.Graphics;

import entity.mobs.Enemy;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

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
