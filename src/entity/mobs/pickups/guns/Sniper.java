package entity.mobs.pickups.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

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
			GameState.screenShake(0.3);

			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.yellowBullet, 15,30,60, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Sniper(manager);
	}

}
