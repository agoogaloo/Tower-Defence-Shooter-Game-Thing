package entity.statics;

import java.awt.Graphics;

import graphics.Camera;

public class HitBox extends Statics{
	boolean colliding=false;
	public HitBox(int x, int y,int width, int height) {
		bounds.x=x;
		bounds.y=y;
		bounds.width=width;
		bounds.height=height;
	}

	@Override
	public void damage() {
		if(entityCollide().size()>0) colliding=true;
		else colliding=false;
	}
	
	
	public boolean isColliding() {
		return colliding;
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g, Camera camera) {}

}
