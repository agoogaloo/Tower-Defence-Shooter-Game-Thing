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
import graphics.UI.TextElement;
import states.State;
import window.Window;

public class TowerPlacer {
	// an enum that will be used to tell store whether the player is placing or upgrading a tower or just doing nothing
	private enum Mode{PLACING,UPGRADING,WAITING}
	private Tower selectedTower;
	private BufferedImage currentImage;
	private char mouseUpDown='n',mouseLeftRight='n';//set to l,r,u,d,or n depending on where the mouse is
	private TextElement infoText=new TextElement(0, 0,72 ,10,"i am testing multiline text and stuff and hope this works");
	
		
	int startX,startY, moneySpent;
	Mode mode=Mode.WAITING;
	private void updateMouseAngle() {
		if(State.getInputs().getMouseY()-startY>7) {
			//this means the mouse is in the bottom half
			mouseUpDown='d';
		}else if(State.getInputs().getMouseY()-startY<-7) {
			//this means it is on the top of the circle
			mouseUpDown='u';
		}else{
			//otherwise it is in the middle
			mouseUpDown='n';
		}
		if(State.getInputs().getMouseX()-startX>7) {
			//this means it is to the right
			mouseLeftRight='r';
		}else if(State.getInputs().getMouseX()-startX<-7) {
			//this means it is to the left
			mouseLeftRight='l';
		}else {
			mouseLeftRight='n';
		}	
	}
	private Tower place(int money,Camera camera, char direction) {
		if(!State.getInputs().isPlace()) {
			if(mouseUpDown=='d') {
				//returns a laser tower if it is selected
				if(money>=5) {
					moneySpent+=5;
					return new LaserTowerlvl1(startX+camera.getxOffset(),startY+camera.getyOffset(), direction); 
				}
			}else if(mouseUpDown=='u') {
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
		currentImage=selectedTower.getUpgradeIcon();
		if(!State.getInputs().isPlace()) {
			if(mouseUpDown=='u') {
				moneySpent+=selectedTower.upgrade(mouseLeftRight, money);
			}else if(mouseUpDown=='d') {
				moneySpent=-selectedTower.getSellValue();//giving some money back when the tower is sold
				selectedTower.destroy();//destroying the selected tower
			}
		}
		return null;
	}
	public Tower update(int money,Camera camera, ArrayList<Entity> entities, char direction) {
		Tower tower=null;//the tower that will be returned at the end of the method
		//determining if the player is waiting, placing, or upgrading a tower
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
		updateMouseAngle();
		if(mode==Mode.PLACING) {
			tower = place(money,camera, direction);
		}else if(mode==Mode.UPGRADING) {
			tower=upgrade(money,camera);
		}
		if(!State.getInputs().isPlace()) {
			mode=Mode.WAITING;//if the button is not being pushed the mode can be set back to waiting
		}
		return tower;
	}
	public void render(Graphics g, Camera camera) {
		if(mode==Mode.PLACING) {
			g.drawImage(Assets.towerMenu[0], startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,0,null);
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,100,null);
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,200,null);
			g.drawString("testing text", camera.getScreenWidth()-72, 10);
		}else if(mode==Mode.UPGRADING) {
			g.drawImage(Assets.towerMenu[1], startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
			g.drawImage(currentImage, startX-Assets.towerMenu[0].getWidth()/2, startY-Assets.towerMenu[0].getHeight()/2,null);	
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,0,null);
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,100,null);
			g.drawImage(Assets.heartTexture, camera.getScreenWidth()-72,200,null);
		}
	}
	public int getSpentMoney() {
		int money=moneySpent;
		moneySpent=0;
		return money;
	}
}
