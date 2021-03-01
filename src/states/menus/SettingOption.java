package states.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Properties;

import graphics.Assets;
import saveData.Settings;

public class SettingOption extends MenuObject{
	private String name;
	private String[] options;
	private int currentValue;
	private int valueOffset;
	private boolean hovered=false;
	
	public SettingOption(Rectangle bounds,int valueOffset, String name, String[] options ) {
		super(bounds);
		this.name = name;
		this.valueOffset = valueOffset;
		this.options=options;
		
		currentValue=0;
		for(int i=0;i<options.length;i++) {
			if(options[i].equals(Settings.getProperties().getProperty(name))) {
				currentValue=i;
			}
		}
	}

	@Override
	public void renderObject(Graphics g) {
		//BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		//Graphics g = img.getGraphics();
		g.setFont(Assets.bigpixelfont);//setting the font
		
		if(selected||hovered) {
			g.setColor(Color.BLACK);//setting the colour for the menu text
			if(selected) {
				g.drawString(options[currentValue].toUpperCase(), bounds.x+valueOffset-1, bounds.y+bounds.height);
				g.drawString(options[currentValue].toUpperCase(), bounds.x+valueOffset, bounds.y+bounds.height);
			}
			g.drawString(name.toUpperCase(), bounds.x+1, bounds.y+bounds.height);
			g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height);
		}
		
		g.setColor(Color.WHITE);//setting the colour for the menu text
		g.drawString(name.toUpperCase(), bounds.x, bounds.y+bounds.height-1);
		g.drawString(options[currentValue].toUpperCase(), bounds.x+valueOffset, bounds.y+bounds.height-1);
		hovered=false;
		
		
	}
	
	@Override
	public void hover() {
		hovered=true;
	}
	
	@Override
	public void leftPress() {
		currentValue--;
		if(currentValue<0) {
			currentValue=options.length-1;
		}
		applychange();
	}
	@Override
	public void rightPress() {
		currentValue++;
		if(currentValue>options.length-1) {
			currentValue=0;
		}
		applychange();
	}
	
	private void applychange() {
		Properties settings = Settings.getProperties();
		settings.setProperty(name, options[currentValue]);
		Settings.writeproperties(settings);
	}


	
}
