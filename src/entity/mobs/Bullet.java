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
	int pic;
	
	BufferedImage [] bulletPics = Assets.bullet;

	public Bullet(int startX,int startY,double targetX,double targetY, int pic, int speed, boolean friendly){ //0 is an enemy bullet 1 is a friendly bullet
		this.speed = speed;
		this.friendly=friendly;
		this.pic = pic;
		x = startX;
		y = startY;	
		trueX=x;
		trueY=y;
		damage=1;
		width=6;
		height=6;
		
		bulletPath = Math.atan2(targetY-y, targetX-x);
		velocityX = speed*Math.cos(bulletPath);
		velocityY = speed*Math.sin(bulletPath);
	}
	

	@Override
	public void update(){
		updateBounds();
		int offsetX = bulletPics[pic].getWidth()/2 - 10;
		int offsetY = bulletPics[pic].getHeight()/2 - 10;
		trueX+=velocityX;
		trueY+=velocityY;
		x=((int)(trueX) - offsetX);
		y=((int)(trueY) - offsetY);
		move();
		
	}
	@Override
	public void render(Graphics g, Camera camera){
		g.drawImage(bulletPics[pic], x-bulletPics[pic].getWidth()/2-camera.getxOffset(),
				y-bulletPics[pic].getHeight()/2- camera.getyOffset(), null);
	}

}

