package states;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.statics.Chest;
import entity.statics.Door;
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
	public final static int HUBINDEX=8, TUTORIALINDEX=9, FLOOR1=10,FLOOR2=11,FLOOR3=12;
	//constants that are needed for different things in the gamestate
	private static int floorIndex;
	private static Floor floor;
	private static Tutorialator tutorial;
	private static boolean canHaveEnemies=true;
	private static final Camera camera= new Camera(Window.getDisplay().getWidth()/Window.getDisplay().getScale(), 
			Window.getDisplay().getHeight()/Window.getDisplay().getScale());
	private static double screenShake=0.01;//a number from 0-1 indicating how much screen shake there should be
	
	public GameState() {
		this(HUBINDEX);
		
	}
	public GameState(int floorIndex) {
		//creating the floor
		newFloor(floorIndex);
		
	}
	
	@Override
	public void update() {
		double xshake=0,yshake=0;
		int maxScreenShake=Settings.getScreenShake();
		
		
		if(getInputs().isPause()) {
			currentState=new PauseState(this);
		}else if(getInputs().isConsole()) {
			currentState=new ConsoleState(this);
		}
		if(ConsoleState.isGameFrozen()) {
			return;
		}
		
		
		Particle.getParticleManager().update();
		Entity.getEntityManager().update();
		if(Entity.getEntityManager().getPlayer().isKilled()&&currentState==this) {
			State.currentState=new DeadState(this);
		}
		
		// updating the camera position to center on the player
		camera.moveToEntity(Entity.getEntityManager().getPlayer());
		camera.moveToMouse(getInputs().getMouseX(), getInputs().getMouseY());
	
		
		camera.update();
		if(tutorial!=null)tutorial.update();
		
		
		//moving the screen in a random direction by the current screen shake amount
		if(maxScreenShake>0) {//makes sure that screen shake is enabled so it doesnt break everytihng
			xshake=ThreadLocalRandom.current().nextDouble(-screenShake*maxScreenShake,screenShake*maxScreenShake);
			yshake=screenShake*maxScreenShake-xshake;
		}else {
			xshake=0;
			yshake=0;
		}
		//moving the camera the right amount
		//camera.move((int)(Math.round(xshake)),(int)(Math.round(yshake)));
		//the random direction part breaks if screen shake is 0 so it resets to a super small number so it will round 
		// down to 0 when it is applied
		getInputs().update();
		screenShake=0.000000001;
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
			break;
		case TUTORIALINDEX:
			System.out.println("loading tutorial from index "+newFloorIndex);
			path+="tutorial.json";
			deletePlayer=true;
			size=1;
			canHaveEnemies=false;
			tiles=Assets.level1tiles;
			break;
			
		case FLOOR1:
			System.out.println("loading floor one from index "+newFloorIndex);
			path+="floor 1";
			size=4;
			canHaveEnemies=true;
			tiles=Assets.level1tiles;
			deletePlayer=true;
			break;
		case FLOOR2:
			path+="floor 2";
			size=5;
			canHaveEnemies=true;
			tiles=Assets.level2tiles;
			break;
		case FLOOR3:
			currentState = new WinState();
			System.out.println("you win!");
		default :
				return;
		}
		floorIndex=newFloorIndex;
		
		UIElement.getUIManager().clear();//clearing the ui things
		floor = new Floor(path,size, Window.getDisplay().getWidth()/Window.getDisplay().getScale(),
				Window.getDisplay().getHeight()/Window.getDisplay().getScale(), tiles);
		Entity.init(deletePlayer);
		floor.init();
		
		if(newFloorIndex==TUTORIALINDEX)
			tutorial=new Tutorialator();
		else tutorial=null;
		
		Point coreLoc = null;
		Point playerLoc = new Point(0,0);
		
		
		for (int y = 0; y < floor.getSize()*floor.getHeight(); y++) {// looping though all the tiles
			for (int x = 0; x < floor.getSize()*floor.getWidth() ; x++) {
				switch (floor.getSpawnData(x, y)) {
				case 1:
					coreLoc=new Point(x,y);
					System.out.println("found core spawn");
					break;
		
				case 2:
					playerLoc=new Point(x,y);
					System.out.println("found player spawn");
					break;
					
				}
			}
		}
		Entity.getEntityManager().getPlayer().reset(playerLoc.x*16,playerLoc.y*16);
		if(newFloorIndex!=TUTORIALINDEX&&newFloorIndex!=HUBINDEX) {
			Entity.getEntityManager().addEntity(new Chest(playerLoc.x*16+28,playerLoc.y*16-55));
			Entity.getEntityManager().addEntity(new Chest(playerLoc.x*16-12,playerLoc.y*16-55));
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
}
