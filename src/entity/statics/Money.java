package entity.statics;

import java.awt.Graphics;

import entity.Entity;
import entity.mobs.player.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Money extends Statics{
	Animation anim;
	public Money(int x, int y) {
		this.x=x;
		this.y=y;
		width=11;
		height=11;
		health=999999999;
		anim=new Animation(Assets.coin,7);
		updateBounds();
	}

	@Override
	public void update() {
		for(Entity e:entityCollide()) {
			if(e instanceof Player) {
				Entity.getEntityManager().getPlayer().giveMoney(1);
				killed=true;
			}
		}
		anim.update();
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
	

}
