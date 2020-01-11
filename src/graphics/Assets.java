package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/*
 * by: Matthew Milum
 * a class that contains all the pictures, sounds, etc. for the game
 */
public class Assets {
	public static final BufferedImage[] bullet = splitSpriteSheet(load("res/bullets.png"), 7, 7, 2, 2);
	public static final BufferedImage[] tiles=splitSpriteSheet(load("res/tileset.png"), 16, 16, 6, 10);
	public static final BufferedImage[] player = splitSpriteSheet(load("res/player idle.png"), 16, 27, 7, 4);
	public static final BufferedImage[] enemy = splitSpriteSheet(load("res/robot.png"), 21, 25, 4, 4);
	public static final BufferedImage[] core = splitSpriteSheet(load("res/core.png"), 40, 35, 3, 3);
	
	public static  final BufferedImage[] playerD=Arrays.copyOfRange(player, 0, 7);
	public static  final BufferedImage[] playerL = Arrays.copyOfRange(player, 7, 14);
	public static  final BufferedImage[] playerU =Arrays.copyOfRange(player, 14, 21);
	public static  final BufferedImage[] playerR =Arrays.copyOfRange(player,21, 28);
	
	public static final BufferedImage[] enemyD = Arrays.copyOfRange(enemy, 0, 4);
	public static final BufferedImage[] enemyL = Arrays.copyOfRange(enemy, 4, 8);
	public static final BufferedImage[] enemyU = Arrays.copyOfRange(enemy, 8, 12);
	public static final BufferedImage[] enemyR = Arrays.copyOfRange(enemy, 12, 16);

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
}
