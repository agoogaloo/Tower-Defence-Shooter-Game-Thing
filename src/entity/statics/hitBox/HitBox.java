package entity.statics.hitBox;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.Entity;
import entity.statics.Statics;
import graphics.Camera;

public abstract class HitBox extends Statics{
	protected ArrayList<Entity> collisions= new ArrayList<Entity>();
	protected ArrayList<Entity> finishedCollisions= new ArrayList<Entity>();
	
		
	
	@Override
	public void render(Graphics g, Camera camera) {}
	
	
	public ArrayList<Entity> getCollisions() {
		return collisions;
	}
	public void destroy() {
		killed=true;
	
	}

}
