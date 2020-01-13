package entity.statics;

import java.awt.Graphics;
import java.awt.Rectangle;
import entity.Entity;
import entity.mobs.Enemy;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

// @author Kevin (did all of tower except for a few parts)
// @author Matthew (helped debug the code, made Entity target, and fixed towerRange, made width and height too)

public class Tower extends Statics { //extends from statics as towers don't move
	
	private boolean attack = false; //When this variable is true tower is capable of attacking enemies
	private int shotDelay = 0; //Shot delay making towers shoot once a second, rather than rapidly shooting 
	private int rangeWidth = 80, rangeHeight = 80; //The specific width and height of the the tower's attack range
	private Entity target; //The specific target the tower gets the x and y of
	private Rectangle towerRange; //A rectangle of range where the tower can shoot at
	private Animation animation = new Animation(Assets.wizardTowerFix,6); //Animations
	
	public Tower(int x, int y) { //Tower class, can only be called when player presses the control key, and spawns on the player's location
		this.x = x;
		this.y = y;
		towerRange=new Rectangle(x-28,y-17,rangeWidth,rangeHeight); //Creates a rectangle for the towers range 
	}
	
	public void search() {
		attack=false; //Attack is normally false
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			if(e.getBounds().intersects(towerRange)&&e instanceof Enemy) { //If an enemy entity is detected within tower range it gets its x and y values and sets it to the appropriate variables
				System.out.println(e);
				target=e; //sets the target to the specific enemy enetity that intersected the tower's range
				attack= true; //An entity has been detected so the tower will start shooting	
			}
		}
	}
	private void shoot() {
		entityManager.addEntity(new Bullet(x,y,target.getX(),target.getY(),1,8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	}			

	@Override
	public void update(){ 
		animation.update(); //Updates animations, allowing it to loop through the animation array
		search(); //Every frame check to see if an entity is within towers range, if so start attacking
		if (attack && shotDelay>= 60) { //If attack is true and it's been 60 frames since last shot, shoot again
			shoot(); //Calls shoot method
			shotDelay = 0; //Ensures the tower can't rapdily shoot
		}
		shotDelay+=1; //Counts up for every frame, towers can only shoot every 60 frames
	}
	
	@Override
	public void render(Graphics g, Camera camera) { //Renders the tower, along with it's range
		g.drawRect(towerRange.x-camera.getxOffset(),towerRange.y-camera.getyOffset(),
				towerRange.width,towerRange.height);
		g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
}
