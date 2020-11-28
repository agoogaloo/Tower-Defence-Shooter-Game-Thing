//@author Kevin

package entity.mobs.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
import states.console.ConsoleState;

//@author Kevin (did animations, did shoot method, enemy shoot range and playerBox, shot delay, and rendering)
//@author Matthew (did update direction, and all damage/collision related code, width, height, damage, friendly)

	/*
	 * this is the robot enemy guy that will go around the path to the core and try to shoot you
	 */
public abstract class Enemy extends Mobs {
	protected char direction, bufferedDirection; //Depending on the direction the enemy will face different ways
	protected int shotDelay, turnDelay; //Shot delay to make sure enemies can not shoot rapidly
	protected int rangeWidth = 150, rangeHeight = 150; //The specific width and height of the enemy's attack range
	private boolean attack = false; //If true enemy will shoot
	
	//setting default animations
	protected Animation animDown = new Animation(Assets.enemyRedD,5); //Different animations depending on the direction the enemy is facing
	protected Animation animLeft = new Animation(Assets.enemyRedL,5);
	protected Animation animUp = new Animation(Assets.enemyRedU,5);
	protected Animation animRight = new Animation(Assets.enemyRedR,5);
	protected BufferedImage currentPic;//the current sprite being drawn onto the screen

	public Enemy(int x, int y, char direction) { //Enemy Class contains traits of the enemies
		setLocation(x+width/2, y+height-8);
		this.direction=direction;
		bufferedDirection=direction;
		friendly=false;
		width=0;
		height=0;
		damage=0;
	}

	public void updateDirection() {
  //this checks what tile the enemy is currently on and changes its direction if it is a corner path tile
		int tile=GameState.getFloor().getTile((x+width/2)/16,(y+height-15)/16);
		if(bufferedDirection==direction) {//doing things if the enemy doesnt need to turn soon
			//setting turn delay to the right amount so that they will end up centered
			if(direction=='l'||direction=='r') {
				turnDelay=(int)(8/speed);
			}else if(direction=='u') {
				turnDelay=(int)(14/speed);//(int)((1)/speed);
			}else {
				turnDelay=0;
			}
			//getting ready to turn of they see a turning tile
			if(tile==4||tile==13) {
				bufferedDirection='r';
				
			}else if(tile==5||tile==6) {
				bufferedDirection='d';
				
			}else if(tile==12||tile==7) {
				bufferedDirection='l';
				
			}else if(tile==11||tile==14) {
				bufferedDirection='u';
			}
		}else {
			turnDelay--;
			if(turnDelay<=0) {
				direction=bufferedDirection;
				
			}
		}
		
		switch(direction) {//moving a different direction depending on which way it is facing
		case 'u'://if it is facing up 
			trueY-=speed;//it should move up
			break;
		case 'd'://move cases for the other directions
			trueY+=speed;
			break;
		case 'l':
			trueX-=speed;
			break;
		case 'r':
			trueX+=speed;
			break;
		}
		x=(int)(trueX);
		y=(int)(trueY);
	}
	
	protected void shoot() {
		double targetX, targetY; 
		targetX = entityManager.getPlayer().getX(); //Sets the players x location as the targetX
		targetY = entityManager.getPlayer().getY(); //Sets the players y location as the targetY
		entityManager.addEntity(new Bullet (x+10,y+10, targetX, targetY,Assets.enemyBullet, 3, false)); //Creates red bullets that shoot towards the player
	}
	@Override
	public void damage() {
		int initialHealth=health;
		super.damage();
		if(health!=initialHealth) {
			//making the enemy flash white when it gets hit
			if(ConsoleState.isInstaKillEnemy()) {
				killed=true;
			}
			currentPic=damageFlash(currentPic);
		}
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
		int tile=GameState.getFloor().getTile(x/16, y/16);//getting the tile the enemy is currently on
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
		updateBounds(); 
		
		//opening the door if the enemy runs into it
		/*for(int i:Floor.DOORTILES) {
			if(i==tile) {
				GameState.getFloor().getRoom((x/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE,(y/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE).unlock();
			}
		}*/
		
		animDown.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		animLeft.update(); //Animation and sprites change depending on the direction
		animUp.update();
		animRight.update();
		//setting the current picture to the right animation depending on its direction
		switch(direction) {
		case 'd':
			currentPic=animDown.getCurrentFrame();
			break;
		case 'u':
			currentPic=animUp.getCurrentFrame();
			break;
		case 'l':
			currentPic=animLeft.getCurrentFrame();
			break;
		case 'r':
			currentPic=animRight.getCurrentFrame();
		}
		shotDelay++; //Increase shotDelay by one every frame
	}
	
	public void render(Graphics g, Camera camera) { //Draws different enemy sprites depending on it's direction 
		g.drawImage(currentPic, x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
}

