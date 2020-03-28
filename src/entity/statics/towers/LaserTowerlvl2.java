package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl2 extends Tower{
	char direction;
	public LaserTowerlvl2(int x,int y, char direction) {
		//assigning the animation based off if its direction
		this.direction=direction;
		infoText="gives the tower a bit more range forward and an increased rate of fire.";
		switch (direction) {
		case 'u':
			animation=new Animation(Assets.laserTowerLvl2U,6);
			break;
		case 'l':
			animation=new Animation(Assets.laserTowerLvl2L,6);
			break;
		case 'd':
			animation=new Animation(Assets.laserTowerLvl2D,6);
			break;
		case 'r':
			animation=new Animation(Assets.laserTowerLvl2R,6);
			break;
		default:
			animation=new Animation(Assets.laserTowerLvl2R,6);
				
		}
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=20;
		sellValue=4;
		upgradeIcon=Assets.towerMenu[5];
		//making its range based off of its direction 
		//this is done seperatedly from animations so that it can use the right x,y,width,and height variables
		switch (direction) {
		case 'u':
			towerRange=new Rectangle(this.x-10+width/2,this.y+height-350,20,350); //Creates a rectangle for the towers range 
			break;
		case 'l':
			towerRange=new Rectangle(this.x-350+width,this.y-10+height/2,350,20); //Creates a rectangle for the towers range 
			break;
		case 'd':
			towerRange=new Rectangle(this.x-10+width/2,this.y,20,350); //Creates a rectangle for the towers range 
			break;
		case 'r':
			towerRange=new Rectangle(this.x,this.y-10+height/2,350,20); //Creates a rectangle for the towers range 
			break;
		default:
			towerRange=new Rectangle(this.x,this.y-10,350,20); //Creates a rectangle for the towers range 
				
		}
		

	}
	@Override
	protected void shoot() {
		switch(direction) {
		case 'u':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2,y,x+width/2,y-2,Assets.blueLaser[0],8, true));
			break;
		case 'l':
			//shoots straight left
			entityManager.addEntity(new Bullet(x,y+5,x-2,y+5,Assets.blueLaser[3],8, true));
			break;
		case 'r':
			//shoots straight right
			entityManager.addEntity(new Bullet(x+width,y+5,x+2+width,y+5,Assets.blueLaser[1],8, true));
			break;
		case 'd':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2,y+height,x+width/2,y+height+2,Assets.blueLaser[2],8, true));
			break;
		}
		
	}
	@Override
	public int upgrade(char leftRight, int money) {
		if(leftRight=='l'&&money>=13) {
			entityManager.addEntity(new MachineGunTower(x+width/2, y+2, direction));
			destroy();
			return 13;
		}else if(leftRight=='r'&&money>=5) {
			entityManager.addEntity(new BigLaserTower(x+width/2, y+2, direction));
			destroy();
			return 5;
		}
		return 0;
		
	}
	@Override
	public String hover(char leftRight) {
		if(leftRight=='l') {
			return new MachineGunTower(0,0,'r').getInfoText();
		}else if(leftRight=='r') {
			return new BigLaserTower(0,0,'r').getInfoText();	
		}
		return "";
	}
}
