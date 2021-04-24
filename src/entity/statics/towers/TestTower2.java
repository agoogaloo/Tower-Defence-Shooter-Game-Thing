package entity.statics.towers;

import graphics.Animation;
import graphics.Assets;

public class TestTower2 extends Tower{
	public TestTower2(int x, int y) {
		super(x, y, 50, 50, new Animation(Assets.wizardTowerLvl1,6), 10);
		price=1;
		damage=1;
		sellValue=1;
		infoText="buying cost $"+price+"\n\nthe second tower for testing";
		buyIcon=Assets.towerIcons[3];
		upgradeIcon=Assets.towerIcons[3];
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return "another testing tower";
	}

	@Override
	public Tower createNew(int x, int y) {
		return new TestTower2(x, y);
	}
}
