package entity.statics.hitBox;

import entity.Entity;

public class RectangleBox extends HitBox{
	
	public RectangleBox(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.health=height;
		updateBounds();
	}
	
	@Override
	public void update() {}
	
	@Override
	public void damage() {
		collisions.clear();
		for(Entity i:entityCollide()) {
			if(!finishedCollisions.contains(i)) {
				collisions.add(i);
				finishedCollisions.add(i);
			}
		}
		
	}

}
