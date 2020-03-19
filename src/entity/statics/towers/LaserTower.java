package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTower extends Tower{
	public LaserTower(int x,int y) {
		this.x = x;
		this.y = y;
		animation=new Animation(Assets.laserTower,6);
		this.reloadTime=20;
		towerRange=new Rectangle(x,y-10,300,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x,y,x+2,y,4,8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 

	}
}
