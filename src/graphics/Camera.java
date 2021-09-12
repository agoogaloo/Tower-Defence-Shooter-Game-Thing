package graphics;

import entity.Entity;
import saveData.Settings;

//@author Matthew
public class Camera {
	/*
	 * this is the camera which works by giving everything an offset, which then 
	 * causes everything to be drawn in a different place making it look like the camera is moving
	 */
	private int xOffset, yOffset, entityX, entityY,mouseX,mouseY,locX,locY, screenWidth, screenHeight;
	private float mouseWeight=Settings.getAimLook()/100f, entityWeight=1, locWeight=1;
	private float speed=Settings.getCamSpeed()/100f;
	
	//constructors
	public Camera(int screenWidth, int screenHeight) {
		//screen height and width are required to center things on the screen
		xOffset = 0;
		yOffset = 0;
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
	
	public void update() {
		int targetX= Math.round((entityX*entityWeight+locX*locWeight)/(entityWeight*locWeight));
		int targetY= Math.round((entityY*entityWeight+locY*locWeight)/(entityWeight*locWeight));
		targetX+=mouseX*mouseWeight;
		targetY+=mouseY*mouseWeight;
		xOffset+= speed*(targetX-xOffset);
		yOffset+= speed*(targetY-yOffset);
		
	}

	public void snapToLoc(int x, int y) {
		//lets you manually move the camera however much you want
		xOffset=x- screenWidth / 2; 
		yOffset=y- screenHeight / 2;
		
	}
	public void moveToEntity(Entity e) {
		//positioning the camera so that the inputed entity is in the middle
		entityX = e.getX() - screenWidth / 2 + e.getWidth() / 2;
		entityY = e.getY() - screenHeight / 2 + e.getHeight() / 2;
	}
	public void moveToMouse(int x,int y) {
		mouseX=x- screenWidth / 2;
		mouseY=y- screenHeight / 2;
	}
	public void moveToPoint(int x, int y) {
		locX=x- screenWidth / 2;
		locY=y- screenHeight / 2;
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
	public void setEntityWeight(int entityWeight) {
		this.entityWeight = entityWeight;
	}
	public void setMouseWeight(float mouseWeight) {
		this.mouseWeight = mouseWeight;
	}
	public void setLocationWeight(int locationWeight) {
		this.locWeight = locationWeight;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
