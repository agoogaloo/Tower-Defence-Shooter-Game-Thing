package entity.statics.towers.support;

import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class SupportTech extends Tower{
	public SupportTech(int x, int y) {
		super(x, y, 125, 125, new Animation(Assets.supportTech,6), 30);
		sellValue=5;
		price=0;
		damage=1;
		//statusEffect=new StatusEffect(null, x, y);
	
		infoText="upgrade cost $"+price+"\n\ngives the wizard fire \npowers letting him burn \nenemies to deal damage over \ntime ";
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new SupportTech(x+width/2, y+height*2);
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	
	@Override
	public String select(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
}
