package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/*
 * by: Matthew Milum and Kevin Tea
 * a class that contains all the pictures, sounds, etc. for the game
 */
public class Assets {
	/*
	 * this class holds and loads all the images, sounds or other assets 
	 * so other classes can access them easily
	 */
	//spliting all the spritesheets int the right pictuures and holding them in arrays
	public static final BufferedImage[] bullet = splitSpriteSheet(load("res/bullets.png"), 7, 7, 2, 2);
	public static final BufferedImage[] tiles=splitSpriteSheet(load("res/tileset.png"), 16, 16, 6, 10);
	public static final BufferedImage[] player = splitSpriteSheet(load("res/player idle.png"), 16, 27, 7, 4);
	public static final BufferedImage[] enemyRed = splitSpriteSheet(load("res/robot red.png"), 21, 25, 4, 4);
	public static final BufferedImage[] enemyGreen = splitSpriteSheet(load("res/robot green.png"), 21, 25, 4, 4);
	public static final BufferedImage[] enemyBlue = splitSpriteSheet(load("res/robot blue.png"), 21, 25, 4, 4);
	public static final BufferedImage[] enemyYellow = splitSpriteSheet(load("res/robot yellow.png"), 21, 25, 4, 4);
	public static final BufferedImage[] core = splitSpriteSheet(load("res/core.png"), 40, 35, 3, 3);
	public static final BufferedImage[] wizardTower = splitSpriteSheet(load("res/wizard tower.png"), 26, 33, 3, 3);
	
	//splitting players spritesheet into all its animations

	public static  final BufferedImage[] playerD=Arrays.copyOfRange(player, 0, 7);
	public static  final BufferedImage[] playerL = Arrays.copyOfRange(player, 7, 14);
	public static  final BufferedImage[] playerU =Arrays.copyOfRange(player, 14, 21);
	public static  final BufferedImage[] playerR =Arrays.copyOfRange(player,21, 28);
	
	public static final BufferedImage[] enemyRedD = Arrays.copyOfRange(enemyRed, 0, 4);
	public static final BufferedImage[] enemyRedL = Arrays.copyOfRange(enemyRed, 4, 8);
	public static final BufferedImage[] enemyRedU = Arrays.copyOfRange(enemyRed, 8, 12);
	public static final BufferedImage[] enemyRedR = Arrays.copyOfRange(enemyRed, 12, 16);
	
	public static final BufferedImage[] enemyGreenD = Arrays.copyOfRange(enemyGreen, 0, 4);
	public static final BufferedImage[] enemyGreenL = Arrays.copyOfRange(enemyGreen, 4, 8);
	public static final BufferedImage[] enemyGreenU = Arrays.copyOfRange(enemyGreen, 8, 12);
	public static final BufferedImage[] enemyGreenR = Arrays.copyOfRange(enemyGreen, 12, 16);
	
	public static final BufferedImage[] enemyBlueD = Arrays.copyOfRange(enemyBlue, 0, 4);
	public static final BufferedImage[] enemyBlueL = Arrays.copyOfRange(enemyBlue, 4, 8);
	public static final BufferedImage[] enemyBlueU = Arrays.copyOfRange(enemyBlue, 8, 12);
	public static final BufferedImage[] enemyBlueR = Arrays.copyOfRange(enemyBlue, 12, 16);
	
	public static final BufferedImage[] enemyYellowD = Arrays.copyOfRange(enemyYellow, 0, 4);
	public static final BufferedImage[] enemyYellowL = Arrays.copyOfRange(enemyYellow, 4, 8);
	public static final BufferedImage[] enemyYellowU = Arrays.copyOfRange(enemyYellow, 8, 12);
	public static final BufferedImage[] enemyYellowR = Arrays.copyOfRange(enemyYellow, 12, 16);

	public static final BufferedImage[] wizardTowerFix = Arrays.copyOfRange(wizardTower, 0, 8);

	private static BufferedImage[]  splitSpriteSheet(BufferedImage sheet, int width, int height, int rows, int columns) {
		//this takes on image and splits it into an array of several smaller photos so we 
		//only need to load a few big spritesheet instead of a million single images
		BufferedImage[] pics=new BufferedImage[rows*columns];//creating the array
		for(int y=0;y<columns;y++) {//looping through the image vertically
			for(int x=0;x<rows;x++) {//looping horizontally
				pics[(y*rows)+x]=sheet.getSubimage(x*width, y*height, width, height);
				//spliting the image and putting it in the array
			}
		}
		return pics;//returning the array
	}
	private static BufferedImage load(String path) {
		/*
		 * this method loads a image from a String so you don't need to put 
		 * it in a try catch every time
		 */
		BufferedImage image = null;
		try { //this can throw an error so it needs to be in a try catch to run
			image =ImageIO.read(new File (path));//loading the image
		} catch (IOException e) {
			System.out.println("picture "+path+" not found");//showing what the problem is
		}
		return image;
	}
}
