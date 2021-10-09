package entity.statics.towers.wall;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class WallHor extends Tower{

	public WallHor(int x, int y) {
		this(x,y,null);
		
	}
	
	public WallHor(int x, int y, TowerSpawn spawn) {
		super(x, y, 0,0,new Animation(new BufferedImage[] { Assets.wallHor},6), 0,spawn);
		if(entityManager.getPlayer()!=null) {
			height-=entityManager.getPlayer().getHeight()+1;
		}
		updateBounds();
		solid=true;
		collisions=true;
		splitUpgrades=true;
		price=2;
		sellValue=3;
		infoText="buying cost $"+price+"\n\nmakes the wall much wider";
		buyIcon=Assets.towerIcons[14];
		upgradeIcon=Assets.towerIcons[17];
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
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new WallU(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new WallD(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			newTower.init();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new WallU(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new WallD(0,0).getInfoText();
		}else {
			return "";
		}
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new WallHor(x, y, spawn);
	}

}
