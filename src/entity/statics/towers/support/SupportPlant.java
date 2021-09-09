package entity.statics.towers.support;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class SupportPlant extends Tower{
	public SupportPlant(int x, int y) {
		this(x,y,null);
	}
	public SupportPlant(int x, int y,TowerSpawn spawn) {
		super(x, y, 150, 150, new Animation(Assets.supportPlant,6), 30,spawn);
		price=4;
		sellValue=6;
		damage=1;
		
		infoText="upgrade cost $"+price+"\n\npoisons enemies inside it's \nrange through the powers of \nsci-fi logic";
		
	}
	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new SupportPlant(x+width/2, y+height*2,spawn);
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
					e.giveStatusEffect(new StatusEffect(StatusType.POISON, 0.3, 30));
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
