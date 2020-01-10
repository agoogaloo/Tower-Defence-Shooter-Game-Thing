package entity.mobs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.statics.Core;
import graphics.Assets;
import graphics.Camera;


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
	private Core core;
	
	PlayerInput input=new PlayerInput();//letting it get the inputs

  private Assets assets = new Assets();
	BufferedImage[] pics=assets.getPlayer();
	
	public Player(int x, int y) {
		// initializing variables
		speed = 3;
		health = 100;
		this.x = x;
		this.y = y;
		core=new Core(x,y);
		camera=Main.Main.getWindow().getDisplay().getCamera();
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
			System.out.println("targetX " + targetX + " targetY " + targetY);
			System.out.println("peew");
			entityManager.addEntity(new Bullet(x, y, targetX+camera.getxOffset(), targetY+camera.getyOffset()));
			//shotBuffer = 30;
		}
	}

	@Override
	public void update() {
		super.update();
		input.update();// updating input so that it can get the current inputs
		health-=core.giveDamage();
		System.out.println(health);
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

		shotBuffer -= 1;
	}
	//@author Kevin
	@Override
	public void render(Graphics g, Camera camera) {
		if (input.getDirection() == 'd') {
			g.drawImage(pics[0], x - camera.getxOffset(), y - camera.getyOffset(), null);
			
		}else if (input.getDirection() == 'l') {
			g.drawImage(pics[7],x - camera.getxOffset(), y - camera.getyOffset(), null);
		
		}else if (input.getDirection() == 'u') {
			g.drawImage(pics[14], x - camera.getxOffset(), y - camera.getyOffset(), null);

		}else if (input.getDirection() == 'r') {
			g.drawImage(pics[21], x - camera.getxOffset(), y - camera.getyOffset(), null);
		
		}

	}
}
