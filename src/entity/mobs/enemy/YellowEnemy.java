package entity.mobs.enemy;

import java.awt.Graphics;

import entity.mobs.Enemy;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class YellowEnemy extends Enemy{

	public YellowEnemy(int x, int y, char direction) {
		super(x, y, direction);
		//changing the animations to the right colour
		animationDown = new Animation(Assets.enemyYellowD,4); //Different animations depending on the direction the enemy is facing
		animationLeft = new Animation(Assets.enemyYellowL,4);
		animationUp = new Animation(Assets.enemyYellowU,4);
		animationRight = new Animation(Assets.enemyYellowR,4);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		damage=999; //The amount of damage the enemy will do if it collides with the player
		shotDelayAmount = 1000;
		health = 1;
	}
	
	
	
	public void render(Graphics g, Camera camera) { //Draws different enemy sprites depending on it's direction 
		if (direction == 'd'){
			g.drawImage(animationDown.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'l') {
			g.drawImage(animationLeft.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'u') {
			g.drawImage(animationUp.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}else if (direction == 'r') {
			g.drawImage(animationRight.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}
	}
}
