//@author Kevin

package entity.mobs.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import audio.AudioManager;
import entity.mobs.Bullet;
import entity.mobs.Mobs;
import entity.mobs.pickups.Health;
import entity.mobs.pickups.Money;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.RectangleShape;
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
	public static final int DOWN=0,LEFT=1,UP=2,RIGHT=3, UPTILE=18, RIGHTTILE=19,DOWNTILE=20, LEFTTILE=21;
	protected int direction, bufferedDirection; //Depending on the direction the enemy will face different ways
	protected int reloadTime, shotDelay, turnDelay; //Shot delay to make sure enemies can not shoot rapidly
	protected int rangeWidth = 150, rangeHeight = 150; //The specific width and height of the enemy's attack range
	private boolean attack = false; //If true enemy will shoot
	
	//setting default animations
	protected Animation[] anims = new Animation[4];	
	protected Rectangle[] directionBounds = new Rectangle[4];
	protected BufferedImage currentPic;//the current sprite being drawn onto the screen
	
	//status effects;
	boolean stunned=false, jammed=false;;
	double weakened=1;
	

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
	public abstract Enemy createNew(int x, int y, int direction);
	
	public void init() {
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
		int tile=GameState.getFloor().getSpawnData(Math.round((x+width/2)/16f-0.5f),Math.round((y+(height/2))/16-0.5f));
		if(bufferedDirection==direction) {//doing things if the enemy doesnt need to turn soon
			//setting turn delay to the right amount so that they will end up centered
			
			
			//getting ready to turn of they see a turning tile
			if(tile==RIGHTTILE) {
				bufferedDirection=RIGHT;
				
			}else if(tile==DOWNTILE) {
				bufferedDirection=DOWN;
				
			}else if(tile==LEFTTILE) {
				bufferedDirection=LEFT;
				
			}else if(tile==UPTILE) {
				bufferedDirection=UP;
			}
			int distance=0;
			if(direction==DOWN) {
				distance+=8-directionBounds[bufferedDirection].height/2+directionBounds[direction].height/2;
			}else if(direction==UP) {
				distance+=8+directionBounds[bufferedDirection].height/2-directionBounds[direction].height/2;
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
		entityManager.addEntity(new Bullet (x+10,y+10, targetX, targetY,Assets.enemyBullet, 3,120, false)); //Creates red bullets that shoot towards the player
	}
	@Override
	public void damage(double amount) {
		super.damage(amount*weakened);
	
		if(amount>0) {
			AudioManager.playSound(AudioManager.enemyHit);
			currentPic=ImageUtils.fillPic(currentPic);
			//making the enemy flash white when it gets hit
			if(ConsoleState.isInstaKillEnemy()) {
				killed=true;
			}
			
		}
		if(killed) {
			AudioManager.playSound(AudioManager.enemyDie);
			new InstantEffect(15, new Straight(new Point((int)x+width/2, (int)y+height/2),0.6),
					new ShrinkOvalParticle(new Timed(Color.darkGray, 60), 12,0.5), true);
			new InstantEffect(15, new Straight(new Point((int)x+width/2, (int)y+height/2),0.9),
					new ShrinkOvalParticle(new Timed(Color.white, 60), 10,0.3), true);
			
			int randnum=ThreadLocalRandom.current().nextInt(0,20);//generating a random number to determine what should drop
			if(randnum==1) {
				entityManager.addEntity(new Health(x, y));
			}else if (randnum>=2) {
				entityManager.addEntity(new Money(x, y));
			}
		}
	}
	
	private void updateEffects() {
		stunned=false;
		jammed=false;
		weakened=1;
		for(int i=currentEffects.size()-1;i>=0;i--) {
			currentEffects.get(i).update();
			if(!currentEffects.get(i).isActive()) {
				currentEffects.remove(i); 
				continue;
			}
			switch (currentEffects.get(i).getType()) {
			case STUN:
				stunned=true;
				break;
				
			case JAMMED:
				jammed=true;
				break;
				
			case WEAKENED:
				weakened=currentEffects.get(i).getLevel();
				new InstantEffect(1, new Straight(new RectangleSpawner(x,y,width,5)
								,90,0,0.5),new RectangleShape(1, 3, new Timed(new Color(33,166,144), 20)),true);
				break;
				
			case BURN:
				health-=currentEffects.get(i).getLevel();
				new InstantEffect(1, new Straight(new RectangleSpawner(x, y, width, height), -90, 3, 0.25),
						new ShrinkOvalParticle(new Timed(new Color(ThreadLocalRandom.current().nextInt(225, 255),
								ThreadLocalRandom.current().nextInt(120, 140),0), 120,30), 4,5,0.2,0.3), true);
				break;
				
			case POISON:
				System.out.println("poisoned");
				health-=currentEffects.get(i).getLevel();
				new InstantEffect(1, new Straight(new RectangleSpawner(x, y, width, height), -90, 3, 0.1),
						new ShrinkOvalParticle(new Timed(new Color(ThreadLocalRandom.current().nextInt(0, 20),
								ThreadLocalRandom.current().nextInt(100, 170),ThreadLocalRandom.current().nextInt(0, 20))
								, 120,30), 5,0.1), true);
				break;
				
			default:
				break;
			}
			
			
		}
	}
	@Override
	public void update() {
		Rectangle attackRange = new Rectangle(x,y,rangeWidth,rangeHeight); //The range which the enemy looks for targets
		Rectangle playerBox = new Rectangle(entityManager.getPlayer().getX(),entityManager.getPlayer().getY(),rangeWidth,rangeHeight); //The player's own box
		if(playerBox.intersects(attackRange)&&!jammed) {  //If the playerBox is intersects the attackRange attack will be set to true, and the enemy will attack the player 
			attack = true;
		}else{ //If the enemy can't detect the player's box it will not attack
			attack = false; 
		}
		if (shotDelay >= reloadTime && attack == true) { //If the enemy hasn't attacked in the last 30 frames and it detects the player's box it will shoot
			shoot();
			shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
		}
		updateEffects();
		
		if(!stunned) {
			updateDirection();//setting its direction and moving based on it
		}
		
		updateBounds();
		
		
		if(!stunned) {
			for(Animation i: anims) {
				i.update();
			}
		}
		
		//setting the current picture to the right animation depending on its direction
		currentPic=anims[direction].getCurrentFrame();
		xOffset=directionBounds[direction].x;
		yOffset=directionBounds[direction].y;
		width=directionBounds[direction].width;
		height=directionBounds[direction].height;
		shotDelay++; //Increase shotDelay by one every frame
		
	}
	
	public void render(Graphics g, Camera camera) { //Draws different enemy sprites depending on it's direction 
		g.drawImage(currentPic, x-camera.getxOffset()-xOffset, y-camera.getyOffset()-yOffset, null);
		//g.setColor(Color.pink);
		//g.fillRect(Math.round((x+width/2)/16f-0.5f)*16-camera.getxOffset(),Math.round((y+(height/2))/16-0.5f)*16-camera.getyOffset(), 16,16);
		//g.setColor(Color.BLUE);
		//g.drawRect(Math.round(x+width/2)-camera.getxOffset(),Math.round(y+(height/2))-camera.getyOffset(), 0,0);
	}
	
	
	
	
}

