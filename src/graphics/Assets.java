package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * by: Matthew Milum
 * a class that contains all the pictures, sounds, etc. for the game
 */
public class Assets {
	private BufferedImage test=load("res/test.png");//creating all the assets
	private final BufferedImage[] bullet = splitSpriteSheet(load("res/bullets.png"), 7, 7, 2, 2);
	private final BufferedImage[] tiles=splitSpriteSheet(load("res/tileset.png"), 16, 16, 6, 10);
	private final BufferedImage[] player = splitSpriteSheet(load("res/smol player idle.png"), 16, 27, 7, 4);
 
	public static  final BufferedImage[] playerD=new BufferedImage[7];
	public static  final BufferedImage[] playerL = new BufferedImage[7];
	public static  final BufferedImage[] playerU =new BufferedImage[7];
	public static  final BufferedImage[] playerR =new BufferedImage[7];
	
	public Assets(){
		System.arraycopy(player, 0, playerD, 0, 7);
		System.arraycopy(player, 7, playerL, 0, 7);
		System.arraycopy(player, 14, playerU, 0, 7);
		System.arraycopy(player, 21, playerR, 0, 7);
	}
	private static BufferedImage[]  splitSpriteSheet(BufferedImage sheet, int width, int height, int rows, int columns) {
		BufferedImage[] pics=new BufferedImage[rows*columns];
		for(int y=0;y<columns;y++) {
			for(int x=0;x<rows;x++) {
				pics[(y*rows)+x]=sheet.getSubimage(x*width, y*height, width, height);
			}
		}
		return pics;
	}
	private static BufferedImage load(String path) {
		/*
		 * this method loads a image from a String so you don't need to put 
		 * it in a try catch every time
		 */
		BufferedImage image = null;
		try { //this can throw an error so it needs to be in a try catch to run
			image =ImageIO.read(new File (path));//loading the test image
		} catch (IOException e) {
			System.out.println("picture "+path+" not found");//showing what the problem is
		}
		return image;
	}

	//methods that return the assets
	public BufferedImage getTest() {
		return test;
	}
	public BufferedImage[] getTiles() {
		return tiles;
	}
	public BufferedImage[] getPlayer() {
		return player;
	}
	public BufferedImage[] getPlayerD() {
		return playerD;
	}
	public BufferedImage[] getPlayerL() {
		return playerL;
	}
	public BufferedImage[] getPlayerU() {
		return playerU;
	}
	public BufferedImage[] getPlayerR() {
		return playerR;
	}
	public BufferedImage[] getBullet() {
		return bullet;
	}
}
