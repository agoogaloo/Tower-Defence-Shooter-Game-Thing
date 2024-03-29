package entity.statics.towers.wall;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class WallL extends Tower{
	TowerSpawn platform;

	public WallL(int x, int y) {
		this(x,y,null);
		
	}
	
	public WallL(int x, int y, TowerSpawn spawn) {
		super(x, y, 0,0,new Animation(new BufferedImage[] { Assets.wallVert},6), 0,spawn);
		platform = new TowerSpawn(x-Assets.towerSpawn[0].getWidth()/2-35, y-Assets.towerSpawn[0].getHeight()/2+4,true);
		if(entityManager.getPlayer()!=null) {
			height-=entityManager.getPlayer().getHeight()+1;
		}
		updateBounds();
		solid=true;
		collisions=true;
		price=4;
		sellValue=5;
		infoText="-buying cost $"+price+"- \n \n adds a spawn platform to the left, letting you place more towers";
		buyIcon=Assets.towerIcons[15];

		
	}
	@Override
	public void init() {
		entityManager.addEntity(platform);
		super.init();
	}
	@Override
	public void update() {
		updateEffects();
		if(spawn.isKilled()) {
			Entity.getEntityManager().getPlayer().giveMoney(sellValue);
			destroy();
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return"already at max power";
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new WallL(x, y, spawn);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		platform.destroy();
	}
	

}
