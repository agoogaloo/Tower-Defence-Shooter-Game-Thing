package entity.mobs;

import entity.Entity;
import states.GameState;

//@author Matthew (updateBounds)
//@author Sahib (move() also known as wall collision)
public abstract class Mobs extends Entity{
	/*
	 * this is all the entities that move(so most of them)unlike static entities
	 */
	protected double speed=1, changeX, changeY, trueX, trueY;
	
	
	@Override
	public void move() {
		//not moving if the entities corners will be inside of a wall
		double checkX=trueX+changeX;
		double checkY=trueY+changeY;
		
		//checking if they would move into a wall horizontally
		//checking left side if they're moving left
		if(changeX<0) {
			if (!(GameState.getFloor().checkwall((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkwall((int)(checkX) / 16, (bounds.height+(int)(trueY)) / 16)))
				trueX=checkX;//moving the if they are going into a free area
			else {
				trueX =Math.round(checkX/16)*16;//snapping them onto the wall
			}
		}else if(changeX>0){//if they are moving right
			checkX+=bounds.width;
			if (!(GameState.getFloor().checkwall((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkwall((int)Math.round(checkX) / 16, (bounds.height+(int)Math.round(trueY)) / 16))) {
				trueX=checkX-bounds.width;//moving the if they are going into a free area
			}else {
				trueX =Math.round(checkX/16 - 1)*16 - bounds.width+15;
			}
		}
		
		
		//doing vertical wall collisions
		if(changeY>0) {//moving down collisions
			checkY+=bounds.height;
			if (!(GameState.getFloor().checkwall((int)Math.round(trueX) / 16, ((int)Math.round(checkY)) / 16)
					|| GameState.getFloor().checkwall((bounds.width+(int)Math.round(trueX)) / 16, ((int)Math.round(checkY)) / 16))) {
				trueY=checkY-bounds.height;
				
			}else {
				trueY =Math.round(checkY/16 - 1)*16-bounds.height+15;
			}	
			
		}else if(changeY<0) {//moving up collisions
			if (!(GameState.getFloor().checkwall((int)Math.round(trueX)/ 16,(int)Math.round(checkY) / 16)
					|| GameState.getFloor().checkwall((bounds.width+(int)Math.round(trueX)) / 16, (int)Math.round(checkY)/ 16))) {
				trueY=checkY;
				
			}else {
				trueY =Math.round(checkY / 16)*16;
			}
		}
		
		
		x=(int)(trueX);
		y=(int)(trueY);
		changeX = 0;// resetting change x and y
		changeY = 0;
		updateBounds();
	}
	protected void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
		trueX=x;
		trueY=y;
	}
}
