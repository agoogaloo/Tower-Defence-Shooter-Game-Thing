package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import graphics.Camera;

//@author Matthew (basically did all the logic and everything in this class)
//@author Kevin (did the framework, psuedocode to real code)

//you need to specify what kind of entity you want to make so it should be abstract
public abstract class Entity {
	/*
	 * everything that is not the level itself such as enemies, bullets, and the
	 * player are a type of entity and extend this class
	 */
	//an entityManager that all the entities can use to check things, update, and render
	protected static EntityManager entityManager = new EntityManager();
	// declaring instance variables
	protected int x, y;
	protected int xOffset, yOffset;
	protected int width, height;
	
	protected double health = 10;
	protected double damage;
	protected boolean killed=false,solid=false,collisions=true, friendly;
    protected Rectangle bounds = new Rectangle(x,y, 10,10); //Gives enemies a hitbox of their width and height
   
	protected RenderLayer layer = RenderLayer.MID;
	
	protected StatusEffect statusDealt = new StatusEffect(StatusType.NONE, 0, 0);
	protected ArrayList<StatusEffect> currentEffects = new ArrayList<StatusEffect>();
	
	public void init() {}

	protected ArrayList<Entity> entityCollide() {
		// this is used to see what is colliding with what
		ArrayList<Entity> entities = new ArrayList<Entity>();// this holds all the entities that are touching
		
		for (int i=entityManager.getEntities().size()-1;i>=0;i--) {// looping through all the entities
			if (entityManager.getEntities().get(i) != this &&entityManager.getEntities().get(i).getBounds().intersects(this.bounds)) {// checking if it is touching this
				entities.add(entityManager.getEntities().get(i));// if something is touching then it adds it to the arrayList
			}
			
		}
		return entities;// returning everything that is touching this entity
	}

	public void damage() {
		//everything calls this so they can be hurt whenever something that is against them touches them
		double damage=0;
		for (Entity e : entityCollide()) {//checking what is colliding with itself
			//checking which side the thing that touched it is on 
			//(making sure enemies only attack the player, player cant attack the core, etc.)
			if (this.collisions&& e.isFriendly() != friendly && e.hasCollisions()) {
				damage+= e.getDamage();//dealing however much damage that entity does	
				giveStatusEffect(e.statusDealt);
			}
		}
		damage(damage);
	}
	public void damage(double d) {
		health-=d;
		if (health <= 0) {//if it has no more health left that it should be dead
			killed = true;
		}
	}
	protected void updateBounds(){
		this.bounds.x=x;//setting the bounds to match the entities state this needs to 
		this.bounds.y=y;//be called every frame for mobs so other things know where they actually are 
		this.bounds.width=width;//now but it can be called by statics once when it is initialized because 
		this.bounds.height=height;//it will never change
	}
	
	
	// abstract methods
	public abstract void update();//used to change the state of all the enemies and is called every frame

	public abstract void render(Graphics g, Camera camera);//drawing the entity to the screen
	
	public void drawHitBox(Graphics g, Camera camera) {
		g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}

	public abstract void move();//move and check for walls
	
	// getters/setters
	public void giveStatusEffect(StatusEffect effect) {
		if(effect.getType()==StatusType.NONE) {
			return;
		}
		for(int i=currentEffects.size()-1;i>=0;i--) {
			if(currentEffects.get(i).getType()==effect.getType()) {
				//System.out.println("removed "+currentEffects.get(i).getType());
				currentEffects.remove(i);
				
			}
		}
		currentEffects.add(effect);
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getDamage() {
		return damage;
	}
	public double getHealth() {
		return health;
	}

	public void destroy() {
		killed=true;
	}
	public boolean isKilled() {
		return this.killed;
	}
	public boolean isSolid() {
		return solid;
	}
	public boolean hasCollisions() {
		return collisions;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isFriendly() {
		return friendly;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	public static char getOppositeDir(char dir) {
		switch (dir) {
		case 'u':
			return 'd';
		case 'r':
			return 'l';
		case 'd':
			return 'u';
		case 'l':
			return 'r';
		}
		System.out.println("couldnt find the opposite direction for'"+dir+"'.");
		return ' ';
	}
	
	public RenderLayer getLayer() {
		return layer;
	}
	
}
