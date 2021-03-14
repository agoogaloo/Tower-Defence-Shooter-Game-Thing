package entity.statics;

import entity.Entity;
import entity.mobs.player.Player;

public abstract class Pickup extends Statics{
	
	public Pickup(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;//setting the size and location so updatebounds will work
		this.width=width;
		this.height=height;
		health=1;
	}
	
	abstract void playerCollide(Player p);
	
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
