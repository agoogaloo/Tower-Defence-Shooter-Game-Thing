package entity.mobs;

import java.awt.Graphics;

import Main.Main;
import entity.Entity;
import entity.EntityManager;
import entity.statics.Core;
import entity.statics.Tower;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;



/**
 * @author Sahib and Matthew
 */
public class Player extends Mobs {
	//declaring variables
	private int money=0;
	private int shotDelay = 0;
	private int numberOfTowers = 1;
	private boolean build = false;
	private Camera camera;
	private Core core;
	private PlayerInput input=new PlayerInput();//letting it get the inputs
	
	private Animation animationDown = new Animation(Assets.playerD,6);
	private Animation animationLeft = new Animation(Assets.playerL,6);
	private Animation animationUp = new Animation(Assets.playerU,6);
	private Animation animationRight = new Animation(Assets.playerR,6);
	

	public Player(int x, int y) {
		// initializing variables
		this.x = x;
		this.y = y;
		width = 16;
		height = 29;
		speed = 3;
		health = 3; 
		friendly=true;
		core=new Core(x,y);
		camera=Main.getWindow().getDisplay().getCamera();
		entityManager.addEntity(core);
		damage=0;
	}

	/**
	 * @author Kevin Tea
	 */
	public void shoot() {
		
		if (shotDelay <= 0) {
			double targetX, targetY;
			
			targetX = (input.getMouseX());
			targetY = (input.getMouseY());
			entityManager.addEntity(new Bullet(x, y, targetX+camera.getxOffset(), 
					targetY+camera.getyOffset(), 0, 5, true));
			shotDelay = 10;
		}
	}

	@Override
	public void update() {
		
		System.out.println(health);
		animationDown.update();
		animationLeft.update();
		animationUp.update();
		animationRight.update();
		input.update();// updating input so that it can get the current inputs
		health-=core.giveDamage();

		if (input.isShoot()) {
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
		if(input.isControl()) {
			tower();
		}
		shotDelay -= 1;
		move();
	}
	
	public void tower() {
		if(numberOfTowers>=1) {
			Tower tower = new Tower(x,y);
			entityManager.addEntity(tower);
			numberOfTowers-=1;
		}
	}
	
	//@author Kevin
	@Override
	public void render(Graphics g, Camera camera) {
		if (input.getDirection() == 'd') {

			g.drawImage(animationDown.getCurrentFrame(),
					x - camera.getxOffset(), y - camera.getyOffset(), null);
		}else if (input.getDirection() == 'l') {
			g.drawImage(animationLeft.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
		}else if (input.getDirection() == 'u') {
			g.drawImage(animationUp.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
		}else if (input.getDirection() == 'r') {
			g.drawImage(animationRight.getCurrentFrame(), x - camera.getxOffset(), y - camera.getyOffset(), null);
		}

	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean getBuild() {
		return build;
	}
}
