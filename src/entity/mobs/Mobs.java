package entity.mobs;

import Main.Main;
import entity.Entity;

public abstract class Mobs extends Entity{
	protected int speed=1, changeX, changeY;
	
	/*public void wallCollision(){
		if (changeX+x>Window.getWidth()){
			changeX=0;
		}
		if (changeY+y>window.getHeight()){
			changeY=0;
		}
	}*/
}
