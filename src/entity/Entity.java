package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Kevin Tea
 *
 */
public abstract class Entity {
	int x, y;
	int health;
	int width, height;
	double changeX, changeY;
	boolean killed = false;
	
	Rectangle bounds = new Rectangle(0,0, width,height); //Gives enemies a hitbox of their width and height
	
	public int getEntityX(){
		return this.x;
	}
	public int getEntityY(){
		return this.y;
	}
	public boolean getKilled(){
		return this.killed;
	}
	
	abstract void update();
	abstract void render(Graphics g);
}
