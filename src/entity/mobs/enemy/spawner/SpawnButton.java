package entity.mobs.enemy.spawner;

import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import states.State;

public class SpawnButton {
	private Rectangle bounds=new Rectangle(5,180,16,15);
	private boolean clicked=false, hovered=false,exists=true;
	
	public void update() {
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
	
	public void render(Graphics g) {
		if(!exists) {
			return;
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
