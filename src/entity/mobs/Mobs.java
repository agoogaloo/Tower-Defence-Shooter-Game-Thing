package entity.mobs;

import java.awt.Graphics;

import entity.Entity;

public class Mobs extends Entity{
	int speed, changeX , changeY;
	public int getchangeX(){
		return x+=changeX;
	}
	public int getchangeY(){
		return y=+changeY;
	}
	/*public void wallCollision(){
		if (changeX+x>Window.getWidth()){
			changeX=0;
		}
		if (changeY+y>window.getHeight()){
			changeY=0;
		}
	}*/
	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
