package states.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;


public class MenuSelection extends MenuObject{
	private String name;
	private boolean isHovered=false;
	
	public MenuSelection(Rectangle bounds, String name) {
		super(bounds);
		this.name = name;
	}

	@Override
	public void renderObject(Graphics g) {
		g.setFont(Assets.bigpixelfont);//setting the font
		if(selected||isHovered) {
			g.setColor(Color.BLACK);//setting the colour for the menu text
			g.drawString(name.toUpperCase(), bounds.x+1, bounds.y+bounds.height);
			g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height);
		}
		
		g.setColor(Color.WHITE);//setting the colour for the menu text
		g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height-1);
		isHovered=false;
	}
	@Override
	public void hover() {
		super.hover();
		isHovered=true;
	}


	
}
