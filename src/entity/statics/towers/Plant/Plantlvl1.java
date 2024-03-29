package entity.statics.towers.Plant;

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

public class Plantlvl1 extends Tower{
	private int minTime=300, wavesToGrow=2;
	private boolean waveCounted;
	public Plantlvl1(int x, int y) {
		this(x,y,null);
	}
	public Plantlvl1(int x, int y,TowerSpawn spawn) {
		super(x, y, 75, 75, new Animation(Assets.plantLvl1,6), 65,spawn);
		price=2;
		statusEffect=new StatusEffect(StatusType.STUN, 1, 15);
		sellValue=1;
		infoText="-planting cost $"+price+"- \n \n a tiny bud that will slowly grow up. It occasionally stuns enemies around it";
		buyIcon=Assets.towerIcons[9];
		upgradeIcon=Assets.towerIcons[10];
		waveCounted=!entityManager.getSpawner().waveComplete();
		
		
	}
	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		
		return new Plantlvl1(x, y,spawn);
		
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
				upgrade('l',99);
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
			new InstantEffect(15, new Straight(new CircleSpawn(x+width/2,y+height/2
					,(int)towerRange.width/2),0.25),new ImgShape(Assets.greenStars,50, 25),true);
			}
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
		
		
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new Plantlvl2(x+width/2, y+height/2,spawn);
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
		return new Plantlvl2(0,0).getInfoText();
	}
}
