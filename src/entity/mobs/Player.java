package entity.mobs;

import java.awt.Graphics;

import Main.Main;
import entity.Entity;
import entity.EntityManager;
import entity.statics.Core;
import entity.statics.Tower;
import entity.statics.WizardTower;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;



/**
 * @author Sahib and Matthew
 */
public class Player extends Mobs {
	//declaring variables
	private int money=0;
	private int shotBuffer = 0;
	private int numberOfTowers = 1;
	private boolean build = false;
	
	private Camera camera;
	private Core core;
	private WizardTower wizardTower;
	private PlayerInput input=new PlayerInput();//letting it get the inputs
	
	private Animation animationDown = new Animation(Assets.playerD,6);
	private Animation animationLeft = new Animation(Assets.playerL,6);
	private Animation animationUp = new Animation(Assets.playerU,6);
	private Animation animationRight = new Animation(Assets.playerR,6);
	

	public Player(int x, int y, int width, int height) {
		// initializing variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		speed = 3;
		health = 3; 
		core=new Core(x,y);
		camera=Main.getWindow().getDisplay().getCamera();
		entityManager.addEntity(core);
	}

	/**
	 * @author Kevin Tea
	 */
	public void shoot() {

		if (shotBuffer <= 0) {
			double targetX, targetY;
			targetX = (input.getMouseX());
			targetY = (input.getMouseY());
			entityManager.addEntity(new Bullet(x, y, targetX+camera.getxOffset(), targetY+camera.getyOffset(), Assets.bullet[0].getWidth(), Assets.bullet[0].getHeight(), 0, 5));
			shotBuffer = 10;
		}
	}
	
	private void playerCollide() {
		for(Entity e: entityCollide()) {
			if(e instanceof Enemy) {
				health-=1;
			}
		}
		if(health<=0){
			killed = true;	
		}else {
			killed = false;
		}
	}

	@Override
	public void update() {
		updateBounds();
		playerCollide();
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
			velocityX -= speed;
		}
		if (input.isRight()) {
			velocityX += speed;
		}
		if (input.isControl() ) {
			tower();
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+velocityX)/16,(y+changeY)/16)){
			velocityX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+16+velocityX)/16,(y+changeY)/16)){
			velocityX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+velocityX)/16,(y+29+changeY)/16)){
			velocityX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+16+velocityX)/16,(y+29+changeY)/16)){
			velocityX=0;
			changeY=0;
		}
		x += velocityX;// actually moving the player
		y += changeY;
		velocityX = 0;// resting change x and y
		changeY = 0;
		
		shotBuffer -= 1;
		
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
