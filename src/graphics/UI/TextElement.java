package graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import graphics.Assets;

public class TextElement extends UIElement{
	/*
	 * this class is for parts of ui that will have text such as how much money you have
	 */
	private String text;
	private Font font=Assets.MozartNbp;
	//this constructor takes the location and value of the text and uses the default font
	public TextElement(int x, int y, String text) {
		super(x, y);
		this.text=text;
	}
	//this constructor lets you specify which font you want to use
	public TextElement(int x, int y, String text, Font font) {
		super(x, y);
		this.text=text;
		this.font=font;
	}
	//if you need to update the text you can but if it stays the same you dont need to call the update method
	public void update(String text) {
		this.text=text;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(250,250,250));//we could have a variable to change font colours later but its fine for now
		g.setFont(font);//setting the font to the right font
		g.drawString(text, x, y);//drawing the text to the screen at its x,y cordinate
	}

}
