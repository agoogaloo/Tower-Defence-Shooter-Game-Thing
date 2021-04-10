package entity.statics.towers.Plant;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Plantlvl2 extends Tower{
	private int minTime=300, wavesToGrow=2;
	private boolean waveCounted;
	public Plantlvl2(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.plantLvl2,6), 60);
		price=3;
		sellValue=1;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=20;
		upgradeIcon=Assets.towerIcons[11];
		infoText="water cost $"+price+"\n\nwater the plant to help it \ngrow faster";
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Plantlvl2(x+width*2, y+height*2);
	}
	@Override
	public void update() {
		super.update();
		minTime--;
		if(entityManager.getSpawner().waveComplete()) {
			if(!waveCounted) {
				waveCounted=true;
				wavesToGrow--;
			}
			if(wavesToGrow==0&&minTime<=0) {
				upgrade('l',99);
			}
		}else {
			waveCounted=false;
		}
	}

	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new Tree(x+width/2, y+height/2);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		if(leftRight=='l') {
			return new Tree(0,0).getInfoText();
		}else if(leftRight=='r') {
			return "not implemented yet";
		}else {
			return "";
		}
	}
}
