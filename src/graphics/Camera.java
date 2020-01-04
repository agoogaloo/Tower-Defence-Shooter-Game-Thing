package graphics;

import entity.Entity;

public class Camera {
	private int xOffset, yOffset, screenWidth, screenHeight;
	
	
	public Camera(int screenWidth, int screenHeight) {
		this.xOffset = 0;
		this.yOffset = 0;
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		
	}

	public Camera(int screenWidth, int screenHeight, int xOffset, int yOffset) {
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
	}
	
	public void move(int xMove, int yMove) {
		xOffset+=xMove;
		yOffset+=yMove;
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - screenWidth/2 + e.getWidth()/2;
		yOffset= e.getY()- screenHeight/2 +e.getHeight()/2;								
	}
	
	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}


}
