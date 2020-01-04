package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Kevin Tea
 *
 */
public abstract class Entity {
	protected int x, y;
	protected int health;
	protected int width, height;
	
	protected double changeX, changeY;
	protected boolean killed = false;
	
	protected static EntityManager entityManager=new EntityManager();
	
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
	public abstract void render(Graphics g);
}
