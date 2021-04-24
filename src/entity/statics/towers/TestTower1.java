package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class TestTower1 extends Tower{
	public TestTower1(int x, int y) {
		super(x, y, 1000, 1000, new Animation(Assets.wizardTowerLvl1,6), 120);
		price=3;
		damage=5;
		sellValue=1;
		infoText="buying cost $"+price+"\n\na the first test tower wow";
		buyIcon=Assets.towerIcons[3];
		upgradeIcon=Assets.towerIcons[3];
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return "imagine that this is really cool";
	}

	@Override
	public Tower createNew(int x, int y) {
		return new TestTower1(x, y);
	}
}
