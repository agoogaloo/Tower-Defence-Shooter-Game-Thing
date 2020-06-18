package entity.statics.towers.laser;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class BigLaserTower extends Tower{
	char direction;
	int beamlength=10, shootTime=11;//how long the mega laser will and has been shooting for
	
public BigLaserTower(int x,int y, char direction) {
	this.direction=direction;
	price=5;
	infoText="upgrade cost $"+price+"\n\ncan now shoot a huge laser \nbeam of doom after a long \nchrarging time";
	switch (direction) {
	case 'u':
		animation=new Animation(Assets.bigLaserTowerU,6);
		break;
	case 'l':
		animation=new Animation(Assets.bigLaserTowerL,6);
		break;
	case 'd':
		animation=new Animation(Assets.bigLaserTowerD,6);
		break;
	case 'r':
		animation=new Animation(Assets.bigLaserTowerR,6);
		break;
	}
	
	width=animation.getCurrentFrame().getWidth();
	height=animation.getCurrentFrame().getHeight();//setting the size of the tower to the size of the animation
	this.x = x-width/2;
	this.y = y-2;
	updateBounds();
	reloadTime=450;
	sellValue=6;
	switch (direction) {
	case 'u':
		towerRange=new Rectangle(this.x-5+width/2,this.y-600,10,600); //Creates a rectangle for the towers range 
		break;
	case 'l':
		towerRange=new Rectangle(this.x-600,this.y,600,10); //Creates a rectangle for the towers range 
		break;
	case 'd':
		towerRange=new Rectangle(this.x-5+width/2,this.y+height,10,600); //Creates a rectangle for the towers range 
		break;
	case 'r':
		towerRange=new Rectangle(this.x+width,this.y,600,10); //Creates a rectangle for the towers range 
		break;
	default:
		towerRange=new Rectangle(this.x,this.y-5,600,10); //Creates a rectangle for the towers range 
			
	}

	}
	@Override
	protected void shoot() {
		switch(direction) {
		case 'u':
			entityManager.addEntity(new MegaLaser(x+width/2, y, 30, direction));
			break;
		case 'r':
			entityManager.addEntity(new MegaLaser(x+width, y+5, 30, direction));
			break;
		case 'd':
			entityManager.addEntity(new MegaLaser(x+width/2, y+height, 30, direction));
			break;
		case 'l':
			entityManager.addEntity(new MegaLaser(x, y+5, 30, direction));
			break;
		}
		
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	@Override
	public String hover(char leftRight) {
		return "already at full power";//saying that you cant upgrade it anymore when you try to upgrade it
	}

}
