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
	int shotDelay = 0;
	int rangeWidth = 80, rangeHeight = 80;
	Entity target;
	Rectangle towerRange;
	Animation animation = new Animation(Assets.wizardTower,6);
	
	public Tower(int x, int y) {
		this.x = x;
		this.y = y;
		towerRange=new Rectangle(x-40,y-40,rangeWidth,rangeHeight); //Creates a rectangle for the towers range 

	}
	
	public void search() {
		attack=false;
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			if(e.getBounds().intersects(towerRange)&&e instanceof Enemy) { //If an entity is detected within tower range get it's x and y and set it to the appropriate variables
				System.out.println(e);
				target=e;
				attack= true; //An entity has been detected so the tower will start shooting	
			}
		}
	}
	private void shoot() {
		entityManager.addEntity(new Bullet(x,y,target.getX(),target.getY(),Assets.bullet[1].getWidth()
				, Assets.bullet[1].getHeight(),1,8));
	}			

	@Override
	public void update(){ //Every frame check to see if an entity is within towers range, if so start attacking
		search();
		if (attack && shotDelay>= 30) {
			shoot();
			shotDelay = 0;
		}
		System.out.println(attack);
		shotDelay+=1; //Counts up for every frame, towers can only shoot every 30 frames
		
		
	}
	@Override
	public void render(Graphics g, Camera camera) {
		for(Entity e:entityManager.getEntities()) {
			if(e instanceof Enemy) {
				g.drawRect(e.getX()-camera.getxOffset(),e.getY()-camera.getyOffset(),Assets.enemy[0].getWidth(),Assets.enemy[0].getHeight());
			}
		}
		g.drawRect(towerRange.x-camera.getxOffset(),towerRange.y-camera.getyOffset(),
				towerRange.width,towerRange.height);
		g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
}
