package entity.statics.towers.wizard;

import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class ElectroWizardTower extends Tower{
	public ElectroWizardTower(int x, int y) {
		super(x, y, 150, 150, new Animation(Assets.elecricWizardTower,6), 20);
		height=15;
		sellValue=7;
		price=10;
		damage=25;
		statusEffect=new StatusEffect(StatusType.STUN,1,15);
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
	public String select(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
}
