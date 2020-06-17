package entity.statics.towers.laser;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class MegaLaser extends Statics{
	private int duration, length, curentAnim=0;
	private char direction;
	private Animation[] gunAnims=new Animation[3], beamAnims=new Animation[3];
	private boolean animLooped=false;
	
	public MegaLaser(int x, int y,int duration, char direction) {
		health=9999;
		this.direction=direction;
		this.duration=duration;
		
		
		friendly=true;//making it so it wont hurt you
		damage=10;//making it do a ton of damage
		this.x=x;
		this.y=y;//setting x an y to paramiters so the length will be determind by the middle of the beam
		findLength();
		makeAnims();
		
		this.x=x-gunAnims[0].getCurrentFrame().getWidth()/2;
		this.y=y-gunAnims[0].getCurrentFrame().getHeight()/2;
		bounds.setBounds(x, y, 0, 0);//making sure that it starts without a hit box
		
	}
	
	private void makeAnims() {
		//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
		switch (direction) {
		case 'u':
			//this is horrible and should be done some other way but i dont know how
			gunAnims[0]=new Animation(Arrays.copyOf(Assets.megaLaserU,6));
			gunAnims[1]=new Animation(new BufferedImage[] {Assets.megaLaserU[6]});
			gunAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserU,7,9));
			
			beamAnims[0]=new Animation(new BufferedImage[] {Assets.blank,Assets.blank,Assets.blank,Assets.blank,
					Assets.megaLaserU[9],Assets.megaLaserU[10]});
			beamAnims[1]=new Animation(Arrays.copyOfRange(Assets.megaLaserU,11,15));
			beamAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserU,15,17));
			break;
			
		case 'r'://doing the same thing for all direction
			gunAnims[0]=new Animation(Arrays.copyOf(Assets.megaLaserR,6));
			gunAnims[1]=new Animation(new BufferedImage[] {Assets.megaLaserR[6]});
			gunAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserR,7,9));
			
			beamAnims[0]=new Animation(new BufferedImage[] {Assets.blank,Assets.blank,Assets.blank,Assets.blank
					,Assets.megaLaserR[9],Assets.megaLaserR[10]});
			beamAnims[1]=new Animation(Arrays.copyOfRange(Assets.megaLaserR,11,15));
			beamAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserR,15,17));
			break;
			
		case 'd':
			gunAnims[0]=new Animation(Arrays.copyOf(Assets.megaLaserD,6));
			gunAnims[1]=new Animation(new BufferedImage[] {Assets.megaLaserD[6]});
			gunAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserD,7,9));
			
			beamAnims[0]=new Animation(new BufferedImage[] {Assets.blank,Assets.blank,Assets.blank,Assets.blank,
					Assets.megaLaserD[9],Assets.megaLaserD[10]});
			beamAnims[1]=new Animation(Arrays.copyOfRange(Assets.megaLaserD,11,15));
			beamAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserD,15,17));
			break;
			
		case 'l':
			gunAnims[0]=new Animation(Arrays.copyOf(Assets.megaLaserL,6));
			gunAnims[1]=new Animation(new BufferedImage[] {Assets.megaLaserL[6]});
			gunAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserL,7,9));
			
			beamAnims[0]=new Animation(new BufferedImage[] {Assets.blank,Assets.blank,Assets.blank,Assets.blank,
					Assets.megaLaserL[9],Assets.megaLaserL[10]});
			beamAnims[1]=new Animation(Arrays.copyOfRange(Assets.megaLaserL,11,15));
			beamAnims[2]=new Animation(Arrays.copyOfRange(Assets.megaLaserL,15,17));
			break;
			
		}
	}
	
	private void setBounds() {
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
		gunAnims[curentAnim].update();
		beamAnims[curentAnim].update();
		switch (curentAnim) {
		case 0:
			if(beamAnims[curentAnim].getlength()-1==beamAnims[curentAnim].getFrameIndex()) {
				animLooped=true;//setting looped to true if it has reached its final frame
				
			}else if(beamAnims[curentAnim].getFrameIndex()==0&&animLooped) {
				curentAnim++;
				animLooped=false;
				setBounds();//adding the hitbox once it has fully opened
			}
			break;
			
		case 1:
			//shaking the screen making the laser feel more powerfull
			GameState.screenShake(0.1);
			duration--;
			if(duration<0) {
				curentAnim++;
			}
			break;
			
		case 2:
			bounds.setBounds(x, y, 0, 0);//removing the hitbox because it if finished shooting
			if(beamAnims[curentAnim].getFrameIndex()==1) {
				animLooped=true;//setting looped to true if it has reached its final frame
				
			}else if(beamAnims[curentAnim].getFrameIndex()==0&&animLooped) {
				killed=true;//setting it to killed so that the entity manager will remove it
				beamAnims[curentAnim].setCurrentFrame(beamAnims[curentAnim].getlength()-1);
				gunAnims[curentAnim].setCurrentFrame(gunAnims[curentAnim].getlength()-1);
			}
			break;
		}
		
		System.out.println("curent anim: "+curentAnim+", beam frame: "+beamAnims[curentAnim].getFrameIndex()+", gun frame: "+gunAnims[curentAnim].getFrameIndex());
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		//drawing the begining part of the laser
		g.drawImage(gunAnims[curentAnim].getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
		
		for(int i=13;i<length;i+=18) {
			//the beam needs to be drawn in a different placce depending on its direction
			switch(direction) {
			case 'u':
				g.drawImage(beamAnims[curentAnim].getCurrentFrame(), x-camera.getxOffset(), y-i-camera.getyOffset(), null);
				break;
			case 'r':
				g.drawImage(beamAnims[curentAnim].getCurrentFrame(), (x+i)-camera.getxOffset(), y-camera.getyOffset(), null);
				break;
			case 'd':
				g.drawImage(beamAnims[curentAnim].getCurrentFrame(), x-camera.getxOffset(), y+i-camera.getyOffset(), null);
				break;
			case 'l':
				g.drawImage(beamAnims[curentAnim].getCurrentFrame(), x-i-camera.getxOffset(), y-camera.getyOffset(), null);
				break;
			}
		}
	}
}
