package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class EmptyTowerSlot extends Tower{
	public EmptyTowerSlot() {
		super(0,0, 0, 0, new Animation(Assets.wizardTowerLvl1,6), 99999);
		price=0;
		damage=0;
		sellValue=0;
		infoText="you don't have a tower equiped in this slot";
		buyIcon=Assets.towerIcons[0];
		killed=true;
	}
	
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return "you shouldn't be able to read this";
	}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		return new EmptyTowerSlot();
	}
}
