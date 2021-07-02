package entity.mobs.player.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class Sniper extends Gun{
	public Sniper(EntityManager manager) {
		super(manager);
	}

	@Override
	protected void init() {
		reloadTime=50;
		icon=Assets.sniper;
		shootAnim = new Animation(Assets.sniperShoot);
	}
	

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {	
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.yellowBullet, 15,60, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Sniper(manager);
	}

}
