package entity.mobs.enemy.spawner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;
import states.GameState;
import states.State;

public class SpawnButton {
	private int targetX, targetY;//where the button is pointing towards
	private Rectangle bounds=new Rectangle(5,180,16,15);
	private boolean clicked=false, hovered=false,exists=true;
	
	public void update(int roomX,int roomY, char direction) {
		clicked=false;
		hovered=false;
		//exiting the function if the button isnt being shown
		if(!exists) {
			return;
		}
		
		//getting the location of the room in pixels
		this.targetX=roomX*GameState.getFloor().TILESIZE*GameState.getFloor().getRoomSize();
		this.targetY=roomY*GameState.getFloor().TILESIZE*GameState.getFloor().getRoomSize();
		
		//moving the button location depending on the exit of the room so it will go to the door of the room
		switch(direction) {
		case 'u':
			this.targetX+=(GameState.getFloor().getRoomSize()*GameState.getFloor().TILESIZE)/2;
			this.targetY+=50;
			break;
			
		case 'd':
			this.targetX+=(GameState.getFloor().getRoomSize()*GameState.getFloor().TILESIZE)/2;
			break;
			
		case 'l':
			this.targetY+=(GameState.getFloor().getRoomSize()*GameState.getFloor().TILESIZE)/2;
			this.targetX+=30;
			break;
			
		case 'r':
			this.targetX+=(GameState.getFloor().getRoomSize()*GameState.getFloor().TILESIZE)-30;
			this.targetY+=(GameState.getFloor().getRoomSize()*GameState.getFloor().TILESIZE)/2;
			break;
		}
		
		
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
				g.drawImage(Assets.waveDirections[7],bounds.x-6,bounds.y-9,null );	
			}else if(right) {
				g.drawImage(Assets.waveDirections[1],bounds.x-6,bounds.y-9,null );	
			}else
				g.drawImage(Assets.waveDirections[0],bounds.x-6,bounds.y-9,null );	
		}else if(down) {
			if(left) {
				g.drawImage(Assets.waveDirections[5],bounds.x-6,bounds.y-9,null );	
			}else if(right) {
				g.drawImage(Assets.waveDirections[3],bounds.x-6,bounds.y-9,null );	
			}else
				g.drawImage(Assets.waveDirections[4],bounds.x-6,bounds.y-9,null );	
		}else if(right) {
			g.drawImage(Assets.waveDirections[2],bounds.x-6,bounds.y-9,null );	
		}else if(left) {
			g.drawImage(Assets.waveDirections[6],bounds.x-6,bounds.y-9,null );	
		}
		
		
		if(clicked) {
			g.drawImage(Assets.waveIcon[2],bounds.x,bounds.y,null );	
		}else if(hovered) {
			g.drawImage(ImageUtils.outline(Assets.waveIcon[2],Color.white),bounds.x-1,bounds.y-1,null );	
		}else {
			g.drawImage(Assets.waveIcon[0],bounds.x,bounds.y,null );	
		}
	}
	/**
	 * this gets rid of the button from the screen
	 */
	public void remove() {
		exists=false;
	}
	
	public void create() {
		exists=true;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void move(int x, int y) {
		targetX=x;
		targetY=y;
	}
	
}
