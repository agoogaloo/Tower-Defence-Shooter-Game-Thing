package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl2 extends Tower{
	public WizardTowerlvl2(int x, int y) {
		super(x, y, 150, 150, new Animation(Assets.wizardTowerLvl2,6), 30);
	}
}
