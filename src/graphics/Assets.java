package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import entity.mobs.Bullet;

/*
 * by: Matthew Milum and Kevin Tea
 * a class that contains all the pictures, sounds, etc. for the game
 */
public class Assets {
	/*
	 * this class holds and loads all the images, sounds or other assets 
	 * so other classes can access them easily
	 */
	public static final Font MozartNbp=loadFont("res/MozartNbp.ttf");
	//spliting all the spritesheets int the right pictures and holding them in arrays
	//some random images
	public static final BufferedImage heartTexture=loadImage("res/textures/heart texture.png");
	//loading bullets
	public static final BufferedImage enemyBullet=loadImage("res/textures/bullets/red bullet.png");
	public static final BufferedImage yellowBullet=loadImage("res/textures/bullets/yellow bullet.png");
	//these have different sizes depending on their direction so they are loaded weirdly
	private static final BufferedImage laserB=loadImage("res/textures/bullets/blue laser.png");
	private static final BufferedImage laserY=loadImage("res/textures/bullets/laser beam.png");
	public static final BufferedImage[] blueLaser = {laserB.getSubimage(0,0,5,7),laserB.getSubimage(7,0,7,5),
			laserB.getSubimage(0,7,5,7),laserB.getSubimage(7,7,7,5)};//aaaaaaaaaaaaaaaaaaaaaaaaaah
	public static final BufferedImage[] YellowLaser = {laserY.getSubimage(0, 0, 10, 4),laserY.getSubimage(0, 10, 4, 10)};//aaaaaaaaaaaaaaaaaaaaaaaaaah
	
	
	public static final BufferedImage[] towerMenu=splitSpriteSheet(loadImage("res/textures/towers/tower menu.png"),
			50, 50, 2, 3);
	public static final BufferedImage[] healthIcon=Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/heart icon.png"), 13, 13, 3, 3),0,8);
	public static final BufferedImage[] coin=splitSpriteSheet(loadImage("res/textures/coin.png"), 11, 11, 2, 2);
	public static final BufferedImage[] tiles=splitSpriteSheet(loadImage("res/textures/tileset.png"), 16, 16, 6, 10);
	//these are private because they are only used to split into other animations which are then actualy used by other classes
	private static final BufferedImage[] player = splitSpriteSheet(loadImage("res/textures/player idle.png"), 14, 25, 7, 4);
	private static final BufferedImage[] enemyRed = splitSpriteSheet(loadImage("res/textures/enemies/robot red.png"), 21, 25, 4, 4);
	private static final BufferedImage[] enemyGreen = splitSpriteSheet(loadImage("res/textures/enemies/robot green.png"), 21, 25, 4, 4);
	private static final BufferedImage[] enemyBlue = splitSpriteSheet(loadImage("res/textures/enemies/robot blue.png"), 21, 25, 4, 4);
	private static final BufferedImage[] enemyYellow = splitSpriteSheet(loadImage("res/textures/enemies/robot yellow.png"), 21, 25, 4, 4);
	public static final BufferedImage[] core = splitSpriteSheet(loadImage("res/textures/core.png"), 40, 35, 3, 3);
	
	//loading towers
	
	public static final BufferedImage[] wizardTowerLvl1 =  Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/towers/wizard lvl.1.png"), 26, 33, 3, 3),0,8);
	public static final BufferedImage[] fireWizardTower =splitSpriteSheet(loadImage("res/textures/towers/fire wizard.png"), 18, 37, 4, 2);
	public static final BufferedImage[] elecricWizardTower =  Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/towers/electric wizard.png"),35, 29, 3, 3),0,8);
	public static final BufferedImage[] wizardTowerLvl2 =  Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/towers/wizard lvl.2.png"), 26, 33, 3, 3),0,8);
		
	
	//the laser tower has alot becasue t has different up down left and right animations
	public static  final BufferedImage[] laserTowerLvl1U=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.1 u.png"),13,27,3,2);
	public static  final BufferedImage[] laserTowerLvl1L=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.1 l.png"),28,14,2,3);
	public static  final BufferedImage[] laserTowerLvl1D= Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.1 d.png"),13,26,3,2),0,5);
	public static  final BufferedImage[] laserTowerLvl1R=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.1 r.png"),28,14,2,3);
	
	public static  final BufferedImage[] laserTowerLvl2U=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.2 u.png"),15,29,3,2);
	public static  final BufferedImage[] laserTowerLvl2L=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.2 l.png"),30,15,3,2);
	public static  final BufferedImage[] laserTowerLvl2D=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.2 d.png"),15,27,3,2);
	public static  final BufferedImage[] laserTowerLvl2R=splitSpriteSheet(loadImage("res/textures/towers/laser tower lv.2 r.png"),30,15,3,2);
	
	public static  final BufferedImage[] machineGunTowerU=splitSpriteSheet(loadImage("res/textures/towers/machine gun u.png"),14,28,3,1);
	public static  final BufferedImage[] machineGunTowerR=splitSpriteSheet(loadImage("res/textures/towers/machine gun r.png"),34,14,1,3);
	public static  final BufferedImage[] machineGunTowerD=splitSpriteSheet(loadImage("res/textures/towers/machine gun d.png"),14,29,3,1);
	public static  final BufferedImage[] machineGunTowerL=splitSpriteSheet(loadImage("res/textures/towers/machine gun l.png"),34,14,1,3);

	public static  final BufferedImage[] bigLaserTowerU=splitSpriteSheet(loadImage("res/textures/towers/big laser tower U.png"),19,32,4,2);	
	public static  final BufferedImage[] bigLaserTowerR=splitSpriteSheet(loadImage("res/textures/towers/big laser tower R.png"),37,21,2,4);	
	public static  final BufferedImage[] bigLaserTowerD=splitSpriteSheet(loadImage("res/textures/towers/big laser tower D.png"),19,30,4,2);	
	public static  final BufferedImage[] bigLaserTowerL=splitSpriteSheet(loadImage("res/textures/towers/big laser tower L.png"),37,21,2,4);	
	
	//player animations
	public static  final BufferedImage[] playerD=Arrays.copyOfRange(player, 0, 7);
	public static  final BufferedImage[] playerL = Arrays.copyOfRange(player, 7, 14);
	public static  final BufferedImage[] playerU =Arrays.copyOfRange(player, 14, 21);
	public static  final BufferedImage[] playerR =Arrays.copyOfRange(player,21, 28);
	//enemy animations
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
	private static BufferedImage loadImage(String path) {
		/*
		 * this method loads a image from a String so you don't need to put 
		 * it in a try catch every time
		 */
		BufferedImage image = null;
		try { //this can throw an error so it needs to be in a try catch to run
			image =ImageIO.read(new File(path));//loading the image
		} catch (IOException e) {
			System.out.println("picture "+path+" not found");//showing what the problem is
		}
		return image;
	}
	private static Font loadFont(String path) {
		Font font=null;
		try {
			font =Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(16f);
		} catch (FontFormatException | IOException e) {
			System.out.println("font at path "+path+" not found");
			e.printStackTrace();
		}
		if(font!=null) {
			System.out.println("font loaded");
		}
		return font;
	}
}
