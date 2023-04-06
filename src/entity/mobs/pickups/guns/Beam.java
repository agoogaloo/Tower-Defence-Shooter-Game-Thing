package entity.mobs.pickups.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Beam extends Gun{

	public Beam(EntityManager manager) {
		super(manager);
		
	}
	
	@Override
	protected void init() {
		reloadTime=2;
		icon=Assets.beam;
		shootAnim=new Animation(Assets.beamShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.beamBullet, 1.25,150,1, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Beam(manager);
	}

	

}
