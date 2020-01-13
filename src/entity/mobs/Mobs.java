package entity.mobs;

import Main.Main;
import entity.Entity;

public abstract class Mobs extends Entity{
	protected int speed=1, changeX, changeY;
	
	@Override
	public void move() {
		//not moving if the players coreners will be inside of a wall
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
		this.bounds.x=x;
		this.bounds.y=y;
		this.bounds.width=width;
		this.bounds.height=height;
				
	}
}
