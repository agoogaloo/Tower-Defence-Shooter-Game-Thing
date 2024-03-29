package entity.statics;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.pickups.GunTowerItem;
import entity.mobs.pickups.ItemList;
import graphics.Assets;
import graphics.Camera;

public class Chest extends Statics{
	public Chest(int x,int y) {
		this.x=x-12;
		this.y=y-10;
		width=24;
		height=21;
		updateBounds();
		
		health=1;
		damage=0;
		friendly=false;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(Assets.chest[0], x-camera.getxOffset(), y-camera.getyOffset(), null);
	}
	
	@Override
	public void damage() {
		super.damage();
		if(killed) {
			int item=ItemList.getFindableItems()[ThreadLocalRandom.current().nextInt(0, ItemList.getFindableItems().length)];
			
			entityManager.addEntity(new GunTowerItem(x, y, item));
			//new TowerPickup(tower);
		}
	}

}
