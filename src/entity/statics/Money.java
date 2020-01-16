package entity.statics;

import java.awt.Graphics;

import entity.Entity;
import entity.mobs.Player;
import graphics.Assets;
import graphics.Camera;

public class Money extends Statics{
	public Money(int x, int y) {
		this.x=x;
		this.y=y;
		width=17;
		height=28;
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
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(Assets.$, x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
	

}
