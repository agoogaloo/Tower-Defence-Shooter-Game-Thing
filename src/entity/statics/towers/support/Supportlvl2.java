package entity.statics.towers.support;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.Plant.Tree;
import entity.statics.towers.Plant.VinePlant;
import entity.statics.towers.wizard.ElectroWizardTower;
import entity.statics.towers.wizard.FireWizardTower;
import graphics.Animation;
import graphics.Assets;

public class Supportlvl2 extends Tower{
	public Supportlvl2(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.supportLvl2,6), 40);
		price=2;
		damage=5;
		sellValue=1;
		statusEffect=new StatusEffect(StatusType.WEAKENED, 1.5,3);
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
					e.giveStatusEffect(new StatusEffect(StatusType.BUFFDMG,1.5,10));
					System.out.println("eee");
				}
				
			}
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new SupportTech(x+width/2, y+height/2);	
		}else if(leftRight=='r') {
			newTower=new ElectroWizardTower(x+width/2, y+height/2);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new SupportTech(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new VinePlant(0,0).getInfoText();
		}else {
			return "";
		}
	}

	@Override
	public Tower createNew(int x, int y) {
		return new Supportlvl2(x, y);
	}
}
