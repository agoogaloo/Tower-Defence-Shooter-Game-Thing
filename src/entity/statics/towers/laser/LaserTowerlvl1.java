package entity.statics.towers.laser;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import entity.mobs.Bullet;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class LaserTowerlvl1 extends Tower{
	char direction;
	public LaserTowerlvl1(int x,int y,char direction) {
		this.direction=direction;
		price=5;
		infoText="buying cost $"+price+"\n\nA tower that can only \nshoot directly forward \nbut very far at an average \nrate. It "
				+ "will be facing the \nsame direction you are \nwhen you place it";
		
		switch (direction) {
		case 'u':
			animation=new Animation(Assets.laserTowerLvl1U,6);
			break;
		case 'l':
			animation=new Animation(Assets.laserTowerLvl1L,6);
			break;
		case 'd':
			animation=new Animation(Assets.laserTowerLvl1D,6);
			break;
		case 'r':
			animation=new Animation(Assets.laserTowerLvl1R,6);
			break;
		}
		
		width=animation.getCurrentFrame().getWidth();
		height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
		this.x = x-width/2;
		this.y = y-2;
		updateBounds();
		reloadTime=30;
		sellValue=3;
		upgradeIcon=Assets.towerMenu[4];
		
		switch (direction) {
		case 'u':
			towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y+height-300,20,300); //Creates a rectangle for the towers range 
			break;
		case 'l':
			towerRange=new Ellipse2D.Float(this.x-300+width,this.y-14+height/2,300,20); //Creates a rectangle for the towers range 
			break;
		case 'd':
			towerRange=new Ellipse2D.Float(this.x-10+width/2,this.y,20,300); //Creates a rectangle for the towers range 
			break;
		case 'r':
			towerRange=new Ellipse2D.Float(this.x,this.y-14+height/2,300,20); //Creates a rectangle for the towers range 
			break;
		default:
			towerRange=new Ellipse2D.Float(this.x,this.y-10,300,20); //Creates a rectangle for the towers range 
				
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
			entityManager.addEntity(new Bullet(x,y+3,x-2,y+3,Assets.blueLaser[3],8, true));
			break;
		case 'r':
			//shoots straight right
			entityManager.addEntity(new Bullet(x+width,y+3,x+2+width,y+3,Assets.blueLaser[1],8, true));
			break;
		case 'd':
			//shoots straight up
			entityManager.addEntity(new Bullet(x+width/2,y+height,x+width/2,y+height+2,Assets.blueLaser[2],8, true));
			break;
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		Tower newTower=new LaserTowerlvl2(x+width/2, y+2,direction);
		if(money>=newTower.getPrice()) {
			entityManager.addEntity(newTower);
			destroy();
			return newTower.getPrice();
		}
		return 0;		
	}
	@Override
	public String hover(char leftRight) {
		return new LaserTowerlvl2(0, 0, 'r').getInfoText();
	}
	
}
