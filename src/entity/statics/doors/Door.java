package entity.statics.doors;

import java.awt.Graphics;

import entity.RenderLayer;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Camera;
import states.GameState;

public abstract class Door extends Statics{

	protected char direction;
	protected Animation anim;
	protected int spawnX, spawnY;
	

	public Door(int x, int y, char direction) {
		solid=true;
		spawnX=x;
		spawnY=y;
		layer=RenderLayer.BACK;
		this.direction=direction;
	}
	
	@Override
	public void update() {
		if(!solid&&anim.getFrameIndex()!=anim.getlength()-1) {
			anim.update();
			
		}	
	}	

	@Override
	public void render(Graphics g, Camera camera) {		
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset()+anim.getxOffset(), y-camera.getyOffset()+anim.getyOffset(), null);
	
	}
	
	
	
	@Override
	public void damage(double amount) {}
	
	public static Door getProperDoor(int x, int y, int levelID, char direction) {
		switch(levelID) {
		case GameState.TUTORIALINDEX:
			return new TutDoor(x, y, direction);
		
		case GameState.FLOOR1: 
			return new Floor1Door(x,y, direction);
			
		case GameState.FLOOR2: 
			return new Floor2Door(x,y, direction);
			
		default: 
			return new Floor1Door(x,y, direction);
		}	
	}
	
	public void unlock() {
		solid=false;
		collisions=false;
	}
	public void reLock() {
		entityManager.addSolid(this);
		solid=true;
		collisions=true;
	}
	public char getDirection() {
		return direction;
	}
	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}
	
}
