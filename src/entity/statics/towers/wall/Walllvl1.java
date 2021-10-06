package entity.statics.towers.wall;

import java.awt.image.BufferedImage;

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
		solid=true;
		collisions=true;
		price=2;
		damage=10;
		sellValue=1;
		infoText="buying cost $"+price+"\n\na wall that can't be shot or walked through";
		buyIcon=Assets.towerIcons[14];
		upgradeIcon=Assets.towerIcons[15];
	}
	@Override
	public void update() {
		
	}
	@Override
	public int upgrade(char leftRight, int money) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String select(char leftRight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new Walllvl1(x, y, spawn);
	}

}
