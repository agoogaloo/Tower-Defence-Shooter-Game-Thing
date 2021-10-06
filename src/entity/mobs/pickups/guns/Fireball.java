package entity.mobs.pickups.guns;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.EntityManager;
import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import graphics.Animation;
import graphics.Assets;
import graphics.particles.EffectOverTime;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.RectangleShape;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class Fireball extends Gun{	
	public Fireball(EntityManager manager) {
		super(manager);
	}

	@Override
	protected void init() {
		reloadTime=30;
		icon=Assets.fireball;
		shootAnim = new Animation(Assets.fireballShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {	
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new FireBullet(x, y, aimX, aimY, Assets.yellowBullet, 5.5,50, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Fireball(manager);
	}
	
	private class FireBullet extends Bullet{
		public FireBullet(int startX, int startY, double targetX, double targetY, BufferedImage pic, double speed,
				int time, boolean friendly) {
			super(startX, startY, targetX, targetY, pic, speed, time,10,new StatusEffect(StatusType.BURN, 0.75, 100), friendly);
							
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
