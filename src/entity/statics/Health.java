package entity.statics;

import java.awt.Graphics;

import entity.Entity;
import entity.mobs.player.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Health extends Statics{
	/*
	 * the money that is dropped when enemies die
	 */
	Animation anim;
	public Health(int x, int y) {
		this.x=x;
		this.y=y;//setting the size and location so updatebounds will work
		width=11;
		height=11;
		health=999999999;//you dont want money to go away when it is shot so it has a bunch of health
		anim=new Animation(Assets.healthIcon);//giving it an animation
		updateBounds();//making its bounds the right size and place
	}

	@Override
	public void update() {
		for(Entity e:entityCollide()) {
			if(e instanceof Player) {//checking if it is touching the player
				Entity.getEntityManager().getPlayer().heal(1);//if it is touching it gives the player money
				killed=true;//destroying itself once the player has collected it
			}
		}
		anim.update();//updating the animation
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		//drawing itself to the screen
	}
	

}
