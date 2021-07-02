package entity.statics.towers.Plant;

import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Plantlvl2 extends Tower{
	private int minTime=300, wavesToGrow=2;
	private boolean waveCounted;
	public Plantlvl2(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.plantLvl2,6), 60);
		price=2;
		sellValue=2;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=20;
		upgradeIcon=Assets.towerIcons[10];
		infoText="water cost $"+price+"\n\nwater the plant to help it \ngrow faster";
		waveCounted=!entityManager.getSpawner().waveComplete();
		
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
				if(ThreadLocalRandom.current().nextBoolean()) {
					upgrade('r',99);
				}else {
					upgrade('l',99);
				}
				
			}
		}else {
			waveCounted=false;
		}
	}

	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new Tree(x+width/2, y+height/2);	
		}else if(leftRight=='r') {
			newTower=new VinePlant(x+width/2, y+height/2);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			newTower.init();
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new Tree(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new VinePlant(0,0).getInfoText();
		}else {
			return "";
		}
	}
}
