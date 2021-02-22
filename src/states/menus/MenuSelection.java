package states.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;


public class MenuSelection extends MenuObject{
	private String name;
	private int valueOffset;
	
	public MenuSelection(Rectangle bounds, String name, int valueLoc) {
		super(bounds);
		this.name = name;
		this.valueOffset = valueLoc;
	}

	@Override
	public void render(Graphics g) {
		
		//BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		g.setColor(Color.WHITE);//setting the colour for the menu text
		g.setFont(Assets.bigpixelfont);//setting the font
		
		g.drawString(name, 0, 0);		
		//return img;
	}


	
}
