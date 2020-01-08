package entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Camera;

public class Core extends Statics{
	BufferedImage[] pics=Assets.core;
	
	public Core(int x, int y){
		this.x=x;
		this.y=y;
		System.out.println("made a core");
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(pics[0], x, y, null);
	
	}

}
