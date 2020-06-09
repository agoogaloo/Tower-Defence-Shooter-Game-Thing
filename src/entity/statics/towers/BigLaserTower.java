package entity.statics.towers;

import java.awt.Rectangle;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class BigLaserTower extends Tower{
	char direction;
	int beamlength=10, shootTime=11;//how long the mega laser will and has been shooting for
	
public BigLaserTower(int x,int y, char direction) {
	this.direction=direction;
	price=5;
	infoText="upgrade cost $"+price+"\n\ncan now shoot a big laser \n(actually just 75 normal \nones stacked on top of each \nother but close enough)"
			+ " \nafter a long chrarging \ntime";
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
		reloadTime=360;
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
		//setting shootTime to 0 so that the beam will start
		shootTime=0;
	}
	@Override
	public void update() {
		super.update();
		if(shootTime<=beamlength) {
			for(int i=0;i<=20;i++) {
				switch(direction) {
				case 'u':
					//shoots straight up
					entityManager.addEntity(new Bullet(x+width/2+i/2-5,y+10,x+width/2+i/2-5,y,Assets.blueLaser[0],8, true));
					break;
				case 'l':
					//shoots straight left
					entityManager.addEntity(new Bullet(x+10,y+i/2,x,y+i/2,Assets.blueLaser[3],8, true));
					break;
				case 'r':
					//shoots straight right
					entityManager.addEntity(new Bullet(x+width-5,y+i/2,x+width,y+i/2,Assets.blueLaser[1],8, true));
					break;
				case 'd':
					//shoots straight down
					entityManager.addEntity(new Bullet(x+width/2+i/2-5,y+height-5,x+width/2+i/2-5,y+height,Assets.blueLaser[2],8, true));
					break;
				}
			}
		}
		shootTime++;
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
