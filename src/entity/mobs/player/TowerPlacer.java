package entity.mobs.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.statics.towers.LaserTowerlvl1;
import entity.statics.towers.Tower;
import entity.statics.towers.WizardTowerlvl1;
import entity.statics.towers.WizardTowerlvl2;
import graphics.Assets;
import graphics.Camera;
import states.State;

public class TowerPlacer {
	// an enum that will be used to tell store whether the player is placing or upgrading a tower or just doing nothing
	enum Mode{PLACING,UPGRADING,WAITING}
	enum MouseAngle{UPLEFT,UPRIGHT,DOWNLEFT,DOWNRIGHT, NEUTRAL}
	Tower selectedTower;
	BufferedImage currentImage;
	boolean destroyTower;
		
	int startX,startY, moneySpent;
	Mode mode=Mode.WAITING;
	private MouseAngle GetMouseAngle() {
		if(State.getInputs().getMouseY()-startY>7) {
			//this means the mouse is in the bottom half
			if(State.getInputs().getMouseX()-startX>7) {
				//this means it is to the right
				return  MouseAngle.DOWNRIGHT;
			}else if(State.getInputs().getMouseX()-startX>-7) {
				//this means it is to the left
				return  MouseAngle.DOWNLEFT;
			}
		}else if(State.getInputs().getMouseY()-startY<-7) {
			//this means it is on the top of the circle
			if(State.getInputs().getMouseX()-startX>7) {
				//this means it is to the right
				return  MouseAngle.UPRIGHT;
			}else if(State.getInputs().getMouseX()-startX>-7) {
				//this means it is to the left
				return  MouseAngle.UPLEFT;
			}
		}
		return MouseAngle.NEUTRAL;
	}
	private Tower place(int money,Camera camera) {
		if(!State.getInputs().isPlace()) {
			if(State.getInputs().getMouseY()-startY>7) {
				//returns a laser tower if it is selected
				if(money>=5) {
					moneySpent+=5;
					return new LaserTowerlvl1(startX+camera.getxOffset(),startY+camera.getyOffset()); 
				}
			}else if(State.getInputs().getMouseY()-startY<-7) {
				//returns a wizard tower if it is selected
				if(money>=2) {
					moneySpent+=2;
					return new WizardTowerlvl1(startX+camera.getxOffset(),startY+camera.getyOffset());
				}
			}
		}
		return null;
	}
	private Tower upgrade(int money,Camera camera) {
		MouseAngle angle=GetMouseAngle();
		System.out.println("mouse at "+angle.toString());
			if(selectedTower instanceof WizardTowerlvl1) {
			currentImage=Assets.towerMenu[2];
			if(!State.getInputs().isPlace()) {
				if(angle==MouseAngle.UPLEFT||angle==MouseAngle.UPRIGHT) {
					if(money>=1) {
						moneySpent=1;
						selectedTower.destroy();
						return new WizardTowerlvl2(selectedTower.getX(), selectedTower.getY());
					}
				}else if(angle==MouseAngle.DOWNLEFT||angle==MouseAngle.DOWNRIGHT) {
					moneySpent=-1;//giving some money back when the tower is sold
					selectedTower.destroy();//destroying the selected tower
				}
			}
		}
		return null;
	}
	public Tower update(int money,Camera camera, ArrayList<Entity> entities) {
		Tower tower=null;//the tower that will be returned at the end of the method
		if(State.getInputs().isPlace()) {//checking if the right click is pressed
			if(mode==Mode.WAITING) {//checking if they just pressed the button this frame
				startX=State.getInputs().getMouseX();//setting the location of the menu 
				startY=State.getInputs().getMouseY();//and where the tower will be placed
				mode=Mode.PLACING;
				for(Entity e:entities) {
					if(e instanceof Tower&&e.getBounds().contains(startX+camera.getxOffset(), startY+camera.getyOffset())){
						mode=Mode.UPGRADING;
						selectedTower=(Tower)(e);//making the entity a tower so it can it can be set as the selected tower
					}
				}
			}
		}
		if(mode==Mode.PLACING) {
			tower = place(money,camera);
		}else if(mode==Mode.UPGRADING) {
			tower=upgrade(money,camera);
		}
		if(!State.getInputs().isPlace()) {
			mode=Mode.WAITING;//if the buttong is not being pushed the mode can be set back to waiting
			destroyTower=false;
		}
		return tower;
	}
	public void render(Graphics g) {
		if(mode==Mode.PLACING) {
			g.drawImage(Assets.towerMenu[0], startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
		}else if(mode==Mode.UPGRADING) {
			g.drawImage(Assets.towerMenu[1], startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
			g.drawImage(currentImage, startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
		}
	}
	public int getSpentMoney() {
		int money=moneySpent;
		moneySpent=0;
		return money;
	}
}
