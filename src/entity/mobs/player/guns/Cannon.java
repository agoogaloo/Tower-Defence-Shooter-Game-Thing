package entity.mobs.player.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import entity.mobs.enemy.StatusEffect;
import graphics.Animation;
import graphics.Assets;

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
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.yellowBullet, 7,15,StatusEffect.STUN,60,1, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Cannon(manager);
	}

	
}
