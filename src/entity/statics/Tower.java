package entity.statics;

import java.awt.Rectangle;

import entity.Entity;
import entity.mobs.Bullet;

/**
 * @author Kevin Tea
 */
public abstract class Tower extends Statics {
	
	boolean attack = false;
	int shotDelay = 0;
	int rangeWidth, rangeHeight = 30;
	Rectangle towerRange = new Rectangle(x,y,rangeWidth,rangeHeight); //Creates a rectangle for the towers range 
	
	private void shoot() {
		double enemyX=0, enemyY=0;
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			if(e.getBounds().intersects(towerRange)) { //If an entity is detected within tower range get it's x and y and set it to the appropriate variables
				enemyX = e.getX();
				enemyY = e.getY();
				attack = true; //An entity has been detected so the tower will start shooting
			}
		}
		if (attack = true && shotDelay == 30) {
			entityManager.addEntity(new Bullet(x,y,enemyX,enemyY,0,8, true));
			shotDelay = 0;
		}
		if (shotDelay>30) { //When 30 frames pass reset shot delay
			shotDelay=0;
		}
	}
	
	@Override
	public void update(){ //Every frame check to see if an entity is within towers range, if so start attacking
		shoot();
		shotDelay+=1; //Counts up for every frame, towers can only shoot every 30 frames
	}
//	@Override
//	public void render() {
//		
//	}
}
