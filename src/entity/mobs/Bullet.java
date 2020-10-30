package entity.mobs;
//@author Kevin (all of bullet except for a few parts)
//@author Matthew (trueX, trueY, width, height, damage, friendly, move())

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import graphics.Camera;
import states.GameState;

public class Bullet extends Mobs{

	double bulletPath; //Calculates the path to the target
	double velocityX,velocityY, trueX, trueY; //Velocity is how fast bullet travels and it's direction, trueX and trueY are the actual locations of x and y, otherwise they would be 0
	int startX, startY; //The bullets starting coordinates, can be changed with a parameter
	int speed; //How fast bullets move, can be changed with a parameter
	//int intVelocityX, intVelocityY; //Needed to parse velocityX and velocityY into ints
	BufferedImage pic; //Sets this bullet picture to a variable

	public Bullet(int startX,int startY,double targetX,double targetY, BufferedImage pic, int speed, boolean friendly){ //Bullet class, can calculate how the bullet travels and which bullet picture to use
		int offsetX = pic.getWidth()/2 ; //Offset applied as in some cases the bullet spawns in the top right of the starting sprite, this difference can mainly be seen when player shoots bullets 
		int offsetY = pic.getHeight()/2;
		x=startX-offsetX;
		y=startY-offsetY;
		targetX-=offsetX;
		targetY-=offsetY;
		trueX=x;
		trueY=y;
		this.speed = speed;
		this.friendly=friendly;
		this.pic=pic; //Sets this variable to the pics specified, allowing different bullet pictures to be used depending on the parameter
		damage=1; //How much points of damage a bullet will do
		width=pic.getWidth(); //making the dimentions the right size
		height=pic.getHeight(); 
		bounds.width=width;
		bounds.height=height;
		health=1;
		
		//calculating bullets path
		bulletPath = Math.atan2(targetY-y, targetX-x); //calculates the angle of the bullet needs to travel to reach the target coordinates
		velocityX = speed*Math.cos(bulletPath); //Using the bullet path angle calculates the direction and speed needed to move along the x axis
		velocityY = speed*Math.sin(bulletPath); //Using the bullet path angle calculates the direction and speed needed to move along the Y axis
	}
	

	@Override
	public void update(){
		ArrayList<Entity> collisions;
		
		trueX+=velocityX; //Applies the velocity the the true variable, allowing the bullet to move in a specific direction and speed depending on the value of velocity
		trueY+=velocityY;
		x=((int)(trueX)); //Sets x to the trueX (including the offset) this updates x moving the bullet
		y=((int)(trueY)); //Sets y to the trueY (including the offset) this updates y moving the bullet
		//intVelocityX = ((int)(velocityX)); //VelocityX and Y are parsed into these int values, allowing us to use the checkwall method. As checkwall only uses ints when these values are normally doubles
		//intVelocityY = ((int)(velocityY));
		
		//updating the hitboxes location
		bounds.setLocation(x,y);
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		checkWalls((int)(velocityX),(int)(velocityY));														//
	//destroying the bullet if it hits an enemy
		if(friendly) {//only bullets made by the player and towers will break once they hit things
			collisions=entityCollide();
			for(Entity e:collisions) {
				if(e.isFriendly()==false) {//if it is touching an enemy...
					killed=true;//destroy the bullet next frame
					break;//and quit the loop
				}
			}
		}
		
	}																														//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
	@Override
	public void render(Graphics g, Camera camera){ //Renders bullets, depending on the value of bulletType the bullet sprite will be different, the x and y will also grow or shrink depending on the bullet picture, as x and y subtract the bullet pictures width and height respectively
		g.drawImage(pic, x-camera.getxOffset(),y- camera.getyOffset(), null);
	}
	
	private void checkWalls(int xVelocity, int yVelocity) {
		if(GameState.getFloor().checkwall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			killed = true;//When this is set to true the bullet will be removed from entityManager, thus disappearing		//	Wall collisions using the checkwall method in floors 
		}																								 					//
		if(GameState.getFloor().checkwall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			killed = true;																									//
		}																													//
		if(GameState.getFloor().checkwall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			killed = true;																									//
		}																													//
		if(GameState.getFloor().checkwall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			killed = true;																									//
		}															
	}
}

