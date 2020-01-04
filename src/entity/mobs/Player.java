package entity.mobs;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

/**
 * @author Sahib and Matthew
 */
public class Player extends Mobs {
	//declaring variables
	int money=0;
	int width = 50, height = 50;
	int shotBuffer = 0;
	
	int health = 100;
	double bulletPath;
	
	PlayerInput input=new PlayerInput();//letting it get the inputs
	BufferedImage[] pics;
	
	public Player(BufferedImage[] pics) {
		this.pics=pics;//the pictures that are drawn where the player is
		speed = 2;
	}

	/**
	 * @author Kevin Tea
	 */
	public void shoot() {
		
		if (shotBuffer <= 0) {
			double targetX, targetY;
			targetX = MouseInfo.getPointerInfo().getLocation().getX();
			targetY = MouseInfo.getPointerInfo().getLocation().getY();
			System.out.println("peew");
			entityManager.addEntity(new Bullet(x, y, targetX, targetY));
			shotBuffer = 30;
		}
	}


	@Override
	public void update() {
		input.update();//updating input so that it can get the current inputs 
		if(input.isShoot()) {
			shoot();

		}
		if(input.isUp()) {//if the up input is triggered than itwil move the player up
			changeY-=speed;
		}
		if(input.isDown()) {//moving other directions
			changeY+=speed;
		}
		if(input.isLeft()) {
			changeX-=speed;
		}
		if(input.isRight()) {
			changeX+=speed;
		}
		x += changeX;//actually moving the player
		y += changeY;
		changeX=0;//resting change x and y
		changeY=0;
//		if (EntityManager.getEntities().contains(Bullet)){
//			health-=2;
//		}
//		if (EntityManager.getEntities().contains(enemies)){
//			health-=1;
//		}
		
		shotBuffer -= 1;
	}

	@Override
	public void render(Graphics g) {
		if (input.getDirection() == 'd') {
			g.drawImage(pics[0], x, y, null);
			System.out.println("Down");
		}else if (input.getDirection() == 'l') {
			g.drawImage(pics[7], x, y, null);
			System.out.println("Left");
		}else if (input.getDirection() == 'u') {
			g.drawImage(pics[14], x, y, null);
			System.out.println("Up");
		}else if (input.getDirection() == 'r') {
			g.drawImage(pics[21], x, y, null);
			System.out.println("Right");
		}
	}
}
