package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl1 extends Tower{
	public WizardTowerlvl1(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.wizardTowerLvl1,6), 40);
		price=2;
		damage=5;
		sellValue=1;
		infoText="buying cost $"+price+"\n\na basic tower that shoots in\na small circle around itself \nat a slowish rate";
		buyIcon=Assets.towerIcons[3];
		upgradeIcon=Assets.towerIcons[4];
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new WizardTowerlvl2(x+width/2, y+height/2);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		return new WizardTowerlvl2(0,0).getInfoText();
	}

	@Override
	public Tower createNew(int x, int y) {
		return new WizardTowerlvl1(x, y);
	}
}
