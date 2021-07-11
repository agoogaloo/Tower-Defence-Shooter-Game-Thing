package entity.statics;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import Main.ItemList;
import entity.mobs.pickups.GunTowerItem;
import graphics.Assets;
import graphics.Camera;

public class Chest extends Statics{
	private static int[] items= {ItemList.LASER,ItemList.PLANT, ItemList.BEAM, ItemList.SNIPER,
			ItemList.CANNON};
	
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
			int item=items[ThreadLocalRandom.current().nextInt(0, items.length)];
			
			entityManager.addEntity(new GunTowerItem(x, y, item));
			//new TowerPickup(tower);
		}
	}

}
