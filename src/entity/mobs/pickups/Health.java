package entity.mobs.pickups;

import java.awt.Graphics;

import entity.mobs.player.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Health extends Pickup{
	/*
	 * the money that is dropped when enemies die
	 */

	public Health(int x, int y) {
		super(x, y,new Animation(Assets.healthIcon), 11, 11);
		
		updateBounds();//making its bounds the right size and place
	}

	@Override
	public void update() {
		anim.update();//updating the animation
	}

	@Override
	protected void playerCollide(Player p) {
		p.heal(1);
	}
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		//drawing itself to the screen
	}

	
	

}
