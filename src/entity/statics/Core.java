package entity.statics;

import java.awt.Graphics;

import entity.Entity;
import entity.mobs.Enemy;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

/*
 * by: Matthew Milum
 */
public class Core extends Statics{
	Animation anim = new Animation(Assets.core,6);
	int damageTaken=0;
	
	public Core(int x, int y){
		friendly=true;
		health=999999999;
		this.x=x;
		this.y=y;
		this.bounds.x=x;
		this.bounds.y=y;
		damage=5;
	}
	
	@Override
	public void update() {
		for(Entity e:entityCollide()) {
			if(e instanceof Enemy) {
				getHit(10);
			}
		}
		anim.update();
		
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	
	}
	public int giveDamage(){
		int num=damageTaken;//needs to be its own variable so that when damage is reset it wont return 0
		damageTaken=0;//reseting damage so it doesnt stack
		return num;
		
	}
	public void getHit(int damage){
		damageTaken+=damage;
	}

}
