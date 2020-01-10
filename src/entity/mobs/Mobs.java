package entity.mobs;

import Main.Main;
import entity.Entity;

public abstract class Mobs extends Entity{
	protected int speed=1, changeX, changeY;
	
	public void update(){
		this.bounds.x=x;
		this.bounds.y=y;
				
	}
}
