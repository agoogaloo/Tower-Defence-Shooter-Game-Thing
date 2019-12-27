package entity;

import javafx.scene.shape.Circle; 
/**
 * @author Kevin Tea
 */
public abstract class Tower extends Statics {
	
	boolean shooting = false;
	int shootDelay = 0;
	int rangeDistance = 30;
	//Circle towerRange = new Circle(width/2,height/2,rangeDistance); //Creates a circle for the towers range (Not Working)
	
	private void shoot() {
		int enemyX, enemyY;
		for(Entity e:EntityManager.getEntities()) {
			
		}
	}
}
