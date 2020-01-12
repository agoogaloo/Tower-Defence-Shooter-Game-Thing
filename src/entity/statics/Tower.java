package entity.statics;

import java.awt.Graphics;
import java.awt.Rectangle;
import entity.mobs.Enemy;
import entity.mobs.Player;
import entity.Entity;
import entity.EntityManager;
import entity.mobs.Bullet;
import entity.mobs.PlayerInput;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

/**
 * @author Kevin Tea
 */
public class Tower extends Statics {
	
	boolean attack = false;
	boolean build = false;
	int shotDelay = 0;
	int rangeWidth = 100, rangeHeight = 100;
	Rectangle towerRange = new Rectangle(x,y,rangeWidth,rangeHeight); //Creates a rectangle for the towers range 
	Rectangle enemyBox;
	private PlayerInput input = new PlayerInput();
	Animation animation = new Animation(Assets.wizardTower,6);
	
	public Tower(int x, int y) {
		this.x = x;
		this.y = y;

	}
	
	public boolean search() {
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			enemyBox = new Rectangle(e.getBounds().x, e.getBounds().y, width, height);
			if(e.getBounds().intersects(towerRange)) { //If an entity is detected within tower range get it's x and y and set it to the appropriate variables
				return false; //An entity has been detected so the tower will start shooting	
			}else{
				return false;
			}
		}
		return false;
	}
	private void shoot() {
		double enemyX=0, enemyY=0;
		for(Entity e:entityManager.getEntities()) {
			if (e instanceof Enemy) {
//			enemyX = e.getX();
//			enemyY = e.getY();
			enemyX = e.getBounds().x;
			enemyY = e.getBounds().y;
			}
		}
		System.out.println(attack);
		entityManager.addEntity(new Bullet(x,y,enemyX,enemyY,Assets.bullet[1].getWidth(), Assets.bullet[1].getHeight(),1,8));
	
	
	}			
	
		

	public void updateBounds(){
		this.bounds.x=x;
		this.bounds.y=y;
				
	}

	@Override
	public void update(){ //Every frame check to see if an entity is within towers range, if so start attacking
		if(search()) {
			attack = true;
		}else {
			attack = false;
		}
		if (attack == true && shotDelay == 30) {
			shoot();
			shotDelay = 0;
		}
		
		shotDelay+=1; //Counts up for every frame, towers can only shoot every 30 frames
		
		if (shotDelay>30) { //When 30 frames pass reset shot delay
			shotDelay=0;
		}
		updateBounds();
	}
	@Override
	public void render(Graphics g, Camera camera) {
		for(Entity e:entityManager.getEntities()) {
			if(e instanceof Enemy) {
				g.drawRect(e.getX()-camera.getxOffset(),e.getY()-camera.getyOffset(),Assets.enemy[0].getWidth(),Assets.enemy[0].getHeight());
			}
		}
		g.drawRect(x-camera.getxOffset(),y-camera.getyOffset(),rangeWidth,rangeHeight);
		g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
}
