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
		//horizontal collisions
		xCollide();
				
		//doing vertical wall collisions
		yCollide();
		
		
		
		x=(int)(trueX);
		y=(int)(trueY);
		changeX = 0;// resetting change x and y
		changeY = 0;
		updateBounds();
	}
	private void xCollide() {
		double checkX=trueX+changeX;
		//doing wall collisions
		
		if(changeX<0) {
			
			if (GameState.getFloor().checkWall((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkWall((int)(checkX) / 16, (bounds.height+(int)(trueY)) / 16)
					|| GameState.getFloor().checkPit((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkPit((int)(checkX) / 16, (bounds.height+(int)(trueY)) / 16)) {
				
				trueX =Math.round(checkX/16)*16;//snapping them onto the wall
				return;
			}
			
		}else if(changeX>0){//if they are moving right
			checkX+=bounds.width;
			if (GameState.getFloor().checkWall((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkWall((int)Math.round(checkX) / 16, (bounds.height+(int)Math.round(trueY)) / 16)
					|| GameState.getFloor().checkPit((int)Math.round(checkX)/ 16,(int)Math.round(trueY) / 16)
					|| GameState.getFloor().checkPit((int)Math.round(checkX) / 16, (bounds.height+(int)Math.round(trueY)) / 16)) {
				trueX =Math.round(checkX/16 - 1)*16 -bounds.width+15;
				return;
			}
			checkX-=bounds.width;//moving the if they are going into a free area
		}
		
		bounds.x=(int)checkX;
		for(Entity i:entityManager.getSolids()) {
			if(bounds.intersects(i.getBounds())) {
				bounds.x=x;
				return;
			}
		}
		bounds.x=x;
		trueX=checkX;//moving the if they are going into a free area
	}
	
	private void yCollide() {
		double checkY=trueY+changeY;
		
		
		if(changeY>0) {//moving down collisions
			checkY+=bounds.height;
			if (GameState.getFloor().checkWall((int)Math.round(trueX) / 16, ((int)Math.round(checkY)) / 16)
					|| GameState.getFloor().checkWall((bounds.width+(int)Math.round(trueX)) / 16, ((int)Math.round(checkY)) / 16)
					|| GameState.getFloor().checkPit((int)Math.round(trueX) / 16, ((int)Math.round(checkY)) / 16)
					|| GameState.getFloor().checkPit((bounds.width+(int)Math.round(trueX)) / 16, ((int)Math.round(checkY)) / 16)) {
				
				trueY =Math.round(checkY/16 - 1)*16-bounds.height+15;
				return;
			}
				checkY-=bounds.height;
				
			
		}else if(changeY<0) {//moving up collisions
			if (GameState.getFloor().checkWall((int)Math.round(trueX)/ 16,(int)Math.round(checkY) / 16)
					|| GameState.getFloor().checkWall((bounds.width+(int)Math.round(trueX)) / 16, (int)Math.round(checkY)/ 16)
					|| GameState.getFloor().checkPit((int)Math.round(trueX)/ 16,(int)Math.round(checkY) / 16)
					|| GameState.getFloor().checkPit((bounds.width+(int)Math.round(trueX)) / 16, (int)Math.round(checkY)/ 16)) {
				trueY =Math.round(checkY / 16)*16;
				return;
			}
		}
		
		bounds.y=(int)checkY;
		for(Entity i:entityManager.getSolids()) {
			if(bounds.intersects(i.getBounds())) {
				bounds.y=y;
				return;
			}
		}
		bounds.y=y;
		trueY=checkY;
	}
	
	protected void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
		trueX=x;
		trueY=y;
	}
}
