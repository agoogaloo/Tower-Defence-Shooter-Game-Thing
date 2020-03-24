package entity.statics.towers;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class BigLaserTower extends Tower{
public BigLaserTower(int x,int y) {
		animation=new Animation(Assets.bigLaserTower,6);
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=420;
		sellValue=6;
		towerRange=new Rectangle(this.x,this.y,600,11); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		for(int i=0;i<=75;i++) {
			entityManager.addEntity(new Bullet(x+width,y+i/5-5,x+1+width,y+i/5-5,4,5, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

}
