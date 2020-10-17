package entity.mobs.enemy.spawner;

import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import graphics.Camera;
import states.GameState;
import states.State;

public class SpawnButton {
	int roomX, roomY;
	private Rectangle bounds=new Rectangle(5,180,16,15);
	private boolean clicked=false, hovered=false,exists=true;
	
	public void update(int Roomx,int RoomY, char direction) {
		this.roomX=Roomx*GameState.getFloor().TILESIZE*GameState.getFloor().ROOMSIZE;
		this.roomY=Roomx*GameState.getFloor().TILESIZE*GameState.getFloor().ROOMSIZE;
		
		switch(direction) {
		case 'u':
			roomX+=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)/2;
			roomY-=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)-40;
			break;
		case 'd':
			roomX+=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)/2;
			
			break;
		case 'l':
			roomY-=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)/2;
			roomX+=20;
			break;
		case 'r':
			roomX+=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)-20;
			roomY-=(GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE)/2;
			break;
		}
		
		
		
		clicked=false;
		hovered=false;
		//System.out.println(exists);
		if(!exists) {
			return;
		}
		if(bounds.contains(State.getInputs().getMouseX(),State.getInputs().getMouseY())){
			hovered=true;
			if(State.getInputs().isShoot()) {
				clicked=true;
			}
		}
		
	}
	
	public void render(Graphics g, Camera camera) {
		boolean up=false,down=false,left=false,right=false;
		if(!exists) {
			return;
		}
		
		bounds.x=roomX-camera.getxOffset();
		bounds.y=roomY-camera.getyOffset();
		
		if(bounds.x<10) {
			bounds.x=10;
			left=true;
		}else if(bounds.x>303) {
			bounds.x=303;
			right=true;
		}
		
		
		if(bounds.y<20) {
			bounds.y=20;
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
			g.drawImage(Assets.waveIcon[1],bounds.x,bounds.y,null );	
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
	
	
	
}
