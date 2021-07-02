package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl2 extends Tower{
	public WizardTowerlvl2(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.wizardTowerLvl2,6), 30);
		price=1;
		sellValue=2;
		damage=6;
		infoText="upgrade cost $"+price+"\n\nmakes the wizard tower \nstronger, with more range \nand bullets";
		upgradeIcon=Assets.towerIcons[4];
	}
	@Override
	public Tower createNew(int x, int y) {
		return new WizardTowerlvl2(x+width/2, y+height*2);
	}

	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new FireWizardTower(x+width/2, y+height/2);	
		}else if(leftRight=='r') {
			newTower=new ElectroWizardTower(x+width/2, y+height/2);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new FireWizardTower(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new ElectroWizardTower(0,0).getInfoText();
		}else {
			return "";
		}
	}
}
