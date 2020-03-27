package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl2 extends Tower{
	public LaserTowerlvl2(int x,int y) {
		animation=new Animation(Assets.laserTowerLvl2R,6);
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=20;
		sellValue=4;
		upgradeIcon=Assets.towerMenu[5];
		towerRange=new Rectangle(this.x,this.y-10,350,20); //Creates a rectangle for the towers range 

	}
	@Override
	protected void shoot() {
		entityManager.addEntity(new Bullet(x+width,y+5,x+2+width,y+5,Assets.blueLaser[1],8, true)); //Creates a friendly bullet that goes towards the enemy entity detected 

	}
	@Override
	public int upgrade(char leftRight, int money) {
		if(leftRight=='l'&&money>=13) {
			entityManager.addEntity(new MachineGunTower(x+width/2, y+2));
			destroy();
			return 13;
		}else if(leftRight=='r'&&money>=5) {
			entityManager.addEntity(new BigLaserTower(x+width/2, y+2));
			destroy();
			return 5;
		}
		return 0;
		
	}
}
