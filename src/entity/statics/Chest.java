package entity.statics;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import entity.statics.towers.TestTower1;
import entity.statics.towers.TestTower2;
import entity.statics.towers.Tower;
import entity.statics.towers.Mushroom.Mushroomlvl1;
import entity.statics.towers.Plant.Plantlvl1;
import entity.statics.towers.laser.LaserTowerlvl1;
import entity.statics.towers.wizard.WizardTowerlvl1;
import graphics.Assets;
import graphics.Camera;
import states.TowerPickup;

public class Chest extends Statics{
	private static Tower[] towers= {new Plantlvl1(0, 0), new LaserTowerlvl1(0, 0),new WizardTowerlvl1(0, 0),new TestTower1(0, 0),
			new TestTower2(0, 0),new Mushroomlvl1(0,0)};
	
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
			Tower tower=towers[ThreadLocalRandom.current().nextInt(0, towers.length)];
			new TowerPickup(tower);
		}
	}

}
