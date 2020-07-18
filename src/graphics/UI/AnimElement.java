package graphics.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;

public class AnimElement extends UIElement{
	/*
	 * this class is for parts of ui that are an animation if you just want a picture you can 
	 * just use a 1 frame animation, however if we need to we can make one just for pictures 
	 */
	Animation anim;
	BufferedImage currentPic=Assets.blank;
	//this constructor takes the location and animation of the element
	public AnimElement(int x, int y, Animation animation) {
		super(x, y);
		this.anim=animation;
	}
	
	//if you need to update the animation you can but if its just a picture you don't need to call the update method
	public void update() {
		anim.update();
		currentPic=anim.getCurrentFrame();
		
	}
	
	public void flash() {
		currentPic=changeColour(currentPic,Color.WHITE);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(currentPic,x,y, null);//drawing the animation at its x and y
	}
	

}
