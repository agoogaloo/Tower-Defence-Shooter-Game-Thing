package entity.mobs.enemy;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class HeliBot extends Enemy  {
	private final int TARGETDISTANCE=70;
	
	
	public HeliBot(int x, int y, char direction) {
		super(x, y, direction);
		speed=2.5;
		health=1;
		reloadTime=15;
		bounds.x=x;
		bounds.y=y;
		bounds.width=0;
		bounds.height=0;
		animUp=new Animation(Assets.heliBotU);
		animDown=new Animation(Assets.heliBotD);
		animLeft=new Animation(Assets.heliBotL);
		animRight=new Animation(Assets.heliBotR);
	}

	
	@Override
	public void update() {
		animDown.update();
		animLeft.update();
		animRight.update();
		animUp.update();
		
		target();
		updateDirection();
		switch(direction) {
		case 'd':
			currentPic=animDown.getCurrentFrame();
			break;
		case 'u':
			currentPic=animUp.getCurrentFrame();
			break;
		case 'l':
			currentPic=animLeft.getCurrentFrame();
			break;
		case 'r':
			currentPic=animRight.getCurrentFrame();
		}
		
		if (shotDelay >= reloadTime) { //If the enemy hasn't attacked in the last 30 frames and it detects the player's box it will shoot
			shoot();
			shotDelay = 0; //Resets shotDelay to prevent enemy from rapidly shooting
		}
		shotDelay++;
	}
	
	@Override
	public void updateDirection() {
		int xDiff=entityManager.getPlayer().getX()-x;
		int yDiff=entityManager.getPlayer().getY()-y;
		if(Math.abs(xDiff)>Math.abs(yDiff)) {
			if(xDiff>0) {
				direction='r';
			}else {
				direction='l';
			}
		}else {
			if(yDiff>0) {
				direction='d';
			}else {
				direction='u';
			}
		}
	}
	
	//this is would be called move but i cant because it is used for something else
	private void target() {
		//the location of the enemy wave that it is trying to send the player to
		int waveX= GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE*GameState.getFloor().getSize()+230,
				waveY=GameState.getFloor().ROOMSIZE*GameState.getFloor().TILESIZE*GameState.getFloor().getSize()-248;
		//the angle of the wave to the player 
		double waveAngle=Math.atan2(entityManager.getPlayer().getY()-waveY, entityManager.getPlayer().getX()-waveX);
		
		//the point that the enemy is trying to get to. it will be TargetDistance away from the player at the 
		//angle of wave angle.
		int targetX= entityManager.getPlayer().getX()+(int)(TARGETDISTANCE*Math.cos(waveAngle));
		int targetY=entityManager.getPlayer().getY()+(int)(TARGETDISTANCE*Math.sin(waveAngle));
		
		//getting the angle that it needs to move to reach target x/y
		double moveAngle = Math.atan2(targetY-y, targetX-x);
		int xMove = (int)(speed*Math.cos(moveAngle)); //how much it should move left/right
		int yMove = (int)(speed*Math.sin(moveAngle)); //how much it should move up/down
		
		//how far it currently is from its targeted point
		double offset=Math.sqrt(Math.pow(Math.abs(targetX-x),2)+
				Math.pow(Math.abs(targetY-y),2));
		
		//moving towards the point if it is more than 7.5 pixels away from it (its a circular range so it can be a not integer)
		if(offset>7.5){
			x+=xMove;
			y+=yMove;
		}
	}
	
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(currentPic, x-camera.getxOffset()-15, y-camera.getyOffset()-13, null);
	}
	
	

	

}
