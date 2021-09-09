package entity.statics.towers.support;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class Supportlvl2 extends Tower{
	public Supportlvl2(int x, int y) {
		this(x,y,null);
	}
	public Supportlvl2(int x, int y,TowerSpawn spawn) {
		super(x, y, 110, 110, new Animation(Assets.supportLvl2,6), 40,spawn);
		price=5;
		damage=5;
		sellValue=4;
		statusEffect=new StatusEffect(StatusType.WEAKENED, 1.2,3);
		infoText="buying cost $"+price+"\n\nbuffs all towers in its \nrange, letting them to deal \nmore damage ";
		upgradeIcon=Assets.towerIcons[13];
		splitUpgrades=true;
		
	}
	
	@Override
	public void update() {
		updateEffects();
		animation.update();
		
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			
			if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())) {
				if(e instanceof Enemy) {
					e.giveStatusEffect(statusEffect.copy());
				}else if(e instanceof Tower&& e!=this) {
					e.giveStatusEffect(new StatusEffect(StatusType.BUFFDMG,1.25,10));
				}
				
			}
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new SupportTech(x+width/2, y+height/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new SupportPlant(x+width/2, y+height/2,spawn);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			newTower.init();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new SupportTech(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new SupportPlant(0,0).getInfoText();
		}else {
			return "";
		}
	}

	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new Supportlvl2(x, y,spawn);
	}
}
