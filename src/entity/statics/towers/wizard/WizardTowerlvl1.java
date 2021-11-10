package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl1 extends Tower{
	public WizardTowerlvl1(int x, int y) {
		this(x,y,null);
	}
	public WizardTowerlvl1(int x, int y, TowerSpawn spawn) {
		super(x, y, 100, 100, new Animation(Assets.wizardTowerLvl1,6), 40,spawn);
		price=2;
		damage=10;
		sellValue=1;
		infoText="-buying cost $"+price+"- \n \n an ordinary tower that shoots enemies in its range";
		buyIcon=Assets.towerIcons[3];
		upgradeIcon=Assets.towerIcons[4];
		
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new WizardTowerlvl2(x+width/2, y+height/2,spawn);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			newTower.init();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return new WizardTowerlvl2(0,0).getInfoText();
	}

	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new WizardTowerlvl1(x, y,spawn);
	}
}
