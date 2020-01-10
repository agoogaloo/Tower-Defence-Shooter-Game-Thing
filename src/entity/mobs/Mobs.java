package entity.mobs;

import entity.Entity;

public abstract class Mobs extends Entity{
	protected int speed=1, changeX, changeY;
	
	public void updateBounds(){
		this.bounds.x=x;
		this.bounds.y=y;
				
	}
}
