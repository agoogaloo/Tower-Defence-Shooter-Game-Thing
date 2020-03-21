package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl2 extends Tower{
	public WizardTowerlvl2(int x, int y) {
		super(x, y, 150, 150, new Animation(Assets.wizardTowerLvl2,6), 30);
		sellValue=2;
		upgradeIcon=Assets.towerMenu[3];
	}

	@Override
	public int upgrade(char leftRight, int money) {
		
		if(leftRight=='l') {
			if(money>=5) {
			entityManager.addEntity(new FireWizardTower(x+width/2, y+height/2));
			destroy();
			return 5;
			}
		}else if(leftRight=='r') {
			if(money>=10) {
			entityManager.addEntity(new ElectroWizardTower(x+width/2, y+height/2));
			destroy();
			return 10;
			}
		}
		return 0;
	}
}
