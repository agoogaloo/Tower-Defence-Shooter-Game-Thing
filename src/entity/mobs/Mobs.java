package entity.mobs;

import Main.Main;
import entity.Entity;

//@author Matthew (updateBounds)
//@author Sahib (move() also known as wall collision)
public abstract class Mobs extends Entity{
	/*
	 * this is all the entities that move(so most of them)unlike static entities
	 */
	protected int speed=1, changeX, changeY;
	
	@Override
	public void move() {
		//not moving if the players corners will be inside of a wall
		if (Main.getWindow().getDisplay().getFloor().checkwall((x + changeX) / 16, (y + changeY) / 16)
				|| Main.getWindow().getDisplay().getFloor().checkwall((x + 16 + changeX) / 16, (y + changeY) / 16)
				|| Main.getWindow().getDisplay().getFloor().checkwall((x + changeX) / 16, (y + 29 + changeY) / 16)
				|| Main.getWindow().getDisplay().getFloor().checkwall((x + 16 + changeX) / 16, (y + 29 + changeY) / 16)) {
			changeX = 0;
			changeY = 0;
		}
		x += changeX;// actually moving the player
		y += changeY;
		changeX = 0;// resting change x and y
		changeY = 0;
		updateBounds();
	}
	protected void updateBounds(){
		this.bounds.x=x;//setting the bounds to match the entities state this needs to 
		this.bounds.y=y;//be called every frame for mobs so other things know where they actually are 
		this.bounds.width=width;//now but it can be called by statics once when it is initialized because 
		this.bounds.height=height;//it will never change
	}
}
