//@author Kevin

package entity.mobs.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.mobs.Mobs;
import entity.statics.Health;
import entity.statics.Money;
import floors.Floor;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

//@author Kevin (did animations, did shoot method, enemy shoot range and playerBox, shot delay, and rendering)
//@author Matthew (did update direction, and all damage/collision related code, width, height, damage, friendly)

	/*
	 * this is the robot enemy guy that will go around the path to the core and try to shoot you
	 */
public abstract class Enemy extends Mobs {
	protected char direction; //Depending on the direction the enemy will face different ways
	private int shotDelay; //Shot delay to make sure enemies can not shoot rapidly
	protected int rangeWidth = 150, rangeHeight = 150; //The specific width and height of the enemy's attack range
	private boolean attack = false; //If true enemy will shoot
	
	//setting default animations
	protected Animation animationDown = new Animation(Assets.enemyRedD,5); //Different animations depending on the direction the enemy is facing
	protected Animation animationLeft = new Animation(Assets.enemyRedL,5);
	protected Animation animationUp = new Animation(Assets.enemyRedU,5);
	protected Animation animationRight = new Animation(Assets.enemyRedR,5);

	public Enemy(int x, int y, char direction) { //Enemy Class contains traits of the enemies
		this.x=x;
		this.y=y;
		this.direction=direction;
		friendly=false;
	}

	public void updateDirection() {
		Assets.enemyRedD[0].getWidth(); //Enemy will always start travelling down
  //this checks what tile the enemy is currently on and changes its direction if it is a corner path tile
		switch (GameState.getFloor().getTile((x+width/2)/16,
				(y+height/2)/16)){ //getting its current tile
		case 7:
			direction='r';
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
			break;
		case 13:
			direction='u';
			break;
		case 14:
			direction='u';
			break;
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
	protected void shoot() {
		double targetX, targetY; 
		targetX = entityManager.getPlayer().getX(); //Sets the players x location as the targetX
		targetY = entityManager.getPlayer().getY(); //Sets the players y location as the targetY
		entityManager.addEntity(new Bullet (x,y, targetX, targetY,Assets.enemyBullet, 3, false)); //Creates red bullets that shoot towards the player
	}
	@Override
	public void damage() {
		super.damage();
		if(killed) {
			int randnum=ThreadLocalRandom.current().nextInt(0,20);//generating a random number to determine what should drop
			if(randnum==1) {
				entityManager.addEntity(new Health(x, y));
			}else if (randnum>=2) {
				entityManager.addEntity(new Money(x, y));
			}
		}
	}
	@Override
	public void update() {
		int tile=GameState.getFloor().getTile(x/16, y/16);
		Rectangle attackRange = new Rectangle(x,y,rangeWidth,rangeHeight); //The range which the enemy looks for targets
		Rectangle playerBox = new Rectangle(entityManager.getPlayer().getX(),entityManager.getPlayer().getY(),rangeWidth,rangeHeight); //The player's own box
		if(playerBox.intersects(attackRange)) {  //If the playerBox is intersects the attackRange attack will be set to true, and the enemy will attack the player 
			attack = true;
		}else{ //If the enemy can't detect the player's box it will not attack
			attack = false; 
		}
		if (shotDelay >= reloadTime && attack == true) { //If the enemy hasn't attacked in the last 30 frames and it detects the player's box it will shoot
			shoot();
			shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
		}
		updateDirection();//setting its direction and moving based on it
		move();//moving the enemy so its bounds can be updated 
		
		for(int i:Floor.DOORTILES) {
			if(i==tile) {
				GameState.getFloor().getRoom((x/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE,(y/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE).unlock();
			}
		}
		
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

