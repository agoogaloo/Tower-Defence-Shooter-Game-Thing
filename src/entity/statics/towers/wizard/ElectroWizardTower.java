package entity.statics.towers.wizard;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class ElectroWizardTower extends Tower{
	public ElectroWizardTower(int x, int y) {
		super(x, y, 150, 150, new Animation(Assets.elecricWizardTower,6), 20);
		sellValue=7;
		price=10;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=5;
		infoText="upgrade cost $"+price+"\n\ngives the wizard the power \nof electricity "
				+ "letting it \nshoot faster and stun \nenemies to slow them down";
	}
	@Override
	public Tower createNew(int x, int y) {
		return new ElectroWizardTower(x+width/2, y+height*2);
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
