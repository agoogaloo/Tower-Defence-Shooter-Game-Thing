package entity.mobs;
//@author Kevin
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Camera;

public class Bullet extends Mobs{
	double bulletPath;
	double velocityX,velocityY, trueX, trueY;
	int startX, startY;
	int speed = 5;
	int i;
	BufferedImage [] bulletPics = Assets.bullet;
	BufferedImage [] playerPics = Assets.player;
	public Bullet(int startX,int startY,double targetX,double targetY, int pics, int speed){
		this.speed = speed;
		x = startX;
		y = startY;	
		i = pics;
		trueX=x;
		trueY=y;
		bulletPath = Math.atan2(targetY-y, targetX-x);
		velocityX = speed*Math.cos(bulletPath);
		velocityY = speed*Math.sin(bulletPath);
	}

	@Override
	public void update(){
		int offsetX = bulletPics[i].getWidth()/2 - 10;
		int offsetY = bulletPics[i].getHeight()/2 - 10;
		trueX+=velocityX;
		trueY+=velocityY;
		x=((int)(trueX) - offsetX);
		y=((int)(trueY) - offsetY);
	}
	@Override

	public void render(Graphics g, Camera camera){
		g.drawImage(bulletPics[i], x-bulletPics[i].getWidth()/2-camera.getxOffset(),
				y-bulletPics[i].getHeight()/2- camera.getyOffset(), null);
	}
}

