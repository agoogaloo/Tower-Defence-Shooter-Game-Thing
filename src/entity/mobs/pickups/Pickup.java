package entity.mobs.pickups;

import entity.Entity;
import entity.mobs.Mobs;
import entity.mobs.player.Player;

public abstract class Pickup extends Mobs{
	
	public Pickup(int x, int y) {
		this.x=x;
		this.y=y;
		health=1;
	}
	public Pickup(int x, int y, int width, int height) {
		this(x,y);
		setSize(width, height);
		
	}
	
	protected void setSize(int width, int height) {
		this.width=width;
		this.height=height;
	}
	
	abstract void playerCollide(Player p);
	
	@Override
	public void update() {
		move();
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
	public void damage(int amount) {}

}
