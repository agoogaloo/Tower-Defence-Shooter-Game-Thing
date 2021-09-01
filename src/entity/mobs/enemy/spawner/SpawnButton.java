package entity.mobs.enemy.spawner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.UI.PicElement;
import states.State;

public class SpawnButton {
	private int targetX, targetY;//where the button is pointing towards
	private Rectangle bounds=new Rectangle(5,180,16,15);
	private boolean clicked=false, hovered=false,exists=true;
	private PicElement button = new PicElement(targetX, targetY, Assets.waveIcon[0]);
	private PicElement arrow = new PicElement(targetX, targetY, Assets.waveIcon[0]);
	
	
	public void update(int x,int y) {
		clicked=false;
		hovered=false;
		//exiting the function if the button isnt being shown
		if(!exists) {
			return;
		}
		
		move(x,y);
		
		
				
		//checking if the button is being clicked or hovered on
		if(bounds.contains(State.getInputs().getMouseX(),State.getInputs().getMouseY())){
			hovered=true;
			if(State.getInputs().isShoot()) {
				clicked=true;
			}
		}
	}
	
	public void render(Graphics g, Camera camera) {
		boolean up=false,down=false,left=false,right=false;
		if(!exists) {//exiting if the button isnt being shown
			return;
		}
		
		bounds.x=targetX-camera.getxOffset();
		bounds.y=targetY-camera.getyOffset();
		
		if(bounds.x<15) {
			bounds.x=15;
			left=true;
		}else if(bounds.x>303) {
			bounds.x=303;
			right=true;
		}
		
		if(bounds.y<20) {
			bounds.y=25;
			up=true;
		}else if(bounds.y>170){
			bounds.y=170;
			down=true;
		}
		
		
		if(up) {
			if(left) {
				arrow.update(Assets.waveDirections[7]);
			}else if(right) {
				arrow.update(Assets.waveDirections[1]);
			}else
				arrow.update(Assets.waveDirections[0]);	
		}else if(down) {
			if(left) {
				arrow.update(Assets.waveDirections[5]);	
			}else if(right) {
				arrow.update(Assets.waveDirections[3]);	
			}else
				arrow.update(Assets.waveDirections[4]);	
		}else if(right) {
			arrow.update(Assets.waveDirections[2]);	
		}else if(left) {
			arrow.update(Assets.waveDirections[6]);	
		}else {
			arrow.visible=false;
		}
		arrow.move(bounds.x-6, bounds.y-9);
		
		
		if(clicked) {
			button.update(Assets.waveIcon[2]);	
		}else if(hovered) {
			button.update(ImageUtils.outline(Assets.waveIcon[2],Color.white));	
		}else {
			button.update(Assets.waveIcon[0]);	
		}
		button.move(bounds.x, bounds.y);
		
	}
	
	
	/**
	 * this gets rid of the button from the screen
	 */
	public void remove() {
		exists=false;
		arrow.visible=false;
		button.visible=false;
	}
	
	public void create() {
		exists=true;
		arrow.visible=true;
		button.visible=true;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void move(int x, int y) {
		targetX=x;
		targetY=y;
	}
	
}
