package entity.mobs;

import java.awt.Graphics;

import graphics.Camera;

public class Bullet extends Mobs{
	double bulletPath;
	double velocityX,velocityY;

	public Bullet(int startX,int startY,double targetX,double targetY){
		double bulletPath = Math.atan2(targetY, targetX);
		double velocityX = speed*Math.cos(bulletPath);
		double velocityY = speed*Math.sin(bulletPath);
	}


	public double getVelocityX(){
		return this.velocityX;
	}
	public double getVelocityY(){
		return this.velocityY;
	}
	@Override
	public void update(){
		x+=getVelocityX();
		y+=getVelocityY();
	}
	@Override
	public void render(Graphics g, Camera camera){
		
	}
}

