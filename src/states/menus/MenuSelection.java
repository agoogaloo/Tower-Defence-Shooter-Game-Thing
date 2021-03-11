package states.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;


public class MenuSelection extends MenuObject{
	private String name;
	private boolean isHovered=false;
	private Color colour, hoverColour;
	
	public MenuSelection(Rectangle bounds, String name) {
		this(bounds,name,Color.white,Color.black);
	}
	public MenuSelection(Rectangle bounds, String name, Color colour,Color hoverColour) {
		super(bounds);
		this.name = name;
		this.colour=colour;
		this.hoverColour=hoverColour;
	}

	@Override
	public void renderObject(Graphics g) {
		g.setFont(Assets.bigpixelfont);//setting the font
		if(selected||isHovered) {
			g.setColor(hoverColour);//setting the colour for the menu text
			g.drawString(name.toUpperCase(), bounds.x+1, bounds.y+bounds.height);
			g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height);
		}
		
		g.setColor(colour);//setting the colour for the menu text
		g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height-1);
		isHovered=false;
	}
	@Override
	public void hover() {
		super.hover();
		isHovered=true;
	}


	
}
