package entity.statics.hitBox;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import entity.Entity;
import graphics.Camera;

public class CircleBox extends HitBox{
	protected Ellipse2D.Float bounds;
	
	public CircleBox(int x, int y, int width, int height, boolean friendly) {
		bounds=new Ellipse2D.Float(x,y,width,height);
		this.friendly=friendly;
	}
	public CircleBox(int x, int y, int width, int height) {
		this(x,y,width,height,true);
	}
	
	@Override
	public void update() {}
	
	@Override
	public void damage() {
		collisions.clear();
		for(int i=0;i<entityManager.getEntities().size();i++) { //Check each entity to see if it's intersecting the tower's range
			Entity e=entityManager.getEntities().get(i);
			if(bounds.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())) { 
				if(!finishedCollisions.contains(e)) {
					collisions.add(e);
					finishedCollisions.add(e);
				}
			}
		}
	}
	@Override
	public void drawHitBox(Graphics g, Camera camera) {
		g.drawOval(Math.round(bounds.x-camera.getxOffset()),Math.round(bounds.y-camera.getyOffset()),
				Math.round(bounds.width), Math.round(bounds.height));
	}
	public void updateBounds(float x, float y) {
		bounds.x=x;
		bounds.y=y;
	}
}
