package entity.mobs.pickups;

import java.awt.Graphics;

import entity.mobs.player.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Money extends Pickup{
	/*
	 * the money that is dropped when enemies die
	 */
	Animation anim;
	public Money(int x, int y) {
		super(x, y, 11, 11);
		anim=new Animation(Assets.coin,7);//giving it an animation
		updateBounds();//making its bounds the right size and place
	}

	@Override
	public void update() {
		anim.update();//updating the animation
	}

	@Override
	void playerCollide(Player p) {
		p.giveMoney(1);
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		//drawing itself to the screen
	}

	
	

}
