package entity.statics.towers.wizard;

import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class WizardTowerlvl2 extends Tower{
	public WizardTowerlvl2(int x, int y) {
		this(x,y,null);
	}
	public WizardTowerlvl2(int x, int y,TowerSpawn spawn) {
		super(x, y, 115, 115, new Animation(Assets.wizardTowerLvl2,6), 35,spawn);
		price=1;
		sellValue=2;
		damage=15;
		infoText="-upgrade cost $"+price+"- \n \n makes the wizard tower stronger, with more range and more bullets";
		upgradeIcon=Assets.towerIcons[5];
		splitUpgrades=true;
	
	}
	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		return new WizardTowerlvl2(x+width/2, y+height*2,spawn);
	}

	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new FireWizardTower(x+width/2, y+height/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new ElectroWizardTower(x+width/2, y+height/2,spawn);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			newTower.init();
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
