package entity.statics.towers.wall;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class WallVert extends Tower{

	public WallVert(int x, int y) {
		this(x,y,null);
		
	}
	
	public WallVert(int x, int y, TowerSpawn spawn) {
		super(x, y, 0,0,new Animation(new BufferedImage[] { Assets.wallVert},6), 0,spawn);
		if(entityManager.getPlayer()!=null) {
			height-=entityManager.getPlayer().getHeight()+1;
		}
		updateBounds();
		solid=true;
		collisions=true;
		splitUpgrades=true;
		price=2;
		sellValue=3;
		infoText="-buying cost $"+price+"- \n \n makes the wall much taller";
		buyIcon=Assets.towerIcons[14];
		upgradeIcon=Assets.towerIcons[16];
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
			newTower=new WallL(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new WallR(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);
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
			return new WallL(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new WallR(0,0).getInfoText();
		}else {
			return "";
		}
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new WallVert(x, y, spawn);
	}

}
