package entity.mobs.player.guns;

import java.awt.image.BufferedImage;

import entity.EntityManager;
import graphics.Animation;
import graphics.Assets;

public abstract class Gun {
	protected EntityManager manager;
	protected int reloadTime=99, shotDelay;
	protected BufferedImage icon = Assets.pistol;
	protected Animation shootAnim = new Animation(Assets.pistolShoot);
	
	public Gun(EntityManager manager) {
		this.manager=manager;
		init();
		shootAnim.setLooping(false);
		shootAnim.setPaused(true);
	}
	protected abstract void init();
	
	public void update() {
		shotDelay++;
		shootAnim.update();		
	}
	
	public abstract void shoot(int x, int y, int aimX, int aimY);
	public abstract Gun createNew(EntityManager manager) ;
	
	public BufferedImage getIcon() {
		if(shootAnim.isPaused()) {
			return icon;
		}
		return shootAnim.getCurrentFrame();
	}

}
