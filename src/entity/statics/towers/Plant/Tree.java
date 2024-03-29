package entity.statics.towers.Plant;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class Tree extends Tower{
	private ArrayList<Point> seedLocs = new ArrayList<Point>();
	public Tree(int x, int y) {
		this(x,y,null);
	}
	public Tree(int x, int y,TowerSpawn spawn) {
		super(x, y, 150, 150, new Animation(Assets.tree,12), 60,spawn);
		price=3;
		sellValue=3;
		damage=6;
		statusEffect= new StatusEffect(StatusType.STUN, 1, 10);
		infoText="-water cost $"+price+"- \n \n grows into a big tree that shoots acorns at enemies, which will into new buds";
		
	}
	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new Tree(x+width*2, y+height*2);
	}

	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	public void update() {
		super.update();
		if(entityManager.getSpawner().waveComplete()) {
			if(seedLocs.size()>0) {
				Point p = seedLocs.get(ThreadLocalRandom.current().nextInt(0, seedLocs.size()));
				entityManager.addEntity(new Plantlvl1(p.x,p.y));
				seedLocs.clear();
			}
		}
	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x+width/2,y+height/2,target.getX(),target.getY(),Assets.acorn[0],5,100,damage
				,statusEffect, true));
		seedLocs.add(new Point(target.getX(),target.getY()));
		
	}

	@Override
	public String select(char leftRight) {
		return "already fully grown";
	}
}
