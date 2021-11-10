package entity.mobs.pickups;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.Mobs;
import entity.mobs.player.Player;
import graphics.Animation;

public abstract class Pickup extends Mobs{
	protected Animation anim;
	
	public Pickup(int x, int y) {
		this.x=x;
		this.y=y;
		health=1;
	}
	public Pickup(int x, int y, Animation anim, int width, int height) {
		this(x,y);
		this.anim=anim;
		setSize(width, height);
		
	}
	
	protected void setSize(int width, int height) {
		this.width=width;
		this.height=height;
	}
	
	
	protected abstract void playerCollide(Player p);
	
	@Override
	public void update() {
		move();
		anim.update();
	}
	
	@Override
	public void damage() {
		for(Entity e:entityCollide()) {
			if(e instanceof Player) {//checking if it is touching the player
				playerCollide((Player)e);
				killed=true;//destroying itself once the player has collected it
			}
		}
	}
	@Override
	public void damage(double amount) {}
	
	public BufferedImage getIcon () {
		return anim.getCurrentFrame();
	}
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
		trueX=x;
		trueY=y;
		updateBounds();
		
	}

}
