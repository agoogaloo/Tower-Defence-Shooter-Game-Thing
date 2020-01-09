package entity.mobs;

import Main.Main;
import entity.Entity;
import floors.Floor;

public abstract class Mobs extends Entity{
	protected int speed=1, changeX, changeY;
	public void wallcollison(int x, int y){
		if (Main.getWindow().getDisplay().getFloor().checkwall(x,y)){
			changeX=0;
			changeY=0;
		}
	}
}
