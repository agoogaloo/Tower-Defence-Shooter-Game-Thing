package states;

import java.awt.Graphics;

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
	
	public GameState() {
		//creating the floor
		floor = new Floor(7, Window.getDisplay().getWidth()/Window.getDisplay().getScale(),
				Window.getDisplay().getHeight()/Window.getDisplay().getScale(), Assets.tiles);
		Entity.init();
	}
	
	@Override
	public void update() {
		getInputs().update();
		if(getInputs().isPause()) {
			currentState=new PauseState(this);
		}
		Entity.getEntityManager().update();
		camera.centerOnEntity(Entity.getEntityManager().getPlayer());
		// updating the camera position to center on the player
	}

	@Override
	public void render(Graphics g) {
		floor.render(g, camera);
		Entity.getEntityManager().render(g, camera);// rendering the entities
		UIElement.UIManager.render(g);//rendering all the ui ontop of everything
	}
	
	public static Floor getFloor() {
		return floor;
	}
	public static Camera getCamera() {
		return camera;
	}
}
