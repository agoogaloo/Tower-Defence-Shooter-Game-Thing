package entity.mobs.pickups.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;

public class Cannon extends Gun{	
	public Cannon(EntityManager manager) {
		super(manager);
	}

	@Override
	protected void init() {
		reloadTime=120;
		icon=Assets.cannon;
		shootAnim = new Animation(Assets.cannonShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {	
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.cannonBullet, 8,60,30,new StatusEffect(StatusType.STUN,1,60), true));
			GameState.screenShake(0.5);

			new InstantEffect(6, new Straight(new Point(x,y),0.6), 
					new ShrinkOvalParticle(new Timed(30),6,0.2), true);
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Cannon(manager);
	}

	
}
