package entity.mobs;

import java.awt.Graphics;

import Main.Main;
import entity.statics.Core;
import entity.statics.Tower;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

//@author Matthew (did all of player movement, the player class, anything related to core)
//@author Kevin (did animation, shoot method, shot delay, anything related to tower, rendering)

public class Player extends Mobs {
	//declaring variables
	private int shotDelay = 0; //Prevents player from shooting too fast
	private int numberOfTowers = 1; //The amount of towers player can build
	private Camera camera; //Camera needed so it can follow player
	private Core core; //Core is related to player, as core effects player health
	private PlayerInput input=new PlayerInput(); //Letting player get all the inputs in PlayerInput
	
	private Animation animationDown = new Animation(Assets.playerD,6); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation animationLeft = new Animation(Assets.playerL,6);
	private Animation animationUp = new Animation(Assets.playerU,6);
	private Animation animationRight = new Animation(Assets.playerR,6);
	
	public Player(int x, int y) {
		/*
		 * this class is the player that you control 
		 */
		// initializing variables
		this.x = x;
		this.y = y;
		width = 16; //The specific width of the player
		height = 29; //The specific height of the player
		speed = 3; //The speed which the player moves at, higher the value the faster the speed
		health = 3;  //The amount of health the player has, when health hits 0 the player dies
		damage=0; // The amount of damage the player will do when it runs into an enemy
		friendly=true; //The status of the bullets shot by this class, can hurt enemies but it's own bullets won't damage itself
		core=new Core(x,y); //Calls the core class, spawning it where the player spawns, AKA spawns the core at the start
		entityManager.addEntity(core); //Adds the core to the entityManager allowing it to detect collisions
		camera=Main.getWindow().getDisplay().getCamera(); //The camera will follow the player
	}

	public void shoot() {
		if (shotDelay >= 10) { //Allows the player to shoot every 10 frames
			double targetX, targetY;
			targetX = (input.getMouseX()); //Sets the mousesX position to targetX
			targetY = (input.getMouseY()); //Sets the mousesY position to targetY
			entityManager.addEntity(new Bullet(x, y, targetX+camera.getxOffset(), targetY+camera.getyOffset(), 0, 5, true)); //Creates a new Bullet, camera offset is applied as it effects the bullets velocity calculation
			shotDelay = 0; //Resets shotDelay to ensure player can not shoot for another 10 frames
		}
	}

	@Override
	public void update() {
		animationDown.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		animationLeft.update(); //Animation and sprites change depending on the direction
		animationUp.update();
		animationRight.update();
		input.update(); //Updating input so that it can get the current inputs
		
		health-=core.giveDamage(); //If Core takes damage apply the damage to the player's health, as player shares damage with core

		if (input.isShoot()) { //If shoot in PlayerInput is triggered (by clicking) than it will call the shoot method
			shoot();
		}
		if (input.isUp()) {// if the up input is triggered than it will move the player up
			changeY -= speed;
		}
		if (input.isDown()) {// moving other directions
			changeY += speed;
		}
		if (input.isLeft()) {
			changeX -= speed;
		}
		if (input.isRight()) {
			changeX += speed;
		}
		if(input.isControl()) { //If control is pressed call the twoer method
			tower();
		}
		move(); //Updates movements, applied by the directional input keys. Also updates bounds and applies wall collision
		shotDelay += 1; //Increase shotDelay by one every frame
	}
	
	public void tower() { //Tower method to create a tower
		if(numberOfTowers>=1) { //As long as the player has at least one tower they can create a tower
			Tower tower = new Tower(x,y); //Creates a tower at the player's current location
			entityManager.addEntity(tower); //Adds that tower to the entityManager
			numberOfTowers-=1; //Decreases the amount of towers the player holds by 1 
		}
	}
	
	@Override
	public void render(Graphics g, Camera camera) { //Draws different player sprites depending on it's direction 
		if (input.getDirection() == 'd') {

			g.drawImage(animationDown.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
		}else if (input.getDirection() == 'l') {
			g.drawImage(animationLeft.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
		}else if (input.getDirection() == 'u') {
			g.drawImage(animationUp.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
		} else if (input.getDirection() == 'r') {
			g.drawImage(animationRight.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
		}
	}
}
