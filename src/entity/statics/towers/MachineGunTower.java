package entity.statics.towers;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class MachineGunTower extends Tower{
public MachineGunTower(int x,int y) {
		animation=new Animation(Assets.machineGunTower,6);
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=6;
		sellValue=10;
		towerRange=new Rectangle(this.x,this.y-10,600,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		int offset=ThreadLocalRandom.current().nextInt(1, 9);//making the bullets go slightly above and bellow its other bullets so it looks cooler
		entityManager.addEntity(new Bullet(x+width,y+offset,x+width+1,y+offset,6,10, true)); //Creates a friendly bullet that goes towards the enemy entity detected 
	}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

}
