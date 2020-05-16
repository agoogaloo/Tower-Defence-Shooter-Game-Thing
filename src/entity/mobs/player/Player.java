package entity.mobs.player;

import java.awt.Graphics;

import entity.Entity;
import entity.mobs.Bullet;
import entity.mobs.Mobs;
import entity.statics.Core;
import entity.statics.towers.LaserTowerlvl1;
import entity.statics.towers.Tower;
import entity.statics.towers.WizardTowerlvl2;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;
import states.State;

//@author Matthew (did all of player movement, the player class, anything related to core)
//@author Kevin (did animation, shoot method, shot delay, anything related to tower, rendering)

public class Player extends Mobs {
	//declaring variables
	private int shotDelay = 0; //Prevents player from shooting too fast
	
	private int money=10,invincibility=0; //The amount of towers player can build
	private Camera camera; //Camera needed so it can follow player
	private Core core; //Core is related to player, as core effects player health
	
	private PlayerUI ui=new PlayerUI();
	private TowerPlacer towerPlacer=new TowerPlacer();
	
	private Animation animationDown = new Animation(Assets.playerD,6); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation animationLeft = new Animation(Assets.playerL,6);
	private Animation animationUp = new Animation(Assets.playerU,6);
	private Animation animationRight = new Animation(Assets.playerR,6);
	
	private char direction='d'; //Sets player's direction to down by default at the start
	
	public Player(int x, int y) {
		/*
		 * this class is the player that you control 
		 */
		// initializing variables
		
		this.x = x;
		this.y = y;
		reloadTime=15;
		width = 14; //The specific width of the player
		height = 25; //The specific height of the player
		speed = 3; //The speed which the player moves at, higher the value the faster the speed
		health = 5;  //The amount of health the player has, when health hits 0 the player dies
		damage=0; // The amount of damage the player will do when it runs into an enemy
		friendly=true; //The status of the bullets shot by this class, can hurt enemies but it's own bullets won't damage itself
		core=new Core(x,y); //Calls the core class, spawning it where the player spawns, AKA spawns the core at the start
		camera=GameState.getCamera(); //The camera will follow the player
		entityManager.addEntity(core); //Adds the core to the entityManager allowing it to detect collisions0
	}

	public void shoot() {
		if (shotDelay >= reloadTime) { //Allows the player to shoot every 10 frames
			double targetX, targetY;
			targetX = (State.getInputs().getMouseX()); //Sets the mousesX position to targetX
			targetY = (State.getInputs().getMouseY()); //Sets the mousesY position to targetY
			entityManager.addEntity(new Bullet(x+7, y+12, targetX+camera.getxOffset(), targetY+camera.getyOffset(), Assets.yellowBullet, 5, true)); //Creates a new Bullet, camera offset is applied as it effects the bullets velocity calculation
			shotDelay = 0; //Resets shotDelay to ensure player can not shoot for another 10 frames
		}
	}

	@Override
	public void update() {
		//System.out.println(health+", "+money);
		animationDown.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		animationLeft.update(); //Animation and sprites change depending on the direction
		animationUp.update();
		animationRight.update();
		
		health-=core.giveDamage(); //If Core takes damage apply the damage to the player's health, as player shares damage with core
		ui.update(health, money);
		if (State.getInputs().isShoot()) { //If shoot in PlayerInput is triggered (by clicking) than it will call the shoot method
			shoot();
		}
		if (State.getInputs().isUp()) {// if the up input is triggered than it will move the player up
			changeY -= speed;
			direction='u';
		}
		if (State.getInputs().isDown()) {// moving other directions
			changeY += speed;
			direction='d';
		}
		if (State.getInputs().isLeft()) {
			changeX -= speed;
			direction='l';
		}
		if (State.getInputs().isRight()) {
			changeX += speed;
			direction='r';
		}
		tower();
		move(); //Updates movements, applied by the directional input keys. Also updates bounds and applies wall collision
		shotDelay += 1; //Increase shotDelay by one every frame
		invincibility--;
	}
	
	public void tower() { //Tower method to create a tower
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
					invincibility=30;
				}
			}
			if (health <= 0) {//if it has no more health left that it should be dead
				killed = true;
			}
		}
	}
	@Override
	public void render(Graphics g, Camera camera) { //Draws different player sprites depending on it's direction 
		switch(direction) {
		case 'd':
			g.drawImage(animationDown.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
			break;
		case 'l':
			g.drawImage(animationLeft.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
			break;
		case'u':
			g.drawImage(animationUp.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
			break;
		case'r':
			g.drawImage(animationRight.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
		}
		towerPlacer.render(g, camera);
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
