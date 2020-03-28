package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class FireWizardTower extends Tower{
	public FireWizardTower(int x, int y) {
		super(x, y, 222, 222, new Animation(Assets.fireWizardTower,6), 20);
		sellValue=5;
		infoText="gives the wizard fire powers letting him shoot faster and have more range";
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	
	@Override
	public String hover(char leftRight) {
		return "this tower is in its strongest form";
	}
}
