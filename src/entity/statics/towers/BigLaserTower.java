package entity.statics.towers;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Bullet;
import graphics.Animation;
import graphics.Assets;

public class BigLaserTower extends Tower{
	char direction;
public BigLaserTower(int x,int y, char direction) {
	this.direction=direction;
	infoText="can now shoot a big laser (actually just 75 normal ones stacked on top of each other but close enough)"
			+ " after a long chrarging time";
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
		reloadTime=420;
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
		for(int i=0;i<=75;i++) {
			switch(direction) {
			case 'u':
				//shoots straight up
				entityManager.addEntity(new Bullet(x+width/2+i/5-5,y,x+width/2+i/5-5,y-2,Assets.blueLaser[0],8, true));
				break;
			case 'l':
				//shoots straight left
				entityManager.addEntity(new Bullet(x,y+i/5-5,x-2,y+i/5-5,Assets.blueLaser[3],8, true));
				break;
			case 'r':
				//shoots straight right
				entityManager.addEntity(new Bullet(x+width,y+i/5-5,x+2+width,y+i/5-5,Assets.blueLaser[1],8, true));
				break;
			case 'd':
				//shoots straight up
				entityManager.addEntity(new Bullet(x+width/2+i/5-5,y+height,x+width/2+i/5-5,y+height+2,Assets.blueLaser[2],8, true));
				break;
			}
		}
	}
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}
	@Override
	public String hover(char leftRight) {
		return "this tower is in its strongest form";
	}

}
