package states;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import floors.Floor;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.UIElement;
import window.Window;

public class GameState extends State{
	/*
	 * this represents the game when it is actualy being played and not in a menu or cutscene or whatever
	 */
	//constants that are needed for different things in the gamestate 
	private static Floor floor;
	private static final Camera camera= new Camera(Window.getDisplay().getWidth()/Window.getDisplay().getScale(), 
			Window.getDisplay().getHeight()/Window.getDisplay().getScale());
	private static int maxScreenShake=10;//the maximum amount in pixels that the screen can move from screen shake
	private static double screenShake=0.01;//a number from 0-1 indicating how much screen shake there should be
	public GameState() {
		//creating the floor
		newFloor();
	}
	
	@Override
	public void update() {
		double xshake=0,yshake=0;
		getInputs().update();
		if(getInputs().isPause()) {
			currentState=new PauseState(this);
		}
		Entity.getEntityManager().update();
		camera.centerOnEntity(Entity.getEntityManager().getPlayer());
		// updating the camera position to center on the player
		
		//moving the screen in a random direction by the current screen shake amount
		xshake=ThreadLocalRandom.current().nextDouble(-screenShake*maxScreenShake,screenShake*maxScreenShake);
		yshake=screenShake*maxScreenShake-xshake;
		//moving the camera the right amount
		camera.move((int)(Math.round(xshake)),(int)(Math.round(yshake)));
		//the random direction part breaks if screen shake is 0 so it resets to 0.01 which will round 
		//to 0 when it is applied
		screenShake=0.01;
	}

	@Override
	public void render(Graphics g) {
		floor.render(g, camera);
		Entity.getEntityManager().render(g, camera);// rendering the entities
		UIElement.getUIManager().render(g);//rendering all the ui ontop of everything
	}
	
	public static void newFloor() {
		//creating the floor
		UIElement.getUIManager().clear();
		floor = new Floor(8, Window.getDisplay().getWidth()/Window.getDisplay().getScale(),
				Window.getDisplay().getHeight()/Window.getDisplay().getScale(), Assets.tiles);
		Entity.init();
		
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
