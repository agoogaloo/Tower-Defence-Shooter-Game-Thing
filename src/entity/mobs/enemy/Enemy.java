//@author Kevin

package entity.mobs.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.mobs.Mobs;
import entity.statics.Health;
import entity.statics.Money;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.particles.ParticleEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;
import states.console.ConsoleState;

//@author Kevin (did animations, did shoot method, enemy shoot range and playerBox, shot delay, and rendering)
//@author Matthew (did update direction, and all damage/collision related code, width, height, damage, friendly)

	/*
	 * this is the robot enemy guy that will go around the path to the core and try to shoot you
	 */
public abstract class Enemy extends Mobs {
	public static final int DOWN=0,LEFT=1,UP=2,RIGHT=3;
	protected int direction, bufferedDirection; //Depending on the direction the enemy will face different ways
	protected int reloadTime, shotDelay, turnDelay; //Shot delay to make sure enemies can not shoot rapidly
	protected int rangeWidth = 150, rangeHeight = 150; //The specific width and height of the enemy's attack range
	private boolean attack = false; //If true enemy will shoot
	
	//setting default animations
	protected Animation[] anims = new Animation[4];	
	protected Rectangle[] directionBounds = new Rectangle[4];
	protected BufferedImage currentPic;//the current sprite being drawn onto the screen
	

	public Enemy(int x, int y, int direction) { //Enemy Class contains traits of the enemies
		setLocation(x, y);
		anims[DOWN] = new Animation(Assets.enemyRedD,5); //Different animations depending on the direction the enemy is facing
		anims[LEFT] = new Animation(Assets.enemyRedL,5);
		anims[UP] = new Animation(Assets.enemyRedU,5);
		anims[RIGHT] = new Animation(Assets.enemyRedR,5);
		this.direction=direction;
		bufferedDirection=direction;
		friendly=false;
		damage=0;
	}

	protected void init() {
		width=directionBounds[direction].width;
		height=directionBounds[direction].height;
		bounds=(Rectangle) directionBounds[direction].clone();
		setLocation(x-width/2, y-height/2);
	}
	protected void setDefaultBounds() {
		for(int i=0; i<directionBounds.length;i++) {
			directionBounds[i]= new Rectangle(0,0,width,height);
		}
	}
	
	public void updateDirection() {
  //this checks what tile the enemy is currently on and changes its direction if it is a corner path tile
		int tile=GameState.getFloor().getTile(Math.round((x+width/2)/16f-0.5f),Math.round((y+(height/2))/16-0.5f));
		if(bufferedDirection==direction) {//doing things if the enemy doesnt need to turn soon
			//setting turn delay to the right amount so that they will end up centered
			
			
			//getting ready to turn of they see a turning tile
			if(tile==4||tile==13) {
				bufferedDirection=RIGHT;
				
			}else if(tile==5||tile==6) {
				bufferedDirection=DOWN;
				
			}else if(tile==12||tile==7) {
				bufferedDirection=LEFT;
				
			}else if(tile==11||tile==14) {
				bufferedDirection=UP;
			}
			int distance=0;
			if(direction==DOWN) {
				distance+=2+directionBounds[bufferedDirection].height/2-directionBounds[direction].height/2;
			}else if(direction==UP) {
				distance+=14+directionBounds[bufferedDirection].height/2-directionBounds[direction].height/2;
			}else if(direction==LEFT) {
				distance+=8+directionBounds[bufferedDirection].width/2-directionBounds[direction].width/2;
			}else if(direction==RIGHT) {
				distance+=8-directionBounds[bufferedDirection].width/2+directionBounds[direction].width/2;
			}
			turnDelay=(int) Math.round(distance/speed);
		}else {
			turnDelay--;
			if(turnDelay<=1) {
				direction=bufferedDirection;
				
			}
		}
		
		switch(direction) {//moving a different direction depending on which way it is facing
		case UP://if it is facing up 
			trueY-=speed;//it should move up
			break;
		case DOWN://move cases for the other directions
			trueY+=speed;
			break;
		case LEFT:
			trueX-=speed;
			break;
		case RIGHT:
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
			currentPic=ImageUtils.fillPic(currentPic);
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
		if(statusEffect!=StatusEffect.STUN) {
			updateDirection();//setting its direction and moving based on it
		}
		updateBounds(); 
		
		if(statusEffect!=StatusEffect.STUN) {
			for(Animation i: anims) {
				i.update();
			}
		}if (statusEffect==StatusEffect.BURN){
			health-=statusLevel;
			new ParticleEffect(statusLevel, new Straight(new RectangleSpawner(x, y, width, height), -90, 3, 0.25),
					new ShrinkOvalParticle(new Timed(new Color(ThreadLocalRandom.current().nextInt(225, 255),
							ThreadLocalRandom.current().nextInt(120, 140),0), 120,30), 4,5,0.2,0.3), true);
		}else if (statusEffect==StatusEffect.POISON){
			if(statusLength%statusLevel==0) {
				health-=1;
				new ParticleEffect(1, new Straight(new RectangleSpawner(x, y, width, height), -90, 3, 0.1),
						new ShrinkOvalParticle(new Timed(new Color(ThreadLocalRandom.current().nextInt(0, 20),
								ThreadLocalRandom.current().nextInt(100, 170),ThreadLocalRandom.current().nextInt(0, 20))
								, 120,30), 6,0.1), true);
			}
		}
		
		//setting the current picture to the right animation depending on its direction
		currentPic=anims[direction].getCurrentFrame();
		xOffset=directionBounds[direction].x;
		yOffset=directionBounds[direction].y;
		width=directionBounds[direction].width;
		height=directionBounds[direction].height;
		shotDelay++; //Increase shotDelay by one every frame
		if(statusLength<=0) {
			statusEffect=StatusEffect.NONE;
		}else {
			statusLength--;
		}
	}
	
	public void render(Graphics g, Camera camera) { //Draws different enemy sprites depending on it's direction 
		g.drawImage(currentPic, x-camera.getxOffset()-xOffset, y-camera.getyOffset()-yOffset, null);
		//g.setColor(Color.pink);
		//g.fillRect(Math.round((x+width/2)/16f-0.5f)*16-camera.getxOffset(),Math.round((y+(height/2))/16-0.5f)*16-camera.getyOffset(), 16,16);
		//g.setColor(Color.BLUE);
		//g.drawRect(Math.round(x+width/2)-camera.getxOffset(),Math.round(y+(height/2))-camera.getyOffset(), 0,0);
	}
	
	public void setStatusEffect(StatusEffect statusEffect, int length, int level) {
		this.statusEffect = statusEffect;
		this.statusLength=length;
		this.statusLevel=level;
	}
}

