package entity.statics.towers.Plant;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Tree extends Tower{
	private ArrayList<Point> seedLocs = new ArrayList<Point>();
	public Tree(int x, int y) {
		super(x, y, 150, 150, new Animation(Assets.tree,12), 60);
		price=4;
		sellValue=2;
		damage=6;
		statusEffect=StatusEffect.STUN;
		statusLength=30;
		infoText="water cost $"+price+"\n\nwater the plant to help it \ngrow into a big tree that \nshoots acorns at enemies. "
				+ "\nallowing it to plant new \nbuds every round";
		
	}
	@Override
	public Tower createNew(int x, int y) {
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
		entityManager.addEntity(new Bullet(x+width/2,y+height/2,target.getX(),target.getY(),Assets.acorn[0],5,damage
				,statusEffect,statusLength,statusLevel, true));
		seedLocs.add(new Point(target.getX(),target.getY()));
		
	}

	@Override
	public String select(char leftRight) {
		return "already completely grown";
	}
}
