package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class FireWizardTower extends Tower{
	public FireWizardTower(int x, int y) {
		super(x, y, 222, 222, new Animation(Assets.fireWizardTower,6), 20);
		sellValue=5;
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

}
