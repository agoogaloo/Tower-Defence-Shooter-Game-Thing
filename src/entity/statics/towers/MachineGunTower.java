package entity.statics.towers;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class MachineGunTower extends Tower{
	char direction;
public MachineGunTower(int x,int y, char direction) {
	this.direction=direction;
	switch (direction) {
	case 'u':
		animation=new Animation(Assets.machineGunTowerU,6);
		break;
	case 'l':
		animation=new Animation(Assets.machineGunTowerL,6);
		break;
	case 'd':
		animation=new Animation(Assets.machineGunTowerD,6);
		break;
	case 'r':
		animation=new Animation(Assets.machineGunTowerR,6);
		break;
	}
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=6;
		sellValue=10;
		switch (direction) {
		case 'u':
			towerRange=new Rectangle(this.x-10+width/2,this.y+height-600,20,600); //Creates a rectangle for the towers range 
			break;
		case 'l':
			towerRange=new Rectangle(this.x-600+width,this.y-10+height/2,600,20); //Creates a rectangle for the towers range 
			break;
		case 'd':
			towerRange=new Rectangle(this.x-10+width/2,this.y,20,600); //Creates a rectangle for the towers range 
			break;
		case 'r':
			towerRange=new Rectangle(this.x,this.y-10+height/2,600,20); //Creates a rectangle for the towers range 
			break;
		default:
			towerRange=new Rectangle(this.x,this.y-10,600,20); //Creates a rectangle for the towers range 
				
		}

	}
	@Override
	protected void shoot() {
		int offset=ThreadLocalRandom.current().nextInt(0, 10);//making the bullets go slightly above and bellow its other bullets so it looks cooler
		switch(direction) {
		case 'u':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2+offset-5,y,x+width/2+offset-5,y-2,Assets.YellowLaser[1],8, true));
			break;
		case 'l':
			//shoots straight left
			entityManager.addEntity(new Bullet(x,y+offset,x-2,y+offset,Assets.YellowLaser[0],8, true));
			break;
		case 'r':
			//shoots straight right
			entityManager.addEntity(new Bullet(x+width,y+offset,x+2+width,y+offset,Assets.YellowLaser[0],8, true));
			break;
		case 'd':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2+offset-5,y+height,x+width/2+offset-5,y+height+2,Assets.YellowLaser[1],8, true));
			break;
		}
		}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

}
