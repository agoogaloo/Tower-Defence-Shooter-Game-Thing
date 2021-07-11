package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
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
	public static final Font MozartNbp=loadFont("res/MozartNbp.ttf");
	public static final Font myfont=loadFont("res/smallfont.ttf");
	public static final Font bigpixelfont=loadFont("res/bigpixelfont.ttf");
	public static final Font boldfont=loadFont("res/boldfont.ttf");
	
	public static final BufferedImage cursor = loadImage("res/textures/cursor.png");
	//spliting all the spritesheets int the right pictures and holding them in arrays
	//some random images
	public static final BufferedImage blank=loadImage("res/textures/blank.png");
	
	//ui stuff
	public static final BufferedImage infobackground=loadImage("res/textures/heart texture.png");
	public static final BufferedImage MenuPic=loadImage("res/textures/menu.png");
	public static final BufferedImage[] healthIcon=Arrays.copyOfRange(
			splitSpriteSheet(loadImage("res/textures/heart icon.png"), 13, 13, 3, 3),0,8);
	public static final BufferedImage[] coin=splitSpriteSheet(loadImage("res/textures/coin.png"), 11, 11, 2, 2);
	public static final BufferedImage[] chest=splitSpriteSheet(loadImage("res/textures/chest.png"),24,21, 2, 1);
	public static final BufferedImage gunBg=loadImage("res/textures/ui/gunBg.png");
	public static final BufferedImage altGunBg=loadImage("res/textures/ui/altGunBg.png");

	//loading guns
	public static final BufferedImage pistol = loadImage("res/textures/guns/pistol.png");
	public static final BufferedImage[] pistolShoot = splitSpriteSheet(loadImage("res/textures/guns/pistolShoot.png"),32,21,3,1);
	public static final BufferedImage beam = loadImage("res/textures/guns/beam.png");
	public static final BufferedImage[] beamShoot = splitSpriteSheet(loadImage("res/textures/guns/beamShoot.png"),32,21,4,1);
	public static final BufferedImage beamBullet=loadImage("res/textures/bullets/beamGun.png");
	public static final BufferedImage sniper = loadImage("res/textures/guns/sniper.png");
	public static final BufferedImage[] sniperShoot = splitSpriteSheet(loadImage("res/textures/guns/sniperShoot.png"),32,21,2,1);
	public static final BufferedImage cannon = loadImage("res/textures/guns/cannon.png");
	public static final BufferedImage[] cannonShoot = splitSpriteSheet(loadImage("res/textures/guns/cannonShoot.png"),32,21,4,1);
	
	
	//loading bullets
	public static final BufferedImage enemyBullet=loadImage("res/textures/bullets/red bullet.png");
	public static final BufferedImage yellowBullet=loadImage("res/textures/bullets/yellow bullet.png");
	//these have different sizes depending on their direction so they are loaded weirdly
	private static final BufferedImage laserB=loadImage("res/textures/bullets/blue laser.png");
	private static final BufferedImage laserY=loadImage("res/textures/bullets/laser beam.png");
	
	//all the bullet types
	public static final BufferedImage[] blueLaser = {laserB.getSubimage(0,0,5,7),laserB.getSubimage(7,0,7,5),
			laserB.getSubimage(0,7,5,7),laserB.getSubimage(7,7,7,5)};//aaaaaaaaaaaaaaaaaaaaaaaaaah
	public static final BufferedImage[] YellowLaser = {laserY.getSubimage(0, 0, 10, 4),laserY.getSubimage(0, 10, 4, 10)};//aaaaaaaaaaaaaaaaaaaaaaaaaah
	public static final BufferedImage[] megaLaserU=splitSpriteSheet(loadImage("res/textures/bullets/mega laser u.png"), 
			13, 18, 9, 2);
	public static final BufferedImage[] megaLaserD=splitSpriteSheet(loadImage("res/textures/bullets/mega laser d.png"), 
			13, 18, 9, 2);
	public static final BufferedImage[] megaLaserR=splitSpriteSheet(loadImage("res/textures/bullets/mega laser r.png"), 
			18, 13, 9, 2);
	public static final BufferedImage[] megaLaserL=splitSpriteSheet(loadImage("res/textures/bullets/mega laser l.png"), 
			18, 13, 9, 2);
	public static final BufferedImage[] acorn=splitSpriteSheet(loadImage("res/textures/bullets/acorn.png"), 10, 10, 8, 1);
	
	public static final BufferedImage[] hubtiles=splitSpriteSheet(loadImage("res/textures/hub tiles.png"), 16, 16, 7, 12);
	public static final BufferedImage[] level1tiles=splitSpriteSheet(loadImage("res/textures/level 1 tiles.png"), 16, 16, 7, 12);
	public static final BufferedImage[] level2tiles=splitSpriteSheet(loadImage("res/textures/level 2 tiles.png"), 16, 16, 7, 12);
	//these are private because they are only used to split into other animations which are then actualy used by other classes
	//private static final BufferedImage[] player = splitSpriteSheet(loadImage("res/textures/player idle.png"), 14, 24, 23, 2);
	private static final BufferedImage[] playeridle = splitSpriteSheet(loadImage("res/textures/player combo.png"), 9, 17, 6, 4);
	private static final BufferedImage[] enemyRed = splitSpriteSheet(loadImage("res/textures/enemies/robot red.png"), 21, 25, 4, 4);
	private static final BufferedImage[] enemyGreen = splitSpriteSheet(loadImage("res/textures/enemies/robot green.png"), 21, 25, 4, 4);
	private static final BufferedImage[] tankBot = splitSpriteSheet(loadImage("res/textures/enemies/tank bot.png"), 34, 33, 8, 4);
	private static final BufferedImage[] enemyYellow = splitSpriteSheet(loadImage("res/textures/enemies/robot yellow.png"), 21, 25, 4, 4);
	private static final BufferedImage[] hamburgerBot = splitSpriteSheet(loadImage("res/textures/enemies/hamburger bot.png"), 33, 25, 4, 4);
	private static final BufferedImage[] heliBot = splitSpriteSheet(loadImage("res/textures/enemies/flying bot.png"), 30, 26, 4, 4);
	public static final BufferedImage[] core = splitSpriteSheet(loadImage("res/textures/core.png"), 40, 35, 3, 3);
	
	//loading towers
	public static final BufferedImage[] towerIcons = splitSpriteSheet(loadImage("res/textures/towers/tower icons.png"), 50,50, 11,1);
	
	public static final BufferedImage[] wizardTowerLvl1 =
			splitSpriteSheet(loadImage("res/textures/towers/wizard lvl.1.png"), 26, 33, 8, 1);
	public static final BufferedImage[] fireWizardTower =splitSpriteSheet(loadImage("res/textures/towers/fire wizard.png"), 18, 37, 8, 1);
	public static final BufferedImage[] elecricWizardTower =splitSpriteSheet(loadImage("res/textures/towers/electric wizard.png"),35, 29,8,1);
	public static final BufferedImage[] wizardTowerLvl2 =splitSpriteSheet(loadImage("res/textures/towers/wizard lvl.2.png"), 26, 33, 8, 1);
		
	
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
	
	public static  final BufferedImage[] plantLvl1=splitSpriteSheet(loadImage("res/textures/towers/plant lv.1.png"),9,8,10,1);
	public static  final BufferedImage[] plantLvl2=splitSpriteSheet(loadImage("res/textures/towers/plant lv.2.png"),10,12,10,1);
	public static  final BufferedImage[] tree= splitSpriteSheet(loadImage("res/textures/towers/tree.png"),37,32,8,1);
	public static  final BufferedImage[][] vinePlant=new BufferedImage[][] {
		splitSpriteSheet( loadImage("res/textures/towers/vine plant green.png"),39,37,10,1),
		//splitSpriteSheet( loadImage("res/textures/towers/vine plant yellow.png"),39,37,10,1),
		splitSpriteSheet( loadImage("res/textures/towers/vine plant purple.png"),39,37,10,1),
		splitSpriteSheet( loadImage("res/textures/towers/vine plant blue.png"),39,37,10,1)
	};
	public static  final BufferedImage[][] vines= splitAnimsSheet(loadImage("res/textures/towers/vines.png"),10,10,16,3);
	
	public static  final BufferedImage mushroom=loadImage("res/textures/towers/mushroom lv.1.png");

	
	//player animations
	public static  final BufferedImage[] playerIdleD=Arrays.copyOfRange(playeridle, 0, 6);
	public static  final BufferedImage[] playerIdleL = Arrays.copyOfRange(playeridle, 6, 12);
	public static  final BufferedImage[] playerIdleU =Arrays.copyOfRange(playeridle, 12, 18);
	public static  final BufferedImage[] playerIdleR =Arrays.copyOfRange(playeridle,18, 24);
	//running animations
	public static  final BufferedImage[] playerRunD =playerIdleD;//Arrays.copyOfRange(player,28, 34);
	public static  final BufferedImage[] playerRunL =playerIdleL;//Arrays.copyOfRange(player,34, 37);
	public static  final BufferedImage[] playerRunU =playerIdleU;//Arrays.copyOfRange(player,37, 43);
	public static  final BufferedImage[] playerRunR =playerIdleR;//Arrays.copyOfRange(player,43, 46);
	
	//enemy animations
	public static final BufferedImage[] enemyRedD = Arrays.copyOfRange(enemyRed, 0, 4);
	public static final BufferedImage[] enemyRedL = Arrays.copyOfRange(enemyRed, 4, 8);
	public static final BufferedImage[] enemyRedU = Arrays.copyOfRange(enemyRed, 8, 12);
	public static final BufferedImage[] enemyRedR = Arrays.copyOfRange(enemyRed, 12, 16);
	
	public static final BufferedImage[] enemyGreenD = Arrays.copyOfRange(enemyGreen, 0, 4);
	public static final BufferedImage[] enemyGreenL = Arrays.copyOfRange(enemyGreen, 4, 8);
	public static final BufferedImage[] enemyGreenU = Arrays.copyOfRange(enemyGreen, 8, 12);
	public static final BufferedImage[] enemyGreenR = Arrays.copyOfRange(enemyGreen, 12, 16);
	
	public static final BufferedImage[] tankBotD = Arrays.copyOfRange(tankBot, 0, 8);
	public static final BufferedImage[] tankBotL = Arrays.copyOfRange(tankBot, 8, 16);
	public static final BufferedImage[] tankBotU = Arrays.copyOfRange(tankBot, 16, 24);
	public static final BufferedImage[] tankBotR = Arrays.copyOfRange(tankBot, 24, 32);
	
	public static final BufferedImage[] enemyYellowD = Arrays.copyOfRange(enemyYellow, 0, 4);
	public static final BufferedImage[] enemyYellowL = Arrays.copyOfRange(enemyYellow, 4, 8);
	public static final BufferedImage[] enemyYellowU = Arrays.copyOfRange(enemyYellow, 8, 12);
	public static final BufferedImage[] enemyYellowR = Arrays.copyOfRange(enemyYellow, 12, 16);
	
	public static final BufferedImage[] hamburgerBotD = Arrays.copyOfRange(hamburgerBot, 0, 4);
	public static final BufferedImage[] hamburgerBotL = Arrays.copyOfRange(hamburgerBot, 4, 8);
	public static final BufferedImage[] hamburgerBotU = Arrays.copyOfRange(hamburgerBot, 8, 12);
	public static final BufferedImage[] hamburgerBotR = Arrays.copyOfRange(hamburgerBot, 12, 16);
	
	public static final BufferedImage[] heliBotD = Arrays.copyOfRange(heliBot, 0, 4);
	public static final BufferedImage[] heliBotL = Arrays.copyOfRange(heliBot, 4, 8);
	public static final BufferedImage[] heliBotU = Arrays.copyOfRange(heliBot, 8, 12);
	public static final BufferedImage[] heliBotR = Arrays.copyOfRange(heliBot, 12, 16);
	//other enemy stuff	
	public static final BufferedImage[] waveIcon=splitSpriteSheet(loadImage("res/textures/enemies/wave.png"), 15,16, 3,1);
	public static final BufferedImage[] waveDirections=splitSpriteSheet(loadImage("res/textures/enemies/wave directions.png"), 27,34, 8,1);
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
	private static BufferedImage[][] splitAnimsSheet(BufferedImage sheet, int width, int height, int rows, int columns) {
		//this takes on image and splits it into an array of several smaller photos so we 
		//only need to load a few big spritesheet instead of a million single images
		BufferedImage[][] pics=new BufferedImage[columns][rows];//creating the array
		for(int y=0;y<columns;y++) {//looping through the image vertically
			for(int x=0;x<rows;x++) {//looping horizontally
				pics[y][x]=sheet.getSubimage(x*width, y*height, width, height);
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
