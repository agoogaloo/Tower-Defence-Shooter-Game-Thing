package entity.statics.towers.Plant;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Plantlvl2 extends Tower{
	public Plantlvl2(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.plantLvl2,6), 60);
		price=1;
		sellValue=2;
		damage=6;
		statusEffect=StatusEffect.STUN;
		statusLength=30;
		infoText="upgrade cost $"+price+"\n\nwater the plant to help it \ngrow";
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Plantlvl2(x+width*2, y+height*2);
	}

	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		return "coming sometime";
	}
}
