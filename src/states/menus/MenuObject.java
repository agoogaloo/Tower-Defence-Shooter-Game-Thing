package states.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import states.console.ConsoleState;


public abstract class MenuObject {
	protected Rectangle bounds;
	protected boolean selected=false;
	public MenuObject(int x, int y, int width, int height) {
		this.bounds=new Rectangle(x, y, width, height);
	}
	public MenuObject(Rectangle bounds) {
		this.bounds=bounds;
	}


	public void render(Graphics g) {
		if(ConsoleState.isShowHitBoxen()) {
			g.setColor(Color.pink);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		renderObject(g);
	}
	protected abstract void renderObject(Graphics g);
	
	
	public void hover() {}
	
	public void select() {
		selected=true;
		
	}
	
	public void deselect() {
		selected=false;
		
	}
	public void leftPress() {}
	public void rightPress() {}
	
	public Rectangle getBounds() {
		return bounds;
	}

}
