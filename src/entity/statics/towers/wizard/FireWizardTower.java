package entity.statics.towers.wizard;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class FireWizardTower extends Tower{
	public FireWizardTower(int x, int y) {
		this(x,y,null);
	}
	public FireWizardTower(int x, int y, TowerSpawn spawn) {
		super(x, y, 125, 125, new Animation(Assets.fireWizardTower,6), 40, spawn);
		sellValue=5;
		price=7;
		damage=1;
		statusEffect=new StatusEffect(StatusType.BURN, 0.7, 30);
		infoText="-upgrade cost $"+price+"- \n \n gives the wizard fire powers letting him burn enemies to deal damage over time ";
		
	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new FireBullet(x+width/2,y+height/2,target.getX()+target.getWidth()/2,
				target.getY()+target.getHeight()/2,Assets.yellowBullet,8,30,
				damage*buffDamage, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	}
	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		return new FireWizardTower(x+width/2, y+height*2,spawn);
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	
	@Override
	public String select(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
	
	private class FireBullet extends Bullet{
		public FireBullet(int startX, int startY, double targetX, double targetY, BufferedImage pic, double speed,
				int time,double damage, boolean friendly) {
			super(startX, startY, targetX, targetY, pic, speed, time,damage,new StatusEffect(StatusType.BURN, 0.75, 80), friendly);
							
		}
		@Override
		public void update() {
			super.update();
			new InstantEffect(1, new Straight(new RectangleSpawner(x, y, width, height), -90, 3, 0.25),
					new ShrinkOvalParticle(new Timed(new Color(ThreadLocalRandom.current().nextInt(225, 255),
							ThreadLocalRandom.current().nextInt(120, 140),0), 120,30), 4,5,0.2,0.3), true);
		}
		
		@Override
		public void destroy() {
			if(killed) {
				return;
			}
			killed=true;
			new InstantEffect(14, new Straight(new Point(x+width/2,y+height/2),1), 
					new OvalParticle(2, new Timed(new Color(ThreadLocalRandom.current().nextInt(225, 255),
							ThreadLocalRandom.current().nextInt(120, 140),0), 15,5)), false);
		}
		
	}
}
