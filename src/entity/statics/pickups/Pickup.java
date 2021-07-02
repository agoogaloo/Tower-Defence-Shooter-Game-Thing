package entity.statics.pickups;

import entity.Entity;
import entity.mobs.player.Player;
import entity.statics.Statics;

public abstract class Pickup extends Statics{
	
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
	public void update() {}
	
	@Override
	public void damage() {
		for(Entity e:entityCollide()) {
			if(e instanceof Player) {//checking if it is touching the player
				playerCollide((Player)e);
				killed=true;//destroying itself once the player has collected it
			}
		}
	}

}
