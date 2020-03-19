package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class WizardTower extends Tower{
	public WizardTower(int x, int y) {
		super(x, y, 130, 130, new Animation(Assets.wizardTower,6), 40);
	}
}
