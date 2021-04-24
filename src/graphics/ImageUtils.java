package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.sun.tools.javac.code.Source;

public class ImageUtils {

	public ImageUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * fills the non transparent pixels of an image with a solid colour (useful for damage flashes)
	 * @param startPic - the picture to fill
	 * @param colour - the colour it shuold be filled with
	 * @return - a buffered image that a silhouette of the original
	 */
	public static BufferedImage fillPic(BufferedImage startPic, Color colour) {
		//this method makes the image but filled with white
		BufferedImage newPic=new BufferedImage(startPic.getWidth(), startPic.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x=0;x<startPic.getWidth();x++) {
			for(int y=0;y<startPic.getHeight();y++) {
				if(new Color(startPic.getRGB(x, y), true).getAlpha()!=0) {
					newPic.setRGB(x, y, colour.getRGB());
					
				}
			}
		}
		return newPic;
	}
	/**
	 * 
	 * @return - fillPic(startPic, Color.white)
	 */
	public static BufferedImage fillPic(BufferedImage startPic) {
		return fillPic(startPic, Color.white);
	}
	
	/**
	 * a method used to give an image an outline
	 * the resulting image will be a pixel wider in each direction to accommodate for the outline
	 * @param startPic - the image to be outlined
	 * @param colour - the outline colour
	 */
	public static BufferedImage outline(BufferedImage img, Color colour) {
		BufferedImage startPic=copyImage(img,2,2);
		BufferedImage newPic = copyImage(startPic);
		
		for(int x=0;x<newPic.getWidth();x++) {
			for(int y=0;y<newPic.getHeight();y++) {
				if(isTransparent(startPic, x, y) &&(
						!isTransparent(startPic, x+1, y)||
						!isTransparent(startPic, x-1, y)||
						!isTransparent(startPic, x, y+1)||
						!isTransparent(startPic, x, y-1)
						)) {
					newPic.setRGB(x, y, colour.getRGB());
				}	
			}
		}			
		return newPic.getSubimage(1, 1, img.getWidth()+2, img.getHeight()+2);
	}
	private static boolean isTransparent(BufferedImage img, int x, int y) {
		if(x<0||y<0||x>=img.getWidth()||y>=img.getHeight()) {
			return true;
		}
		if(new Color(img.getRGB(x, y), true).getAlpha()==0) {
			return true;
		}	
		return false;
	}
	
	public static BufferedImage copyImage(BufferedImage source, int xOffset, int yOffset){
	    BufferedImage b = new BufferedImage(source.getWidth()+xOffset*2,source.getHeight()+yOffset*2, source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source,xOffset,yOffset , null);
	    g.dispose();
	    return b;
	}
	public static BufferedImage copyImage(BufferedImage source){
	   return copyImage(source, 0, 0);
	}
	

}
