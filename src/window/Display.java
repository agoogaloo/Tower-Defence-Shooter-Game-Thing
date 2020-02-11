package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import floors.Floor;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.UIElement;

/*
 * by: Matthew Milum
 */
public class Display extends JPanel {
	/*
	 * this is the display where everything is actually drawn onto the display is then put onto
	 * the window so we can see it
	 * extending JPanel so that you have access to the PaintComponent to actually draw things
	 * everything is drawn onto a display which is then added onto the window,
	 * because you aren't supposed to draw directly onto it
	 */
	private int width, height, scale;
	private Floor floor;// the floor that the game it played on
	private Camera camera;
		
	public Display(int width, int height, int scale) {
		// setting the proper size so that the window will pack properly
		// the display is scaled up to look 8-bit so the
		// resolution is actually 1/3 of the screen width
		this.width = width / scale;
		this.height = height / scale;
		this.scale = scale;
		this.setPreferredSize(new Dimension(width, height));
		// setting the preferred size to the inputed one so that the pack method will
		floor = new Floor(10, this.width, this.height, Assets.tiles);
		// creating a floor for the game and giving it the tileset
		camera = new Camera(this.width, this.height);
		// in the future we would probably want to put things like the level in a
		// gameState class or something similar but I it is fine this way for now
	}

	@Override
	public void paintComponent(Graphics g) {// where everything is actually drawn
		// all rendering code goes here

		// a normal graphics object cannot scale so I cast it to a graphics2D which can
		Graphics2D g2d = (Graphics2D) g;

		g2d.scale(scale, scale);// scaling the graphics so the pixel art looks the right size
		//g2d.setColor(new Color(38,12,38));
		//g2d.fillRect(0, 0, width, height);// clearing the previous frame
		floor.render(g2d, camera);// rendering the floor
		Entity.getEntityManager().render(g2d, camera);// rendering the entities
		UIElement.UIManager.render(g2d);
	}

	public void update() {
		Entity.getEntityManager().update();// updating the entities
		camera.centerOnEntity(Entity.getEntityManager().getPlayer());
		// updating the camera position to center on the player
	}
	
	//getters
	public Camera getCamera(){
		return camera;
	}

	public Floor getFloor() {
		return floor;
	}
}
