package entity.statics.towers.wall;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class Walllvl1 extends Tower{

	public Walllvl1(int x, int y) {
		this(x,y,null);
		
	}
	
	public Walllvl1(int x, int y, TowerSpawn spawn) {
		super(x, y, 0,0,new Animation(new BufferedImage[] { Assets.wallLvl1},6), 40,spawn);
		if(entityManager.getPlayer()!=null) {
			height-=entityManager.getPlayer().getHeight()+1;
		}
		updateBounds();
		solid=true;
		collisions=true;
		splitUpgrades=true;
		price=4;
		sellValue=2;
		infoText="-buying cost $"+price+"- \n \n a wall that can't be shot or walked through";
		buyIcon=Assets.towerIcons[15];
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
			newTower=new WallVert(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new WallHor(x+width/2, y+(height+entityManager.getPlayer().getHeight()+1)/2,spawn);
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
			return new WallVert(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new WallHor(0,0).getInfoText();
		}else {
			return "";
		}
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new Walllvl1(x, y, spawn);
	}

}
