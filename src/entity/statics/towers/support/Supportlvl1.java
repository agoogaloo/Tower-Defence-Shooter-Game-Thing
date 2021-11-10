package entity.statics.towers.support;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class Supportlvl1 extends Tower{
	
	public Supportlvl1(int x, int y) {
		this(x,y,null);
	}
	public Supportlvl1(int x, int y,TowerSpawn spawn) {
		super(x, y, 110, 110, new Animation(Assets.supportLvl1,6),0,spawn);
		price=3;
		damage=0;
		statusEffect=new StatusEffect(StatusType.WEAKENED, 1.2,3);
		
		sellValue=2;
		infoText="-buying cost $"+price+"- \n \n weakens any enemies in its range, making them take more damage";
		buyIcon=Assets.towerIcons[12];
		upgradeIcon=Assets.towerIcons[13];
		
	}
	@Override
	public void update() {
		updateEffects();
		animation.update();
		
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy) {
				e.giveStatusEffect(statusEffect.copy());	
			}
		}
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new Supportlvl2(x+width/2, y+height/2,spawn);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			newTower.init();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return new Supportlvl2(0,0).getInfoText();
	}

	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new Supportlvl1(x, y,spawn);
	}
}
