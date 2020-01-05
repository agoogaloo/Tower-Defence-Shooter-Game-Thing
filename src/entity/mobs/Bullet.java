package entity.mobs;
//@author Kevin
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;

public class Bullet extends Mobs{
	double bulletPath;
	double velocityX,velocityY;
	int startX, startY;
	int speed = 5;
	private Assets assets = new Assets();

	public Bullet(int startX,int startY,double targetX,double targetY){
		x = startX;
		y = startY;		
		bulletPath = Math.atan2(targetY-y, targetX-x);
		velocityX = speed*Math.cos(bulletPath);
		velocityY = speed*Math.sin(bulletPath);
	}

	@Override
	public void update(){
		x+=velocityX;
		y+=velocityY;
	}
	@Override
	public void render(Graphics g){
		g.drawImage(assets.getBullet(), x, y, null);
	}
}

