package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl1 extends Tower{
	public WizardTowerlvl1(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.wizardTowerLvl1,6), 40);
		upgradeIcon=Assets.towerMenu[2];
		sellValue=1;
		infoText="a a a a a a a a a a a a a a a a a a a a a a a b b b b i i i i i i i i i i i i i i i i i i ig g g g g g g g g g g g g g ";
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		if(money>=1) {
			entityManager.addEntity(new WizardTowerlvl2(x+width/2, y+height/2));
			destroy();
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public String hover(char leftRight) {
		return new WizardTowerlvl2(0,0).getInfoText();
	}
}
