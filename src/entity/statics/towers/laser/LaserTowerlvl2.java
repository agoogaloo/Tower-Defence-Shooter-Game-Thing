package entity.statics.towers.laser;

import java.awt.geom.Ellipse2D;

import entity.mobs.Bullet;
import entity.statics.towers.Tower;
import entity.statics.towers.wizard.ElectroWizardTower;
import entity.statics.towers.wizard.FireWizardTower;
import entity.statics.towers.wizard.WizardTowerlvl1;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl2 extends Tower{
	char direction;
	public LaserTowerlvl2(int x,int y, char direction) {
		//assigning the animation based off if its direction
		this.direction=direction;
		price=2;
		infoText="upgrade cost $"+price+"\n\ngives the tower a bit more \nrange and an increased"
				+ " rate \nof fire.";
		upgradeIcon=Assets.towerIcons[7];
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
		splitUpgrades=true;
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=20;
		sellValue=4;
		
		//making its range based off of its direction 
		//this is done seperatedly from animations so that it can use the right x,y,width,and height variables
		switch (direction) {
		case 'u':
			towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y+height-350,20,350); //Creates a rectangle for the towers range 
			break;
		case 'l':
			towerRange=new Ellipse2D.Float(this.x-350+width,this.y-14+height/2,350,20); //Creates a rectangle for the towers range 
			break;
		case 'd':
			towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y,20,350); //Creates a rectangle for the towers range 
			break;
		case 'r':
			towerRange=new Ellipse2D.Float(this.x,this.y-14+height/2,350,20); //Creates a rectangle for the towers range 
			break;
		default:
			towerRange=new Ellipse2D.Float(this.x,this.y-10,350,20); //Creates a rectangle for the towers range 
				
		}
		

	}

	@Override
	public Tower createNew(int x, int y) {
		return new LaserTowerlvl2(x, y,direction);
	}
	@Override
	protected void shoot() {
		switch(direction) {
		case 'u':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2,y,x+width/2,y-2,Assets.blueLaser[0],8,7, true));
			break;
		case 'l':
			//shoots straight left
			entityManager.addEntity(new Bullet(x,y+3,x-2,y+3,Assets.blueLaser[3],8,7, true));
			break;
		case 'r':
			//shoots straight right
			entityManager.addEntity(new Bullet(x+width,y+3,x+2+width,y+3,Assets.blueLaser[1],8,7, true));
			break;
		case 'd':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2,y+height,x+width/2,y+height+2,Assets.blueLaser[2],8,7, true));
			break;
		}
		
	}
	@Override
	public int upgrade(char leftRight, int money) {
		
		Tower newTower=null;
		if(leftRight=='l') {
			newTower=new MachineGunTower(x+width/2, y+2, direction);	
		}else if(leftRight=='r') {
			newTower=new BigLaserTower(x+width/2, y+2, direction);
		}
	
		if(newTower!=null&&money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;		
	}
	@Override
	public String select(char leftRight) {
		if(leftRight=='l') {
			return new MachineGunTower(0,0,'r').getInfoText();
		}else if(leftRight=='r') {
			return new BigLaserTower(0,0,'r').getInfoText();	
		}
		return "";
	}
}
