package entity.mobs.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.Bullet;
import entity.mobs.Mobs;
import entity.mobs.player.UI.MiniMap;
import entity.mobs.player.UI.PlayerUI;
import entity.mobs.player.UI.TowerPlacer;
import entity.statics.Core;
import entity.statics.towers.Tower;
import graphics.Assets;
import graphics.Camera;
import graphics.particles.ParticleEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;
import states.State;

//@author Matthew (did all of player movement, the player class, anything related to core)
//@author Kevin (did animation, shoot method, shot delay, anything related to tower, rendering)

public class Player extends Mobs {
	//declaring variables
	private int shotDelay = 0,dustDelay=0; //Prevents player from shooting too fast
	
	private int money=10,invincibility=0; //The amount of towers player can build
	private Camera camera; //Camera needed so it can follow player
	private Core core; //Core is related to player, as core effects player health
	
	private PlayerUI ui;
	private TowerPlacer towerPlacer;
	private MiniMap miniMap;
	private Animator animator=new Animator();
	
	
	private BufferedImage currentPic;//the current image of the player
	private PlayerAnimations currentAnim=PlayerAnimations.IDLEDOWN;
	
	private char direction='d'; //Sets player's direction to down by default at the start
	
	public Player(int x, int y) {
		
		/*
		 * this class is the player that you control 
		 */
		setLocation(x, y);
		// initializing variables
		reloadTime=15;
		width = 9; //The specific width of the player
		height = 16; //The specific height of the player
		speed = 3.4; //The speed which the player moves at, higher the value the faster the speed
		health = 6;  //The amount of health the player has, when health hits 0 the player "dies"
		damage=0; // The amount of damage the player will do when it runs into an enemy
		friendly=true; //its "team" so that it enemies will deal damage to you but you wont damage other things on your "team"
		camera=GameState.getCamera(); //The camera will follow the player
		
	}

	public void reset(int x, int y) {
		//this is called at the start of each floor so it can reset/set its ui/location
		setLocation(x, y);
		ui=new PlayerUI();
		towerPlacer=new TowerPlacer();
		miniMap=new MiniMap();
		
	}
	
	public void shoot() {
		if (shotDelay >= reloadTime) { //Allows the player to shoot every 10 frames
			double targetX, targetY;
			int angle=180;
			targetX = (State.getInputs().getMouseX()); //Sets the mousesX position to targetX
			targetY = (State.getInputs().getMouseY()); //Sets the mousesY position to targetY
			entityManager.addEntity(new Bullet(x+7, y+12, targetX+camera.getxOffset(), targetY+camera.getyOffset(), Assets.yellowBullet, 5, true)); //Creates a new Bullet, camera offset is applied as it effects the bullets velocity calculation
	
			shotDelay = 0; //Resets shotDelay to ensure player can not shoot for another 10 frames
			//adding a bit of screenshake so things feel better
			GameState.screenShake(0.07);	
			angle =(int)Math.round(Math.toDegrees(Math.atan2(targetY+camera.getyOffset()-y, 
					targetX+camera.getxOffset()-x)));
			new ParticleEffect(3, new Straight(new Point(x+7,y+12),angle,15,1), new OvalParticle(2, 
					new Timed(new Color(250,230,150),30)), true);
		}
	}

	@Override
	public void update() {
		int moveKeys=0;
		
		health-=core.giveDamage(); //If Core takes damage apply the damage to the player's health, as player shares damage with core
		ui.update(health, money);//updating ui with current health and money
		miniMap.update(entityManager.getEntities(), x, y);
		
		if (State.getInputs().isShoot()) { //If shoot in PlayerInput is triggered (by clicking) than it will call the shoot method
			shoot();
		}
		if (State.getInputs().isUp()) {// if the up input is triggered than it will move the player up
			changeY -= speed;
			direction='u';
			moveKeys++;
		}
		if (State.getInputs().isDown()) {// moving other directions
			changeY += speed;
			direction='d';
			moveKeys++;
		}
		if (State.getInputs().isLeft()) {
			changeX -= speed;
			direction='l';
			moveKeys++;
		}
		if (State.getInputs().isRight()) {
			changeX += speed;
			direction='r';
			moveKeys++;
		}
		if(moveKeys>=2) {
			changeX/=1.25;
			changeY/=1.25;
		}
		
		tower();
		
		if(changeX==0&&changeY==0) {
			currentPic=animator.update(direction, false);
			dustDelay=20;
		}else {
			currentPic=animator.update(direction, true);
			if(dustDelay>=15) {
				new ParticleEffect(3, new Straight(new Point(x+7,y+12),0.5), 
						new OvalParticle(2, new Timed(45)), false);
				dustDelay=0;
			}
		}
		
		move(); //Updates movements, applied by the directional input keys. Also updates bounds and applies wall collision
		shotDelay ++; //Increase shotDelay by one every frame
		dustDelay ++;
		invincibility--;
		
		
	}
	
	private void tower() { //Tower method to create a tower
		Tower tower=towerPlacer.update(money,camera, entityManager.getEntities(), direction);
		int moneySpent=towerPlacer.getSpentMoney();
		entityManager.addEntity(tower);
		money-=moneySpent;
	}
	
	@Override
	public void damage() {
		if(invincibility<=0) {
			for (Entity e : entityCollide()) {//checking what is colliding with itself
				//checking which side the thing that touched it is on 
				//(making sure enemies only attack the player, player cant attack the core, etc.)
				if (e.isFriendly() != friendly) {
					health -= e.getDamage();//dealing however much damage that entity does
					if(e.getDamage()>0) {//making sure you actually take damage from the entity
						//shaking the screen so it feels like you actually got hit
						GameState.screenShake(0.75);
						currentPic=damageFlash(currentPic);
						invincibility=30;
					}
				}
			}
			if (health <= 0) {//if it has no more health left that it should be dead
				killed = true;
			}
		}
	}
	@Override
	public void render(Graphics g, Camera camera) { //Draws different player sprites depending on it's direction 
		g.drawImage(currentPic,x - camera.getxOffset(), y - camera.getyOffset(), null);
		towerPlacer.render(g, camera);
		miniMap.render(g);
		//drawHitBox(g, camera);
	}
	
	public void createCore() {
		createCore(x,y);
	}
	
	public void createCore(int x, int y) {
		if(core!=null) {//making sure there is a core to destroy so it wont throw an error
			core.destroy();//removing the old core
		}
		core=new Core(x,y); //makes the core where the paramiters tell it to
		entityManager.addEntity(core); //Adds the core to the entityManager so it can exist
	}
	
	public void giveMoney(int amount) {
		//lets us give the player money
		money+=amount;
	}
	public void heal(int amount) {
		//lets other things heal the player
		health+=amount;
	}
}
