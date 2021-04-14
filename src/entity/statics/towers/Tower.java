package entity.statics.towers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.Bullet;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

// @author Kevin (did all of tower except for a few parts)
// @author Matthew (helped debug the code, made Entity target, and fixed towerRange, made width and height too)

public abstract class Tower extends Statics { //extends from statics as towers don't move
	
	private boolean attack = false; //When this variable is true tower is capable of attacking enemies
	protected int shotDelay = 0, reloadTime, damage; //Shot delay making towers shoot once a second, rather than rapidly shooting 
	protected StatusEffect statusEffect=StatusEffect.NONE;
	protected int effectLevel=0, effectLength=0;
	
	protected Entity target; //The specific target the tower gets the x and y of
	protected Ellipse2D.Float towerRange; //A rectangle of range where the tower can shoot at
	protected Animation animation;
	
	
	protected BufferedImage buyIcon,upgradeIcon;
	protected int price, sellValue;
	protected String infoText;
	protected Tower() {
		friendly=true;
	}
	public Tower(int x, int y, int rangeWidth, int rangeHeight,Animation anim, int reloadTime) {
		friendly=true;
		width=anim.getCurrentFrame().getWidth();
		height=anim.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-height/2;
		animation=anim;
		this.reloadTime=reloadTime;
		towerRange=new Ellipse2D.Float(x-rangeWidth/2,y-rangeHeight/2, rangeWidth, rangeHeight);
		updateBounds();
	}
	public void init() {
		
	}
	
	public abstract int upgrade(char leftRight, int money);
	public abstract String hover(char leftRight);//this if used when the tower is hovered over but not actually bought yet
	public abstract Tower createNew(int x,int y);
	
	public void search() {
		attack=false; //Attack is normally false
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy) { //If an enemy entity is detected within tower range it gets its x and y values and sets it to the appropriate variables
				target=e; //sets the target to the specific enemy entity that intersected the tower's range
				attack= true; //An entity has been detected so the tower will start shooting	
			}
		}
	}
	
	protected void shoot() {
		entityManager.addEntity(new Bullet(x+width/2,y+height/2,target.getX(),target.getY(),Assets.yellowBullet,8,damage
				,statusEffect,statusLength,statusLevel, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	}			

	@Override
	public void update(){ 
		animation.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		search(); //Every frame check to see if an entity is within towers range, if so start attacking
		if (attack && shotDelay<=0) { //If attack is true and it's been 60 frames since last shot, shoot again
			shoot(); //Calls shoot method
			shotDelay = reloadTime; //Ensures the tower can't rapidly shoot
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
	}
	
	@Override
	public void render(Graphics g, Camera camera) { //Renders the tower
		g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
	
	public void showRange(Graphics g, Camera camera ) {
		//this is used to show the towers range
		g.setColor(new Color(0,0,0,75));//setting the colour to a translucent dark colour
		g.fillOval((int)towerRange.x-camera.getxOffset(),(int) towerRange.y-camera.getyOffset(),
				(int)towerRange.width, (int)towerRange.height);//making everything in its range darker
	}
	@Override
	public void damage() {
		//left blank so that the towers wont take damage
	}
	
	public void destroy() {
		killed=true;
	}	
	//getters/setters
	public BufferedImage getUpgradeIcon() {
		return upgradeIcon;
	}
	public Animation getAnimation() {
		return animation;
	}
	public BufferedImage getBuyIcon() {
		return buyIcon;
	}
	public int getPrice() {
		return price;
	}
	
	public int getSellValue() {
		return sellValue;
	}
	public String getInfoText() {
		return infoText;
	}
	
	
}
