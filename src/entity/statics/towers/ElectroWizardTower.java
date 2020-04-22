package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class ElectroWizardTower extends Tower{
	public ElectroWizardTower(int x, int y) {
		super(x, y, 250, 250, new Animation(Assets.elecricWizardTower,6), 15);
		sellValue=7;
		infoText="gives the wizard the \npower of electricity \nletting it shoot very fast \nand have even more range";
	}

	@Override
	public int upgrade(char leftRight, int money) {
		return 0;//not doing anything if they try to upgrade it
	}

	@Override
	public String hover(char leftRight) {
		return "this tower is in its strongest form";
	}
}
