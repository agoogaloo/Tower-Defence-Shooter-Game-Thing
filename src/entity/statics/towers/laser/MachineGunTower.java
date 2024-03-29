package entity.statics.towers.laser;

import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class MachineGunTower extends Tower{
	char direction;
	public MachineGunTower(int x,int y,TowerSpawn spawn, char direction) {
		this.direction=direction;
		this.spawn=spawn;
		price=8;
		damage=3;
		infoText="-upgrade cost $"+price+"- \n \n can shoot super duper fast";
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
			reloadTime=5;
			sellValue=10;
			
			int range=400;
			switch (direction) {
			case 'u':
				towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y+height-range,20,range); //Creates a rectangle for the towers range 
				break;
			case 'l':
				towerRange=new Ellipse2D.Float(this.x-range+width,this.y-13+height/2,range,21); //Creates a rectangle for the towers range 
				break;
			case 'd':
				towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y,20,range); //Creates a rectangle for the towers range 
				break;
			case 'r':
				towerRange=new Ellipse2D.Float(this.x,this.y-13+height/2,range,21); //Creates a rectangle for the towers range 
				break;
			default:
				towerRange=new Ellipse2D.Float(this.x,this.y-10,range,20); //Creates a rectangle for the towers range 
					
			}
	
		}

	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new MachineGunTower(x+width*2, y+2,spawn,direction);
	}
	@Override
	protected void shoot() {
		int offset=ThreadLocalRandom.current().nextInt(0, 10);//making the bullets go slightly above and bellow its other bullets so it looks cooler
		switch(direction) {
		case 'u':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2+offset-5,y,x+width/2+offset-5,y-2,Assets.YellowLaser[1],8,55,damage, true));
			break;
		case 'l':
			//shoots straight left
			entityManager.addEntity(new Bullet(x,y+offset,x-2,y+offset,Assets.YellowLaser[0],8,55,damage, true));
			break;
		case 'r':
			//shoots straight right
			entityManager.addEntity(new Bullet(x+width,y+offset,x+2+width,y+offset,Assets.YellowLaser[0],8,55,damage, true));
			break;
		case 'd':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2+offset-5,y+height,x+width/2+offset-5,y+height+2,Assets.YellowLaser[1],8,55,damage, true));
			break;
		}
		}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	@Override
	public String select(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}
}
