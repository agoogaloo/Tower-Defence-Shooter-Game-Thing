package entity.statics.towers.wizard;

import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class FireWizardTower extends Tower{
	public FireWizardTower(int x, int y) {
		this(x,y,null);
	}
	public FireWizardTower(int x, int y, TowerSpawn spawn) {
		super(x, y, 125, 125, new Animation(Assets.fireWizardTower,6), 30, spawn);
		sellValue=5;
		price=7;
		damage=1;
		statusEffect=new StatusEffect(StatusType.BURN, 0.7, 30);
		infoText="upgrade cost $"+price+"\n\ngives the wizard fire \npowers letting him burn \nenemies to deal damage over \ntime ";
		
	}
	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		return new FireWizardTower(x+width/2, y+height*2,spawn);
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
