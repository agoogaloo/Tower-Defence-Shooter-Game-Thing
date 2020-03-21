package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl2 extends Tower{
	public LaserTowerlvl2(int x,int y) {
		animation=new Animation(Assets.laserTowerLvl2,6);
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=15;
		sellValue=4;
		towerRange=new Rectangle(this.x,this.y-10,700,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x,y,x+2,y,4,8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 

	}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
}
