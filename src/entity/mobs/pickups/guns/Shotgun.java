package entity.mobs.pickups.guns;

import java.util.concurrent.ThreadLocalRandom;

import audio.AudioManager;
import entity.EntityManager;
import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Shotgun extends Gun{	
	private final int shots = 12;
	public Shotgun(EntityManager manager) {
		super(manager);
	}

	@Override
	protected void init() {
		reloadTime=55;
		icon=Assets.shotgun;
		shootAnim = new Animation(Assets.shotGunShoot);
	}

	@Override
	public void shoot(int x, int y, int aimX, int aimY) {
		if (shotDelay >= reloadTime) {	
			shotDelay=0;
			shootAnim.setPaused(false);

			double hyp = Math.sqrt(Math.pow(x-aimX, 2)+Math.pow(y-aimY, 2)); 
			double mouseAngle = Math.acos((aimX-x)/hyp);
			if(y-aimY>0){
				mouseAngle*=-1;
			}
			System.out.println(aimX-x+", "+(y-aimY)+", "+hyp+", "+mouseAngle);
			for(int i=0;i<shots;i++){
				double changeAngle = ThreadLocalRandom.current().nextDouble(Math.PI/3)-Math.PI/3/2;
				double speed = ThreadLocalRandom.current().nextDouble(5,8);
				
				double newX = x+Math.cos(changeAngle+mouseAngle)*5;
				double newY = y+Math.sin(changeAngle+mouseAngle)*5;
				manager.addEntity(new Bullet(x, y, newX,newY, Assets.yellowBullet, speed,25,6, true));
				//System.out.println(x+", "+y+". "+newX+", "+newY);
			}
			//manager.addEntity(new Bullet(x, y, aimx, normalY+offsetY, Assets.yellowBullet, 5.5,40,10, true));
			AudioManager.playSound(AudioManager.cannon);
			GameState.screenShake(0.3);
		}
		
	}

	@Override
	public Gun createNew(EntityManager manager) {
		return new Shotgun(manager);
	}

	
}
