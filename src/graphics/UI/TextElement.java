package graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import graphics.Assets;

public class TextElement extends UIElement{
	/*
	 * this class is for parts of ui that will have text such as how much money you have
	 */
	
	private static JSONObject smallFontSizes=loadJsonObject("res/smallfont.json");
	private String text;
	private Font font=Assets.myfont;
	private int lineWidth=9999,height, lineHeight=10;
	private boolean buildLines=true;
	ArrayList <String> lines=new ArrayList<String>();
	//this constructor takes the location and value of the text and uses the default font
	public TextElement(int x, int y, String text) {
		super(x, y);
		this.text=text;
	}
	public TextElement(int x, int y,int width, int lineHeight,String text) {
		super(x, y);
		this.text=text;
		this.lineWidth=width;
		this.lineHeight=lineHeight;
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
		buildLines=true;
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(250,250,250));//we could have a variable to change font colours later but its fine for now
		g.setFont(font);//setting the font to the right font
		if(buildLines) {
			setTextRows(g);
			buildLines=false;
		}
		for(int i=0;i<lines.size();i++) {
			g.drawString(lines.get(i).toUpperCase(), x,y+(lineHeight*(i+1)));
			
		}
	}
	
	private void setTextRows(Graphics g) {
		lines.clear();
		String[] words=text.toUpperCase().split("\\s+");
		int line=0;
		lines.add("");
		System.out.println(g.getFontMetrics().stringWidth("A"));

		for(String i:words) {
			if(g.getFontMetrics().stringWidth(lines.get(line))+g.getFontMetrics().stringWidth(i)+g.getFontMetrics().charWidth(' ')<lineWidth) {
				lines.set(line, lines.get(line)+i+" ");
			}
			else {
				line+=1;
				lines.add(i+" ");
			}
		}
		height=(line+1)*lineHeight;
	}
	
	private static JSONObject loadJsonObject(String path) {
		//this is just used to load in json objects so it is static
		try {
			JSONObject file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
			return file;
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
		}
		System.out.print("this shouldnt happen");
		return null;
	}
	
	private int stringlength(JSONObject sizes, String string) {
		int size=0;//the size of the string in pixels
		JSONObject widths=(JSONObject)(sizes.get("charSizes"));//the json object that hold info about 
		System.out.println(widths.toString());
		//the fonts sizes because java cant measure things properly
		for(int i=0;i<string.length();i++) {
			try {
				size+=(int)((long)widths.get(string.charAt(i)));//adding the size of the letters to size
			}catch(NullPointerException e) {
				System.out.println(string.charAt(i)+" isnt found");
			}
			size+=(int)((long)sizes.get("charSpaces"));//adding the space between letters to the size
			

		}
		System.out.println(string+" is "+size+" pixels long");
		return size;
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return lineWidth;
	}
	
}
