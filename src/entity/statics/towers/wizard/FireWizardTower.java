package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class FireWizardTower extends Tower{
	public FireWizardTower(int x, int y) {
		super(x, y, 125, 125, new Animation(Assets.fireWizardTower,6), 20);
		sellValue=5;
		price=5;
		damage=8;
		infoText="upgrade cost $"+price+"\n\ngives the wizard fire \npowers letting him deal \nmore damage and have more \nrange";
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	
	@Override
	public String hover(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
}
