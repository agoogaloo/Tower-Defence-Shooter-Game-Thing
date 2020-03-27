package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl1 extends Tower{
	public LaserTowerlvl1(int x,int y) {
		
		animation=new Animation(Assets.laserTowerLvl1,6);
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=30;
		sellValue=3;
		upgradeIcon=Assets.towerMenu[4];
		towerRange=new Rectangle(this.x,this.y-10,300,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x+width,y+5,x+width+1,y+5,Assets.blueLaser[1],8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	
	}
	@Override
	public int upgrade(char leftRight, int money) {
		if(money>=2) {
			entityManager.addEntity(new LaserTowerlvl2(x+width/2, y+2));
			destroy();
			return 2;
		}else {
			return 0;
		}
		
	}
	
}
