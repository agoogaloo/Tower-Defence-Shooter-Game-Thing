package entity.mobs.player;

import java.awt.Graphics;

import entity.statics.towers.LaserTower;
import entity.statics.towers.Tower;
import entity.statics.towers.WizardTower;
import graphics.Assets;
import graphics.Camera;
import states.State;

public class TowerPlacer {
	int startX,startY;
	boolean placing=false;
	
	public Tower update(Camera camera) {
		if(State.getInputs().isPlace()) {
			if(!placing) {
				placing=true;
				startX=State.getInputs().getMouseX();
				startY=State.getInputs().getMouseY();
			}
			
		}else if(State.getInputs().getMouseY()-startY>7&&placing) {
			//returns a laser tower if it is selected
			placing=false;
			return new LaserTower(startX+camera.getxOffset(),startY+camera.getyOffset()); 
			
		}else if(State.getInputs().getMouseY()-startY<-7&&placing) {
			//returns a wizard tower if it is selected
			placing=false;
			return new WizardTower(startX+camera.getxOffset(),startY+camera.getyOffset());
		}
		if(!State.getInputs().isPlace()) {
			placing=false;
		}
		return null;
	}
	public void render(Graphics g) {
		if(State.getInputs().isPlace()) {
			g.drawImage(Assets.towerMenu, startX-Assets.towerMenu.getWidth()/2, startY-Assets.towerMenu.getHeight()/2,null);
			
		}
	}

}
