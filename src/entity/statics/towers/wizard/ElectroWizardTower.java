package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class ElectroWizardTower extends Tower{
	public ElectroWizardTower(int x, int y) {
		super(x, y, 250, 250, new Animation(Assets.elecricWizardTower,6), 15);
		sellValue=7;
		price=10;
		infoText="upgrade cost $"+price+"\n\ngives the wizard the \npower of electricity \nletting it shoot very fast \nand have even more range";
	}

	@Override
	public int upgrade(char leftRight, int money) {
		return 0;//not doing anything if they try to upgrade it
	}

	@Override
	public String hover(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
}
