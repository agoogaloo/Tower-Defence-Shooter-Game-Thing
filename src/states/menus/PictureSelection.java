package states.menus;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.Assets;


public class PictureSelection extends MenuObject{
	private BufferedImage pic, hoverPic;
	private boolean isHovered=false;
	
	public PictureSelection(Rectangle bounds, BufferedImage pic) {
		this(bounds,pic,pic);
	}
	public PictureSelection(Rectangle bounds, BufferedImage pic, BufferedImage hoverPic) {
		super(bounds);
		this.pic = pic;
		this.hoverPic=hoverPic;
		
	}

	@Override
	public void renderObject(Graphics g) {
		g.setFont(Assets.bigpixelfont);//setting the font
		if(selected||isHovered) {
			g.drawImage(hoverPic,bounds.x,bounds.y, null);
		}else {
			g.drawImage(pic,bounds.x,bounds.y, null);
		}
		
		isHovered=false;
	}
	@Override
	public void hover() {
		super.hover();
		isHovered=true;
	}


	
}
