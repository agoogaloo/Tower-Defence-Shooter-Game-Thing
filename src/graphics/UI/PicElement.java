package graphics.UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PicElement extends UIElement{
	/*
	 * this class is for parts of ui that are a picture it is its own class so it can be added to the 
	 * uiManager and be rendered ontop if other things
	 */
	BufferedImage pic;
	//this constructor takes the location and picture
	public PicElement(int x, int y, BufferedImage pic) {
		super(x, y);
		this.pic=pic;
	}
	
	//used to change the picture
	public void update(BufferedImage newPic) {
		pic=newPic;
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(pic,x,y, null);//drawing the picture at its x and y
		
		
	}
}

