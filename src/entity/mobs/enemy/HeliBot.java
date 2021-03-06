package entity.mobs.enemy;

import java.awt.Graphics;

import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class HeliBot extends Enemy  {
	private final int TARGETDISTANCE=70;
	private int changedDistance=TARGETDISTANCE;
	private int timeAlive=0;
	
	
	public HeliBot(int x, int y) {
		super(x, y,'d');		
		speed=2.5;
		health=1;
		reloadTime=25;
		bounds.x=x;
		bounds.y=y;
		bounds.width=0;
		bounds.height=0;
		//creating its animations
		anims[UP]=new Animation(Assets.heliBotU);
		anims[DOWN]=new Animation(Assets.heliBotD);
		anims[LEFT]=new Animation(Assets.heliBotL);
		anims[RIGHT]=new Animation(Assets.heliBotR);
	}

	
	@Override
	public void update() {
		//updating the animations
		for(Animation i: anims) {
			i.update();
		}
		timeAlive++;
		//making it shoot 1 frame faster every 35 frames
		if(timeAlive%35==0 &&reloadTime>=7) {
			reloadTime--;
		}
		//setting/moving towards its target
		target();
		//making it face the right way
		updateDirection();
		
		if (shotDelay >= reloadTime&&timeAlive>=180) { //shooting when it has waited long enough
			shoot();
			shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
		}
		shotDelay++;
	}
	
	@Override
	public void updateDirection() {
		//setting its direction based on where the player is relative to heliBot
		int xDiff=entityManager.getPlayer().getX()-x;
		int yDiff=entityManager.getPlayer().getY()-y;
		if(Math.abs(xDiff)>Math.abs(yDiff)) {
			if(xDiff>0) {
				direction=RIGHT;
			}else {
				direction=LEFT;
			}
		}else {
			if(yDiff>0) {
				direction=DOWN;
			}else {
				direction=UP;
			}
		}
		//using the right animation base on its direction
		currentPic=anims[direction].getCurrentFrame();
		
	}
	
	//this is would be called move but i cant because it is used for something else
	private void target() {
		//the location of the enemy wave that it is trying to send the player to
		int waveX= 0,waveY=0;
		int enemies=0;
		//making wavex and y the average x and y of all the enemies
		for(Entity e:entityManager.getEntities()) {//looping through all the entities
			if(e instanceof Enemy&&!e.equals(this)) {//checking if it is an enemy
				enemies++;//adding one to the enemies counter so we know how much to divide by
				waveX+=e.getX();//adding the x/y of the entity
				waveY+=e.getY();
			}
		}
		//setting making sure there are enemies so there isnt a divide by 0 error 
		if(enemies!=0) {	
			//dividing by number of enemies to take the average location
			waveX/=enemies;
			waveY/=enemies;
		}
		
		//the angle of the wave to the player 
		double waveAngle=Math.atan2(entityManager.getPlayer().getY()-waveY, entityManager.getPlayer().getX()-waveX);
		
		//the point that the enemy is trying to get to. it will be TargetDistance away from the player at the 
		//angle of wave angle.
		int targetX= entityManager.getPlayer().getX()+(int)(changedDistance*Math.cos(waveAngle));
		int targetY=entityManager.getPlayer().getY()+(int)(changedDistance*Math.sin(waveAngle));
		
		//getting the angle that it needs to move to reach target x/y
		double moveAngle = Math.atan2(targetY-y, targetX-x);
		int xMove = (int)(speed*Math.cos(moveAngle)); //how much it should move left/right
		int yMove = (int)(speed*Math.sin(moveAngle)); //how much it should move up/down
		
		//how far it currently is from its targeted point
		double offset=Math.sqrt(Math.pow(Math.abs(targetX-x),2)+
				Math.pow(Math.abs(targetY-y),2));
		
		//moving towards the point if it is more than 10 pixels away from it (its a circular range so it can be a not integer)
		if(offset>10){
			x+=xMove;
			y+=yMove;
			
		}
		if (GameState.getFloor().checkwall((int)x/16,(int)y/16)){
			changedDistance-=3;
		}else {
			changedDistance=TARGETDISTANCE;	
		}
	}
	
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(currentPic, x-camera.getxOffset()-15, y-camera.getyOffset()-13, null);
	}
	
	@Override
	public void damage() {
	}
	
	public void destroy() {
		killed=true;
	}
}
