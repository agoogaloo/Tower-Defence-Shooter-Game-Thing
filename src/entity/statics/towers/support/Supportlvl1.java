package entity.statics.towers.support;

import java.awt.Color;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;
import graphics.particles.ParticleEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.CircleSpawn;
import graphics.particles.shapes.ImgShape;
import graphics.particles.shapes.RectangleShape;
import graphics.particles.shapes.colourers.Timed;

public class Supportlvl1 extends Tower{
	//private int particleTimer = 0, particleTime=5;
	public Supportlvl1(int x, int y) {
		super(x, y, 100, 100, new Animation(Assets.supportLvl1,6),0);
		price=2;
		damage=0;
		statusEffect=new StatusEffect(StatusType.WEAKENED, 1.5,3);
		
		sellValue=1;
		infoText="weakens any enemies in its \nrange, making them take \nmore damage";
		buyIcon=Assets.towerIcons[11];
		upgradeIcon=Assets.towerIcons[12];
		
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
		Tower newTower=new Supportlvl2(x+width/2, y+height/2);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;
	}

	@Override
	public String select(char leftRight) {
		return new Supportlvl2(0,0).getInfoText();
	}

	@Override
	public Tower createNew(int x, int y) {
		return new Supportlvl1(x, y);
	}
}
