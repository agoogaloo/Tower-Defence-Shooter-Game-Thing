package states;

import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import floors.Floor;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.UIElement;
import graphics.particles.Particle;
import settings.Settings;
import states.console.ConsoleState;
import window.Window;

public class GameState extends State{
	/*
	 * this represents the game when it is actually being played and not in a menu or cutscene or whatever
	 */
	//floor index constants
	public final static int HUBINDEX=4, TUTORIALINDEX=5, FLOOR1=6,FLOOR2=7,FLOOR3=8;
	//constants that are needed for different things in the gamestate 
	private static Floor floor;
	private static final Camera camera= new Camera(Window.getDisplay().getWidth()/Window.getDisplay().getScale(), 
			Window.getDisplay().getHeight()/Window.getDisplay().getScale());
	private static double screenShake=0.01;//a number from 0-1 indicating how much screen shake there should be
	
	public GameState() {
		//creating the floor
		newFloor(HUBINDEX);
	}
	
	@Override
	public void update() {
		double xshake=0,yshake=0;
		int maxScreenShake=Settings.getScreenShake();
		
		getInputs().update();
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
		camera.centerOnEntity(Entity.getEntityManager().getPlayer());
		// updating the camera position to center on the player
		
		//moving the screen in a random direction by the current screen shake amount
		if(maxScreenShake>0) {//makes sure that screen shake is enabled so it doesnt break everytihng
			xshake=ThreadLocalRandom.current().nextDouble(-screenShake*maxScreenShake,screenShake*maxScreenShake);
			yshake=screenShake*maxScreenShake-xshake;
		}else {
			xshake=0;
			yshake=0;
		}
		//moving the camera the right amount
		camera.move((int)(Math.round(xshake)),(int)(Math.round(yshake)));
		//the random direction part breaks if screen shake is 0 so it resets to a super small number so it will round 
		// down to 0 when it is applied
		screenShake=0.000000001;
	}

	@Override
	public void render(Graphics g) {
		//g.clearRect(0, 0,333, 200);
		floor.render(g, camera);
		Particle.getParticleManager().renderBottom(g, camera);
		Entity.getEntityManager().render(g, camera);// rendering the entities
		Particle.getParticleManager().renderTop(g, camera);
		
		UIElement.getUIManager().render(g);//rendering all the ui ontop of everything
	}
	
	public static void newFloor(int floorIndex) {
		//creating the floor
		String path="res/maps/";
		int size=0;
		
		switch (floorIndex){
		case HUBINDEX:
			path+="hub.json";
			size=1;
			break;
		case TUTORIALINDEX:
			path+="tutorial.json";
			size=1;
			break;
		case FLOOR1:
			path+="floor 1";
			size=4;
			break;
		case FLOOR2:
			path+="floor 2";
			size=5;
			break;
		default :
				return;
		}
		
		floor = new Floor(path,size, Window.getDisplay().getWidth()/Window.getDisplay().getScale(),
				Window.getDisplay().getHeight()/Window.getDisplay().getScale(), Assets.tiles);
		UIElement.getUIManager().clear();//clearing the ui things
		Entity.init();
		
		Point coreLoc = null;
		Point playerLoc = new Point(0,0);
		
		
		for (int y = 0; y < floor.getSize()*floor.getRoomSize(); y++) {// looping though all the tiles
			for (int x = 0; x < floor.getSize()*floor.getRoomSize()*2 ; x++) {
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
		if(coreLoc!=null) {
			Entity.getEntityManager().getPlayer().createCore(coreLoc.x*16,coreLoc.y*16);
		}else {
			Entity.getEntityManager().getPlayer().createCore(playerLoc.x*16,playerLoc.y*16);
		}
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
	
	public static Camera getCamera() {
		return camera;
	}
}
