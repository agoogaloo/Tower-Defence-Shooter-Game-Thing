package entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

//@author Matthew

public class Core extends Statics{
	/*
	 * this is the core that spawns at the beginning of the level and all the enemies try 
	 * to reach. every frame give damage is called which takes all the damage that has been 
	 * given to it by the enemies and lets the player subtract it from their health so they are connected
	 */
	Animation anim = new Animation(Assets.core,6);
	private BufferedImage currentPic;
	int damageTaken=0;//how much damage the core has taken this frame
	
	public Core(int x, int y){
		friendly=true;
		health=1;//the core wont take damage because the damage method is modified
		this.x=x-20;
		this.y=y-17;
		this.bounds.x=this.x;//setting the bounds to be the right size/location
		this.bounds.y=this.y;
		bounds.width=40;
		bounds.height=35;
		damage=1000;//enemies that touch it will take a bunch of damage so they wont repeatedly hit the core
	}
	
	@Override
	public void damage() {
		for(Entity e:entityCollide()) {//checking if anything is touching it
			if(e instanceof Enemy) {//making sure the entity is an enemy
				damageTaken+=1;//an enemy has reached the core so it should deal 10 damage to the player
				currentPic=damageFlash(currentPic);
			}
		}
	}
	@Override
	public void update() {
		anim.update();//updating its animation
		currentPic=anim.getCurrentFrame();
	}
	@Override
	public void render(Graphics g, Camera camera) {
		//just drawing itself to the screen
		g.drawImage(currentPic, x-camera.getxOffset(), y-camera.getyOffset(), null);
	
	}
	public int giveDamage(){
		//this is called by the player every frame so it knows how much health to subtract
		int num=damageTaken;//needs to be its own variable so that when damage is reset it wont return 0
		damageTaken=0;//reseting damage so it doesnt stack
		return num;//Outputting the damage
		
	}
	public void destroy() {
		//this lets us destroy the core because without this method it could never go away once it is spawned
		killed=true;
	}
}
