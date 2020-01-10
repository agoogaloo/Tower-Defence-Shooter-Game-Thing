package entity.statics;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

/*
 * by: Matthew Milum
 */
public class Core extends Statics{
	Animation anim = new Animation(Assets.core,6);
	int damage=0;
	
	public Core(int x, int y){
		this.x=x;
		this.y=y;
		this.bounds.x=x;
		this.bounds.y=y;
	}
	
	@Override
	public void update() {
		if(entityCollide().size()>0){
			getHit(1);
		}
		anim.update();
		
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	
	}
	public int giveDamage(){
		int num=damage;//needs to be its own variable so that when damage is reset it wont return 0
		damage=0;//reseting damage so it doesnt stack
		return num;
		
	}
	public void getHit(int damage){
		this.damage+=damage;
	}

}
