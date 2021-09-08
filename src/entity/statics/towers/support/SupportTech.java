package entity.statics.towers.support;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class SupportTech extends Tower{
	public SupportTech(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.supportTech,6), 30);
		price=8;
		sellValue=8;
		damage=1;
		infoText="upgrade cost $"+price+"\n\njams the guns of enemies in \nthe tower's range so they \nstop shooting at you";
		
		
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new SupportTech(x+width/2, y+height*2);
	}
	@Override
	public void update() {
		updateEffects();
		animation.update();
		
		for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
			
			if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())) {
				if(e instanceof Enemy) {
					e.giveStatusEffect(new StatusEffect(StatusType.WEAKENED, 1.2,3));
					e.giveStatusEffect(new StatusEffect(StatusType.JAMMED, 1, 10));
				}else if(e instanceof Tower&& e!=this) {
					e.giveStatusEffect(new StatusEffect(StatusType.BUFFDMG,1.25,10));
				}
				
			}
		}
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