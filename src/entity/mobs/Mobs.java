package entity.mobs;

import Main.Main;
import entity.Entity;

public abstract class Mobs extends Entity{
	protected int speed=1, velocityX, changeY;
	
	@Override
	public void move() {
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+changeX)/16,(y+changeY)/16)){
			changeX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+width+changeX)/16,(y+changeY)/16)){
			changeX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+changeX)/16,(y+height+changeY)/16)){
			changeX=0;
			changeY=0;
		}
		if(Main.getWindow().getDisplay().getFloor().checkwall((x+width+changeX)/16,(y+height+changeY)/16)){
			changeX=0;
			changeY=0;
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
