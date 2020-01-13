package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

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
	protected int health = 10, damage;//its health and how much damage it can deal
	protected int width, height;
	protected int shotDelayAmount;
	protected boolean killed=false, friendly;
  protected Rectangle bounds = new Rectangle(x,y, 10,10); //Gives enemies a hitbox of their width and height
	
	public static void init(){
		entityManager.init();
	}

	protected ArrayList<Entity> entityCollide() {
		// this is used to see what is colliding with what
		ArrayList<Entity> entities = new ArrayList<Entity>();// this holds all the entities that are touching
		for (Entity e : entityManager.getEntities()) {// looping through all the entities
			if (e != this && e.getBounds().intersects(this.bounds)) {// checking if it is touching this
				entities.add(e);// if something is touching then it adds it to the arrayList
			}
		}
		return entities;// returning everything that is touching this entity
	}

	public void damage() {
		//everything calls this so they can be hurt whenever something that is against them touches them
		for (Entity e : entityCollide()) {//checking what is colliding with itself
			//checking which side the thing that touched it is on 
			//(making sure enemies only attack the player, player cant attack the core, etc.)
			if (e.isFriendly() != friendly) {
				health -= e.getDamage();//dealing however much damage that entity does
			}
		}
		if (health <= 0) {//if it has no more health left that it should be dead
			killed = true;
		}
	}

	// abstract methods
	public abstract void update();//used to change the state of all the enemies and is called every frame

	public abstract void render(Graphics g, Camera camera);//drawing the entity to the screen

	public abstract void move();//move and check for walls

	// getters/setters
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

	public int getDamage() {
		return damage;
	}

	public boolean isKilled() {
		return this.killed;
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
}
