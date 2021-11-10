package entity.mobs.pickups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.player.Player;
import graphics.Animation;
import graphics.Camera;
import graphics.ImageUtils;

public class GunTowerItem extends Pickup{
	private int id;
	
	
	private double xVel, yVel, vel=3.5;
	private double slowDown=0.25;
	public GunTowerItem(int x, int y, int id) {
		super(x,y);
		this.id=id;
		trueX=x;
		trueY=y;
		
		anim = new Animation(new BufferedImage[]{ImageUtils.outline(ItemList.getIcon(id),Color.white)});
		
		setSize(getIcon().getWidth(), getIcon().getHeight());
		double radians=ThreadLocalRandom.current().nextInt(0, 360)*Math.PI/180;
		xVel = Math.cos(radians); //calculates the direction and speed needed to move along the x axis
		yVel = Math.sin(radians); 
	}
	@Override
	public void update() {
		super.update();
		if(vel>0) {
			vel-=slowDown;
		}else {
			vel=0;
		}
		changeX=xVel*vel;
		changeY=yVel*vel;
	}

	
	@Override
	protected void playerCollide(Player p) {
		p.giveItem(id);
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(getIcon(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		//drawing itself to the screen
	}
}
