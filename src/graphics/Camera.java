package graphics;

import game.Handler;
import game.entities.Entity;

public class Camera {
	private int xOffset, yOffset;
	
	
	public Camera() {
		this.xOffset = 0;
		this.yOffset = 0;
	}

	public Camera(int xOffset, int yOffset) {
		this.xOffset=xOffset;
		this.yOffset=yOffset;
	}
	
	public void move(int xMove, int yMove) {
		xOffset+=xMove;
		yOffset+=yMove;
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - Handler.getScreenWidth()/2 + e.getWidth()/2;
		yOffset= e.getY()-Handler.getScreenHeight()/2 +e.getHeight()/2;								
	}

}
