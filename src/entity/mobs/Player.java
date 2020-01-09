package entity.mobs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Camera;
import graphics.Animation;

/**
 * @author Sahib and Matthew
 */
public class Player extends Mobs {
	//declaring variables
	private int money=0;
	private int width = 50, height = 50;
	private int shotBuffer = 0;
	private int health = 100;
	private double bulletPath;
	
	private Camera camera;
	
	private Assets assets = new Assets();
	PlayerInput input=new PlayerInput();//letting it get the inputs


	Animation animationDown = new Animation(Assets.playerD,6);
	Animation animationLeft = new Animation(Assets.playerL,6);
	Animation animationUp = new Animation(Assets.playerU,6);
	Animation animationRight = new Animation(Assets.playerR,6);
//	Animation animation = new Animation(pics,10);
	public Player() {
		// initializing variables
		speed = 3;
		health = 100;
		x = 1600;
		y = 1600;
		this.camera=Main.Main.getWindow().getDisplay().getCamera();
	}

	/**
	 * @author Kevin Tea
	 */
	public void shoot() {

		if (shotBuffer <= 0) {
			double targetX, targetY;
			targetX = (input.getMouseX());
			targetY = (input.getMouseY());
			System.out.println("peew");
			entityManager.addEntity(new Bullet(x, y, targetX+camera.getxOffset(), targetY+camera.getyOffset(), 0));
			shotBuffer = 10;
		}
	}

	@Override
	public void update() {
		animationDown.update();
		animationLeft.update();
		animationUp.update();
		animationRight.update();
		input.update();// updating input so that it can get the current inputs
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
		x += changeX;// actually moving the player
		y += changeY;
		changeX = 0;// resting change x and y
		changeY = 0;
//		if (EntityManager.getEntities().contains(Bullet)){
//			health-=2;
//		}
//		if (EntityManager.getEntities().contains(enemies)){
//			health-=1;
//		}

		shotBuffer -= 1;
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
}
