//@author Kevin

package entity.mobs;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Main;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

//@author Kevin (did animations, did shoot method, enemy shoot range and playerBox, shot delay, and rendering)
//@author Matthew (did update direction, and all damage/collision related code, width, height, damage, friendly)

public class Enemy extends Mobs {
	/*
	 * this is the robot enemy guy that will go around the path to the core and try to shoot you
	 */

	private char direction; //Depending on the direction the enemy will face different ways
	private int rangeWidth = 150, rangeHeight = 150; //The specific width and height of the enemy's attack range
	private int shotDelay = 0; //Shot delay to make sure enemies can not shoot rapidly
	private boolean attack = false; //If true enemy will shoot
	
	private Animation animationDown = new Animation(Assets.enemyD,4); //Different animations depending on the direction the enemy is facing
	private Animation animationLeft = new Animation(Assets.enemyL,4);
	private Animation animationUp = new Animation(Assets.enemyU,4);
	private Animation animationRight = new Animation(Assets.enemyR,4);

	public Enemy(int x, int y, char direction) { //Enemy Class contains traits of the enemies
		this.x=x;
		this.y=y;
		this.direction=direction;
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		friendly=false; //The status of the bullet, bullets from this class can hurt player
		damage=1; //The amount of damage the enemy will do if it collides with the player
	}
	
	private void updateDirection() {
		//this checks what tile the enemy is currently on and changes its direction if it is a corner path tile
		switch (Main.getWindow().getDisplay().getFloor().getTile((x+Assets.enemyD[0].getWidth()/2)/16,
				(y+Assets.enemyD[0].getHeight()/2)/16)){//getting its current tile
		case 5://the 5 tile is the path that goes from up to right so the robot should turn right
			direction='r';
			break;
		case 6:
			direction='u';//cases for all the other corner tiles
			break;
		case 7:
			direction='u';
			break;
		case 8:
			direction='r';
			break;
		case 9:
			direction='d';
			break;
		case 10:
			direction='d';
			break;
		case 11:
			direction='l';
			break;
		case 12:
			direction='l';
		}
		
		switch(direction) {//moving a different direction depending on which way it is facing
		case 'u'://if it is facing up 
			y-=speed;//it should move up
			break;
		case 'd'://move cases for the other directions
			y+=speed;
			break;
		case 'l':
			x-=speed;
			break;
		case 'r':
			x+=speed;
			break;
		}
	}
	private void shoot() {
		double targetX, targetY; 
		targetX = entityManager.getPlayer().getX(); //Sets the players x location as the targetX
		targetY = entityManager.getPlayer().getY(); //Sets the players y location as the targetY
		entityManager.addEntity(new Bullet (x,y, targetX, targetY,2, 3, false)); //Creates red bullets that shoot towards the player
		shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
	}
	
	@Override
	public void update() {
		Rectangle attackRange = new Rectangle(x,y,rangeWidth,rangeHeight); //The range which the enemy looks for targets
		Rectangle playerBox = new Rectangle(entityManager.getPlayer().getX(),entityManager.getPlayer().getY(),rangeWidth,rangeHeight); //The player's own box
		if(playerBox.intersects(attackRange)) {  //If the playerBox is intersects the attackRange attack will be set to true, and the enemy will attack the player 
			attack = true;
		}else{ //If the enemy can't detect the player's box it will not attack
			attack = false; 
		}
		if (shotDelay >= 30 && attack == true) { //If the enemy hasn't attacked in the last 30 frames and it detects the player's box it will shoot
			shoot();
		}
		updateDirection();//setting its direction and moving based on it
		move();//moving the enemy so its bounds can be updated 
		animationDown.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		animationLeft.update(); //Animation and spites change depending on the direction
		animationUp.update();
		animationRight.update();
		shotDelay+=1; //Increase shotDelay by one every frame
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

