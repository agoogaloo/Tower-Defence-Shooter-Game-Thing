package graphics;

import entity.Entity;

//@author Matthew
public class Camera {
	/*
	 * this is the camera which works by giving everything an offset, which then 
	 * causes everything to be drawn in a different place making it look like the camera is moving
	 */
	private int xOffset, yOffset, screenWidth, screenHeight;
	//constructors
	public Camera(int screenWidth, int screenHeight) {
		//screen height and width are required to center an entity on the screen
		this.xOffset = 0;
		this.yOffset = 0;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	//lets you start the camera with in a specific location
	public Camera(int screenWidth, int screenHeight, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void move(int xMove, int yMove) {
		//lets you manually move the camera however much you want
		xOffset += xMove;
		yOffset += yMove;
	}
	public void centerOnEntity(Entity e) {
		//positioning the camera so that the inputed entity is in the middle
		xOffset = e.getX() - screenWidth / 2 + e.getWidth() / 2;
		yOffset = e.getY() - screenHeight / 2 + e.getHeight() / 2;
	}
	
	//getters so things can know how offset they should be
	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
}
