package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import graphics.Camera;

/**
 * @author Kevin Tea and Matthew Milum
 *
 */
public abstract class Entity {
	protected static EntityManager entityManager=new EntityManager();
	
	protected int x, y;
	protected int health;
	protected int width, height;
	protected boolean killed = false;
	
	protected Rectangle bounds = new Rectangle(0,0, width,height); //Gives enemies a hitbox of their width and height
	

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
	public boolean getKilled(){
		return this.killed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
	public abstract void update();
	public abstract void render(Graphics g, Camera camera);
}
