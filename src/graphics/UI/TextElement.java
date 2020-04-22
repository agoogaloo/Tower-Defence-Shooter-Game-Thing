package graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import graphics.Assets;

public class TextElement extends UIElement{
	/*
	 * this class is for parts of ui that will have text such as how much money you have
	 */
	private Font font=Assets.myfont;
	private int height, lineHeight=10;
	String[] lines= new String[1];
	String text;//only really used to return the text back if it is needed
	//this constructor takes the location and value of the text and uses the default font
	public TextElement(int x, int y, String text) {
		super(x, y);
		this.text=text;
		splitTextRows(text);//adding line breaks if there is a new line in the text
	}
	public TextElement(int x, int y, int lineHeight,String text) {
		this(x,y,text);
		this.lineHeight=lineHeight;
		
	}
	//this constructor lets you specify which font you want to use
	public TextElement(int x, int y, String text, Font font) {
		this(x,y,text);
		this.font=font;
	}
	//if you need to update the text you can but if it stays the same you dont need to call the update method
	public void update(String text) {
		splitTextRows(text);
		this.text=text;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(250,250,250));//we could have a variable to change font colours later but its fine for now
		g.setFont(font);//setting the font to the right font
		for(int i=0;i<lines.length;i++) {
			g.drawString(lines[i].toUpperCase(), x,y+(lineHeight*(i+1)));
			
		}
	}
	private String[] splitTextRows(String text) {
		lines=text.split("\n");//adding line breaks if there is a new line in the text
		height=lines.length*lineHeight;
		return lines;
	}
	
	//getters/setters
	public int getHeight() {
		return height;
	}
	
	public String getText() {
		return text;
	}
}
