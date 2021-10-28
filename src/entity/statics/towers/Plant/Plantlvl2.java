package entity.statics.towers.Plant;

import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.CircleSpawn;
import graphics.particles.shapes.ImgShape;

public class Plantlvl2 extends Tower{
	private int minTime=300, wavesToGrow=2;
	private boolean waveCounted;
	
	public Plantlvl2(int x, int y) {
		this(x,y,null);
	}
	public Plantlvl2(int x, int y,TowerSpawn spawn) {
		super(x, y, 110, 110, new Animation(Assets.plantLvl2,6), 55,spawn);
		price=2;
		sellValue=2;
		statusEffect=new StatusEffect(StatusType.STUN, 1, 20);
		upgradeIcon=Assets.towerIcons[10];
		splitUpgrades=true;
		infoText="-water cost $"+price+"- \n \n water the plant to help it grow faster";
		waveCounted=!entityManager.getSpawner().waveComplete();
		
	}
	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new Plantlvl2(x+width*2, y+height*2,spawn);
	}
	@Override
	public void update() {
		updateEffects();
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
		
		
		animation.update();
		if (shotDelay<=0) { //If attack is true and it's been 60 frames since last shot, shoot again
			shotDelay = reloadTime; //Ensures the tower can't rapidly shoot
			boolean stunned = false;
			for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
				if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
						e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy) {
					e.giveStatusEffect(statusEffect);
					stunned=true;
				}
			}
			if(stunned) {
			new InstantEffect(20, new Straight(new CircleSpawn(x+width/2,y+height/2
					,(int)towerRange.width/2),0.25),new ImgShape(Assets.greenStars,50, 25),true);
			}
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
	}

	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new Tree(x+width/2, y+height/2,spawn);	
		}else if(leftRight=='r') {
			newTower=new VinePlant(x+width/2, y+height/2,spawn);
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
			return new Tree(0,0).getInfoText();
		}else if(leftRight=='r') {
			return new VinePlant(0,0).getInfoText();
		}else {
			return "";
		}
	}
}
