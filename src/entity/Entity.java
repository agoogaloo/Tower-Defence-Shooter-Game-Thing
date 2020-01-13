package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import graphics.Camera;

//@author Matthew (basically did all the logic and everything in this class)
//@author Kevin (did the framework, psuedocode to real code)

public abstract class Entity {
	protected static EntityManager entityManager=new EntityManager();
	
	protected int x, y;
	protected int health=10, damage;
	protected int width, height;
	protected boolean killed=false, friendly;
  protected Rectangle bounds = new Rectangle(x,y, 10,10); //Gives enemies a hitbox of their width and height
	
	public static void init(){
		entityManager.init();
	}

	protected ArrayList<Entity> entityCollide(){
		ArrayList<Entity> entities=new ArrayList<Entity>();
		for (Entity e:entityManager.getEntities()){
			if(e!=this&&e.getBounds().intersects(this.bounds)){
				entities.add(e);
			}
		}
		return entities;
	}
	
	
	public void damage() {
		for(Entity e: entityCollide()) {
			if(e.isFriendly()!=friendly) {
				health-=e.getDamage();
			}		
		}
		if(health<=0){
			killed = true;
		}
	}
	//abstract methods
	public abstract void update();
	public abstract void render(Graphics g, Camera camera);
	public abstract void move();
	
	
	//getters/setters
	public int getX(){
		return this.x;
	}
	public int getY(){
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
	public boolean isKilled(){
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
