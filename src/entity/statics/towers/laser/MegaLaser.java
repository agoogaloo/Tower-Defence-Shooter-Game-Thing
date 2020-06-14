package entity.statics.towers.laser;

import java.awt.Graphics;

import entity.statics.Statics;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class MegaLaser extends Statics{
	int duration, length;
	char direction;
	public MegaLaser(int x, int y,int duration, char direction) {
		health=999;
		this.direction=direction;
		this.duration=duration;
		this.x=x;
		this.y=y;
		
		friendly=true;//making it so it wont hurt you
		damage=100;//making it do a ton of damage
		findLength();
		
		switch (direction) {
		case 'u':
			bounds.setBounds(x-6, y-length, 13, length);
			break;
		case 'l':
			bounds.setBounds(x-length, y-6, length, 13);
			break;
		case 'd':
			bounds.setBounds(x-6, y, 13, length);
			break;
		case 'r':
			bounds.setBounds(x, y-6, length, 13);
			break;
		}
	}

	private void findLength() {
		for(int i=0;i<=700;i+=10) {
			length=i;//adding 10 to the length of the beam
			switch(direction) {
			case 'u':
				//exiting if there is a wall in the way because it has gone as far as it can
				if(GameState.getFloor().checkwall(x/16,(y-i)/16)) {return;}
				break;//exiting the switch case so it can loop again if there isnt a wall
				
			case 'r'://doing the same thing for all directions
				if(GameState.getFloor().checkwall((x+i)/16,y/16)) {return;}
				break;
				
			case 'd':
				if(GameState.getFloor().checkwall(x/16,(y+i)/16)) {return;}
				break;
				
			case 'l':
				if(GameState.getFloor().checkwall((x-i)/16,y/16)) {return;}
				break;
			}
		}
	}
	
	
	@Override
	public void update() {
		duration--;
		if(duration<=0) {
			killed=true;
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		
		for(int i=0;i<length;i+=18) {
			switch(direction) {
			case 'u':
				g.drawImage(Assets.megaLaserU[12], x-camera.getxOffset(), y-i-camera.getyOffset(), null);
				break;
			case 'r':
				g.drawImage(Assets.megaLaserR[12], (x+i)-camera.getxOffset(), y-camera.getyOffset(), null);
				break;
			case 'd':
				g.drawImage(Assets.megaLaserD[12], x-camera.getxOffset(), y+i-camera.getyOffset(), null);
				break;
			case 'l':
				g.drawImage(Assets.megaLaserL[12], x-i-camera.getxOffset(), y-camera.getyOffset(), null);
				break;
			}
		}
	}
}
