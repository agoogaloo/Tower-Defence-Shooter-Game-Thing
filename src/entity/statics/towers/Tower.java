package entity.statics.towers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.Bullet;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.particles.EffectOverTime;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.RectangleShape;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;

// @author Kevin (did all of tower except for a few parts)
// @author Matthew (helped debug the code, made Entity target, and fixed towerRange, made width and height too)

public abstract class Tower extends Statics { //extends from statics as towers don't move
	
	private boolean attack = false; //When this variable is true tower is capable of attacking enemies
	protected int shotDelay = 0, reloadTime, damage; //Shot delay making towers shoot once a second, rather than rapidly shooting 

	protected double buffDamage=1;
	protected boolean jammed=false;
	protected StatusEffect statusEffect= new StatusEffect(StatusType.NONE,0,0);
	protected EffectOverTime effectParticles;
	protected Entity target; //The specific target the tower gets the x and y of
	protected Ellipse2D.Float towerRange; //A rectangle of range where the tower can shoot at
	protected Animation animation;
	
	protected boolean splitUpgrades=false;
	protected BufferedImage buyIcon,upgradeIcon;
	protected int price, sellValue;
	protected String infoText;
	protected TowerSpawn spawn;
	
	protected boolean hovered=false;
	protected Tower() {
		friendly=true;
	}
	public Tower(int x, int y, int rangeWidth, int rangeHeight,Animation anim, int reloadTime) {
		this(x,y,rangeWidth,rangeHeight,anim,reloadTime,null);
	}
	public Tower(int x, int y, int rangeWidth, int rangeHeight,Animation anim, int reloadTime, TowerSpawn spawn) {
		friendly=true;
		collisions=false;
		width=anim.getCurrentFrame().getWidth();
		height=anim.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.spawn=spawn;
		this.x = x-width/2;
		this.y = y-height/2;
		animation=anim;
		this.reloadTime=reloadTime;
		towerRange=new Ellipse2D.Float(x-rangeWidth/2,y-rangeHeight/2, rangeWidth, rangeHeight);
		updateBounds();
		
		
	}
	public void init() {
		if(spawn!=null) {
			spawn.buildable=false;
		}
	}
	public void buildParticles() {
		new InstantEffect(7, new Straight(new RectangleSpawner(x,y+(int)(height*0.75),width/2,height/4),220,80,0.3), 
				new ShrinkOvalParticle(new Timed(60) , 6,0.15), true);
		new InstantEffect(7, new Straight(new RectangleSpawner(x+width/2,y+(int)(height*0.75),width/2,height/4),-40,80,0.3), 
				new ShrinkOvalParticle(new Timed(60) , 6,0.15), true);
		new InstantEffect(6, new Straight(new Point(x+width/2,y+(int)(height*0.75)),0.4), 
				new ShrinkOvalParticle(new Timed(new Color(181,181,181),60), 7,0.15), true);
	}
	
	public abstract int upgrade(char leftRight, int money);
	public abstract String select(char leftRight);//this if used when the tower is hovered over but not actually bought yet
	public void hover() {
		hovered=true;
	}
	public abstract Tower createNew(int x,int y, TowerSpawn spawn);
	
	public void search() {
		attack=false; //Attack is normally false
		for(int i=0;i<entityManager.getEntities().size();i++) { //Check each entity to see if it's intersecting the tower's range
			if(towerRange.intersects(entityManager.getEntities().get(i).getBounds().getX(), entityManager.getEntities().get(i).getBounds().getY(),
					entityManager.getEntities().get(i).getBounds().getWidth(), 
					entityManager.getEntities().get(i).getBounds().getHeight())&&entityManager.getEntities().get(i) instanceof Enemy) { //If an enemy entity is detected within tower range it gets its x and y values and sets it to the appropriate variables
				target=entityManager.getEntities().get(i); //sets the target to the specific enemy entity that intersected the tower's range
				attack= true; //An entity has been detected so the tower will start shooting	
			}
		}
	}
	
	protected void shoot() {
		entityManager.addEntity(new Bullet(x+width/2,y+height/2,target.getX()+target.getWidth()/2,
				target.getY()+target.getHeight()/2,Assets.yellowBullet,8,30,damage*buffDamage
				,statusEffect, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	}			

	@Override
	public void update(){ 
		animation.update(); //Updates animations, allowing it to get the currentFrame, and allowing it to go through the animation array
		updateEffects();
		search(); //Every frame check to see if an entity is within towers range, if so start attacking
		if (attack && shotDelay<=0) { //If attack is true and it's been 60 frames since last shot, shoot again
			shoot(); //Calls shoot method
			shotDelay = reloadTime; //Ensures the tower can't rapidly shoot
		}
		if(spawn!=null&& spawn.isKilled()) {
			Entity.getEntityManager().getPlayer().giveMoney(sellValue);
			destroy();
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
		
	}
	
	
	@Override
	public void render(Graphics g, Camera camera) { //Renders the tower
		if(hovered) {
			g.drawImage(ImageUtils.outline(animation.getCurrentFrame(),Color.WHITE), x-camera.getxOffset()-1, y-camera.getyOffset()-1, null);
		}else {
			g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		}
		hovered=false;
		
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
		buildParticles();
		killed=true;
		if(spawn!=null) {
			spawn.buildable=true;
		}
	}	
	//getters/setters
	public BufferedImage getUpgradeIcon(char leftRight) {
		if(!splitUpgrades) {
			return upgradeIcon;
		}
		if(leftRight=='l') {
			return upgradeIcon.getSubimage(0, 0, 25, 25);
		}
		if(leftRight=='r') {
			return upgradeIcon.getSubimage(25, 0, 25, 25);
		}
		return upgradeIcon;
	}
	protected void updateEffects() {
		
		if(effectParticles!=null) {
			effectParticles.update();
			if(effectParticles.isFinished()) {
				effectParticles=null;
			}
		}
		
		jammed=false;
		buffDamage=1;		
		for(int i=currentEffects.size()-1;i>=0;i--) {
			currentEffects.get(i).update();
			if(!currentEffects.get(i).isActive()) {
				currentEffects.remove(i); 
				continue;
			}
			switch (currentEffects.get(i).getType()) {
			case JAMMED:
				jammed=true;
				break;
			case BUFFDMG:
				buffDamage=currentEffects.get(i).getLevel();
				if(effectParticles==null) {
					
					effectParticles= new EffectOverTime(bounds.width*bounds.height/150f,20, new Straight(new RectangleSpawner(x,y,bounds.width,bounds.height)
						,-90,0,0.4),new RectangleShape(1, 3, new Timed(new Color(33,166,144), 20)),false);
				}
			default:
				break;
			}
			
			
		}
	}
	public Animation getAnimation() {
		return animation;
	}
	public boolean isSplitUpgrades() {
		return splitUpgrades;
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
