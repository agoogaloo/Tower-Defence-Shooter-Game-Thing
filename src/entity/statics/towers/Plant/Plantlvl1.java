package entity.statics.towers.Plant;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Plantlvl1 extends Tower{
	private int minTime=300, wavesToGrow=2;
	private boolean waveCounted;
	public Plantlvl1(int x, int y) {
		super(x, y, 75, 75, new Animation(Assets.plantLvl1,6), 90);
		price=2;
		statusEffect=StatusEffect.STUN;
		statusLength=15;
		sellValue=1;
		infoText="planting cost $"+price+"\n\na tiny bud that's waiting to \ngrow up. It occasionally \nstuns enemies around it";
		buyIcon=Assets.towerIcons[8];
		upgradeIcon=Assets.towerIcons[9];
		waveCounted=!entityManager.getSpawner().waveComplete();
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Plantlvl1(x, y);
	}
	
	@Override
	public void update() {
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
		animation.update();
		if (shotDelay<=0) { //If attack is true and it's been 60 frames since last shot, shoot again
			shotDelay = reloadTime; //Ensures the tower can't rapidly shoot
			for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
				if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
						e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy) {
					e.giveStatusEffect(statusEffect, 1, statusLength);
					
				}
			}
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
		
		
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
	public String select(char leftRight) {
		return new Plantlvl2(0,0).getInfoText();
	}
}
