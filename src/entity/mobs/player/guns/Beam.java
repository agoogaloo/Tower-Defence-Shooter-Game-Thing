package entity.mobs.player.guns;

import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class Beam extends Gun{

	public Beam(EntityManager manager) {
		super(manager);
		
	}
	
	@Override
	protected void init() {
		reloadTime=1;
		icon=Assets.beam;
		shootAnim=new Animation(Assets.beamShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.beamBullet, 1.5,1, true));
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Beam(manager);
	}

	

}
