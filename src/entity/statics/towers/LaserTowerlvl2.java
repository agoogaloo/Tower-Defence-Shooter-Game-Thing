package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl2 extends Tower{
	public LaserTowerlvl2(int x,int y) {
		this.x = x;
		this.y = y;
		animation=new Animation(Assets.laserTower,6);
		this.reloadTime=15;
		towerRange=new Rectangle(x,y-10,700,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x,y,x+2,y,4,8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 

	}
}
