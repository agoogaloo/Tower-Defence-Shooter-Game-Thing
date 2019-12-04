package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * a class that contains all the pictures, sounds, etc. for the game
 */
public class Assets {
	private static BufferedImage test=load("res/test.png");//creating all the assets
	
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
	public static BufferedImage getTest() {
		return test;
	}		
}
