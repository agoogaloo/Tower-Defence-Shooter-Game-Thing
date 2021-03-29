package entity.statics.towers.Plant;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Plantlvl1 extends Tower{
	public Plantlvl1(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.plantLvl1,6), 120);
		price=1;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=15;
		sellValue=1;
		infoText="buying cost $"+price+"\n\na tiny bud that's trying to \ngrow up";
		buyIcon=Assets.towerIcons[9];
		upgradeIcon=Assets.towerIcons[10];
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Plantlvl1(x, y);
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new Plantlvl2(x+width/2, y+height/2);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		return new Plantlvl2(0,0).getInfoText();
	}
}
