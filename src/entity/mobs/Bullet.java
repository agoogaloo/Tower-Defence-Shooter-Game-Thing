package entity.mobs;
//@author Kevin
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;

public class Bullet extends Mobs{
	double bulletPath;
	double velocityX,velocityY;

	private Assets assets = new Assets();

	public Bullet(int startX,int startY,double targetX,double targetY,BufferedImage pics){
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
	public void render(Graphics g){
		g.drawImage(assets.getBullet(), x, y, null);
	}
}

