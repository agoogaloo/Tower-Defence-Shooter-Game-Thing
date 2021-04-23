package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Plant.Vine;
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
	protected int health = 10, damage;//its health and how much damage it can deal
	protected int width, height;
	protected boolean killed=false, friendly;
    protected Rectangle bounds = new Rectangle(x,y, 10,10); //Gives enemies a hitbox of their width and height
    protected int statusLength=0, statusLevel=0;
	protected StatusEffect statusEffect=StatusEffect.NONE;
	protected RenderLayer layer = RenderLayer.MID;
	
	public static void init(boolean deletePlayer){
		entityManager.reset(deletePlayer);
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
				if(e instanceof Bullet) {
					statusEffect=e.statusEffect;
					statusLength=e.statusLength;
					statusLevel=e.statusLevel;
				}
			}
		}
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
	
	protected BufferedImage damageFlash(BufferedImage startPic) {
		//this method makes the enemy flash white so you can tell they are actually taking damage
		BufferedImage newPic=new BufferedImage(startPic.getWidth(), startPic.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x=0;x<startPic.getWidth();x++) {
			for(int y=0;y<startPic.getHeight();y++) {
				if(new Color(startPic.getRGB(x, y), true).getAlpha()!=0) {
					newPic.setRGB(x, y, Color.WHITE.getRGB());
					
				}
			}
		}
		return newPic;
	}
	// abstract methods
	public abstract void update();//used to change the state of all the enemies and is called every frame

	public abstract void render(Graphics g, Camera camera);//drawing the entity to the screen
	
	public void drawHitBox(Graphics g, Camera camera) {
		g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width, bounds.height);
	}

	public abstract void move();//move and check for walls

	// getters/setters
	public void giveStatusEffect(StatusEffect statusEffect, int statusLevel, int statusLength) {
		this.statusEffect = statusEffect;
		this.statusLevel=statusLevel;
		this.statusLength=statusLength;
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
