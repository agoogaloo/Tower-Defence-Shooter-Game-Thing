package states;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.statics.Chest;
import floors.Floor;
import floors.Tutorialator;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.UIElement;
import graphics.particles.Particle;
import saveData.SaveData;
import saveData.Settings;
import states.console.ConsoleState;
import window.Window;

public class GameState extends State{
	/*
	 * this represents the game when it is actually being played and not in a menu or cutscene or whatever
	 */
	//floor index constants
	public final static int HUBINDEX=1, TUTORIALINDEX=2, FLOOR1=3,FLOOR2=4,FLOOR3=5,CORESPAWN=13, PLAYERSPAWN=14;
	//constants that are needed for different things in the gamestate
	private static int floorIndex;
	private static Floor floor;
	private static Tutorialator tutorial;
	private static boolean canHaveEnemies=true;
	private static final Camera camera= new Camera(Window.getDisplay().getWidth()/Window.getDisplay().getScale(), 
			Window.getDisplay().getHeight()/Window.getDisplay().getScale());
	private static double screenShake=0.0;//how much the screen will shake from 0-1
	private static double shakeX = 0, shakeY = 0;//current offsets from screenshake
	private static long startTime = 0;
	public GameState() {
		this(HUBINDEX);
		
	}
	public GameState(int floorIndex) {
		//creating the floor
		newFloor(floorIndex);
		
	}
	
	@Override
	public void update(){

		if(getInputs().isPause()) {
			currentState=new PauseState(this);
		}else if(getInputs().isConsole()) {
			currentState=new ConsoleState(this);
		}
		
		
		Particle.getParticleManager().update();
		if(!ConsoleState.isGameFrozen()) {
			Entity.getEntityManager().update();
		}
		if(Entity.getEntityManager().getPlayer().isKilled()&&currentState==this) {
			State.currentState=new DeadState(this, startTime);
		}
		
		// updating the camera position to center on the player
		camera.moveToEntity(Entity.getEntityManager().getPlayer());
		camera.moveToMouse(getInputs().getMouseX(), getInputs().getMouseY());
	
		
		camera.update();
		if(tutorial!=null)tutorial.update();
		
		//doing screenshake
		double shakeDeltaX, shakeDeltaY;
		double maxShakeOffset = Settings.getScreenShake()*screenShake;
		
		//moving the screen in a random direction by the current screen shake amount
		if(maxShakeOffset>0) {//makes sure that screen shake is enabled so it doesnt break everytihng
			shakeDeltaX=ThreadLocalRandom.current().nextDouble(-maxShakeOffset/2,maxShakeOffset/2);
			shakeDeltaY=maxShakeOffset/2-shakeDeltaX;

			shakeX+=shakeDeltaX;
			shakeY+=shakeDeltaY;

			shakeX = Math.min(shakeX, maxShakeOffset);
			shakeX = Math.max(shakeX,-maxShakeOffset);
			shakeY = Math.min(shakeY, maxShakeOffset);
			shakeY = Math.max(shakeY,-maxShakeOffset);

		}else {
			shakeX = 0;
			shakeY = 0;
		}
		screenShake = Math.max(0,screenShake-0.1);

		//moving the camera the right amount
		camera.move((int)(Math.round(shakeX)),(int)(Math.round(shakeY)));
		//the random direction part breaks if screen 
		getInputs().update();
	}
	

	@Override
	public void render(Graphics g) {
		//g.clearRect(0, 0,333, 200);
		floor.render(g, camera);
		Particle.getParticleManager().renderBottom(g, camera);
		Entity.getEntityManager().render(g, camera);// rendering the entities
		Particle.getParticleManager().renderTop(g, camera);
		
		if(tutorial!=null)tutorial.render(g,camera);
		UIElement.getUIManager().render(g);//rendering all the ui ontop of everything
	}
	
	public static void newFloor(int newFloorIndex) {
		//creating the floor
		
		String path="res/maps/";
		int size=0;
		int difficulty = 4;
		boolean deletePlayer=false;
		BufferedImage[] tiles;
		
		if(!SaveData.isFinishedTutorial()&&newFloorIndex==FLOOR1)
			newFloorIndex=TUTORIALINDEX;
		switch (newFloorIndex){
		case HUBINDEX:
			System.out.println("loading hub from index "+newFloorIndex);
			path+="hub.json";
			deletePlayer=true;
			size=1;
			canHaveEnemies=false;
			tiles=Assets.hubtiles;
			ConsoleState.cheatsUsed = false;
			break;
		case TUTORIALINDEX:
			System.out.println("loading tutorial from index "+newFloorIndex);
			path+="tutorial.json";
			deletePlayer=true;
			size=1;
			canHaveEnemies=false;
			tiles=Assets.level1tiles;
			startTime = System.currentTimeMillis();
			break;
			
		case FLOOR1:
			System.out.println("loading floor one from index "+newFloorIndex);
			path+="floor 1";
			size=4;
			canHaveEnemies=true;
			tiles=Assets.level1tiles;
			deletePlayer=true;
			startTime = System.currentTimeMillis();
			break;
		case FLOOR2:
			path+="floor 2";
			size=5;
			canHaveEnemies=true;
			tiles=Assets.level2tiles;
			difficulty = 6;
			break;
		case FLOOR3:
			currentState = new WinState(startTime);
			System.out.println("you win!");
		default :
				return;
		}
		floorIndex=newFloorIndex;
		
		UIElement.getUIManager().clear();//clearing the ui things
		floor = Floor.getRightFloor(path,newFloorIndex,size, Window.getDisplay().getWidth()/Window.getDisplay().getScale(),
				Window.getDisplay().getHeight()/Window.getDisplay().getScale(), tiles);
		Entity.getEntityManager().reset(deletePlayer,difficulty);
		floor.init();
		
		
		if(newFloorIndex==TUTORIALINDEX)
			tutorial=new Tutorialator();
		else tutorial=null;
		
		Point coreLoc = null;
		Point playerLoc = new Point(0,0);
		
		
		for (int y = 0; y < floor.getSize()*floor.getHeight(); y++) {// looping though all the tiles
			for (int x = 0; x < floor.getSize()*floor.getWidth() ; x++) {
				switch (floor.getSpawnData(x, y)) {
				case CORESPAWN:
					coreLoc=new Point(x,y);
					System.out.println("found core spawn");
					break;
		
				case PLAYERSPAWN:
					playerLoc=new Point(x,y);
					System.out.println("found player spawn");
					break;
					
				}
			}
		}
		Entity.getEntityManager().getPlayer().reset(playerLoc.x*16,playerLoc.y*16);
		if(newFloorIndex!=TUTORIALINDEX&&newFloorIndex!=HUBINDEX) {
			Entity.getEntityManager().addEntity(new Chest(playerLoc.x*16+6,playerLoc.y*16-40));
		}
		
		if(coreLoc!=null) {
			Entity.getEntityManager().getPlayer().createCore(coreLoc.x*16+8,coreLoc.y*16+8);
		}else {
			Entity.getEntityManager().getPlayer().createCore(playerLoc.x*16+8,playerLoc.y*16+8);
		}
		camera.snapToLoc(playerLoc.x*16,playerLoc.y*16);
	}
	public static void screenShake(double amount) {
		screenShake+=amount;
		if(screenShake>1) {
			screenShake=1;
		}
	}
	public static Floor getFloor() {
		return floor;
	}
	public static int getFloorIndex() {
		return floorIndex;
	}
	
	public static Camera getCamera() {
		return camera;
	}
	
	public static boolean canHaveEnemies() {
		return canHaveEnemies;
		
	}
	public static long getStartTime() {
		return startTime;
	}
	
}
