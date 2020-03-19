package entity.mobs;
//@author Kevin (all of bullet except for a few parts)
//@author Matthew (trueX, trueY, width, height, damage, friendly, move())

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Camera;
import states.GameState;

public class Bullet extends Mobs{

	double bulletPath; //Calculates the path to the target
	double velocityX,velocityY, trueX, trueY; //Velocity is how fast bullet travels and it's direction, trueX and trueY are the actual locations of x and y, otherwise they would be 0
	int startX, startY; //The bullets starting coordinates, can be changed with a parameter
	int speed; //How fast bullets move, can be changed with a parameter
	int bulletType; //Depending on value a different bullet picture will be used, can be changed with a parameter 
	int intVelocityX, intVelocityY; //Needed to parse velocityX and velocityY into ints
	BufferedImage [] bulletPics = Assets.bullet; //Sets this bullet picture to a variable

	public Bullet(int startX,int startY,double targetX,double targetY, int pic, int speed, boolean friendly){ //Bullet class, can calculate how the bullet travels and which bullet picture to use
																											   //0 is an enemy bullet 1 is a friendly bullet
		this.speed = speed;
		this.friendly=friendly;
		x = startX; //Set x and y to the startX and startY given
		y = startY;	
		bulletType = pic; //Sets this variable to the pics specified, allowing different bullet pictures to be used depending on the parameter
						   //0 is for small yellow bullets, 1 is for large yellow bullets, 2 is for small red bullets, 3 is for larger red bullets
		trueX=x; //Sets x and y to trueX and trueY, the actual location of x and y as they move
		trueY=y;
		damage=1; //How much points of damage a bullet will do
		width=6; //Specific width of a bullet
		height=6; //Specific height of a bullet
		health=1;
		bulletPath = Math.atan2(targetY-y, targetX-x); //calculates the angle of the bullet needs to travel to reach the target coordinates
		velocityX = speed*Math.cos(bulletPath); //Using the bullet path angle calculates the direction and speed needed to move along the x axis
		velocityY = speed*Math.sin(bulletPath); //Using the bullet path angle calculates the direction and speed needed to move along the Y axis
	}
	

	@Override
	public void update(){
		updateBounds();
		int offsetX = bulletPics[bulletType].getWidth()/2 ; //Offset applied as in some cases the bullet spawns in the top right of the starting sprite, this difference can mainly be seen when player shoots bullets 
		int offsetY = bulletPics[bulletType].getHeight()/2;
		trueX+=velocityX; //Applies the velocity the the true variable, allowing the bullet to move in a specific direction and speed depending on the value of velocity
		trueY+=velocityY;
		x=((int)(trueX) - offsetX); //Sets x to the trueX (including the offset) this updates x moving the bullet
		y=((int)(trueY) - offsetY); //Sets y to the trueY (including the offset) this updates y moving the bullet
		intVelocityX = ((int)(velocityX)); //VelocityX and Y are parsed into these int values, allowing us to use the checkwall method. As checkwall only uses ints when these values are normally doubles
		intVelocityY = ((int)(velocityY));
		move();
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(GameState.getFloor().checkwall((x+intVelocityX)/16,(y+intVelocityY)/16)){ 					//
			killed = true;//When this is set to true the bullet will be removed from entityManager, thus disappearing		//	Wall collisions using the checkwall method in floors 
		}																								 					//
		if(GameState.getFloor().checkwall((x+intVelocityX)/16,(y+intVelocityY)/16)){ 					//
			killed = true;																									//
		}																													//
		if(GameState.getFloor().checkwall((x+intVelocityX)/16,(y+intVelocityY)/16)){ 					//
			killed = true;																									//
		}																													//
		if(GameState.getFloor().checkwall((x+intVelocityX)/16,(y+intVelocityY)/16)){ 					//
			killed = true;																									//
		}																													//
	}																														//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public void render(Graphics g, Camera camera){ //Renders bullets, depending on the value of bulletType the bullet sprite will be different, the x and y will also grow or shrink depending on the bullet picture, as x and y subtract the bullet pictures width and height respectively
		g.drawImage(bulletPics[bulletType], x-bulletPics[bulletType].getWidth()/2-camera.getxOffset(),
				y-bulletPics[bulletType].getHeight()/2- camera.getyOffset(), null);
	}
}

