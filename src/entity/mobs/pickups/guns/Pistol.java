package entity.mobs.pickups.guns;

import audio.AudioManager;
import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Pistol extends Gun{	
	public Pistol(EntityManager manager) {
		super(manager);
	}

	@Override
	protected void init() {
		reloadTime=15;
		icon=Assets.pistol;
		shootAnim = new Animation(Assets.pistolShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {	
			shotDelay=0;
			shootAnim.setPaused(false);
			manager.addEntity(new Bullet(x, y, aimX, aimY, Assets.yellowBullet, 5.5,40,10, true));
			AudioManager.playSound(AudioManager.pistol);
			GameState.screenShake(0.1);
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Pistol(manager);
	}

	
}
