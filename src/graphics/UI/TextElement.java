package graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import graphics.Assets;

public class TextElement extends UIElement{
	/*
	 * this class is for parts of ui that will have text such as how much money you have
	 */
	private String text;
	private Font font=Assets.MozartNbp;
	private int width=9999, lineHeight=10;
	ArrayList <String> lines;
	//this constructor takes the location and value of the text and uses the default font
	public TextElement(int x, int y, String text) {
		super(x, y);
		this.text=text;
	}
	public TextElement(int x, int y,int width, int lineHeight,String text) {
		super(x, y);
		this.text=text;
		this.width=width;
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
		lines=null;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(250,250,250));//we could have a variable to change font colours later but its fine for now
		g.setFont(font);//setting the font to the right font
		if(lines==null) {
			setTextRows(g);
		}
		for(int i=0;i<lines.size();i++) {
			g.drawString(lines.get(i), x,y+(lineHeight*(i+1)));
			System.out.println(y+(lineHeight*(i+1)));
			
		}
	}
	
	private void setTextRows(Graphics g) {
		lines=new ArrayList<String>(1);
		String[] words=text.split("\\s+");
		int line=0;
		lines.add("");
		
		for(String i:words) {
			if(g.getFontMetrics().stringWidth(lines.get(line))/3+g.getFontMetrics().stringWidth(i)/3<width) {
				lines.set(line, lines.get(line)+i+" ");
			}
			else {
				line+=1;
				lines.add(i+" ");
			}
		}
		
	}

}
