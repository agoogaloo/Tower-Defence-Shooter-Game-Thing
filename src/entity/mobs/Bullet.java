package entity.mobs;
//@author Kevin (all of bullet except for a few parts)
//@author Matthew (trueX, trueY, width, height, damage, friendly, move())

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.RenderLayer;
import entity.mobs.enemy.StatusEffect;
import entity.mobs.enemy.StatusType;
import entity.mobs.pickups.Pickup;
import graphics.Camera;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;

public class Bullet extends Mobs{

	private double bulletPath; //Calculates the path to the target
	private double velocityX,velocityY, trueX, trueY; //Velocity is how fast bullet travels and it's direction, trueX and trueY are the actual locations of x and y, otherwise they would be 0
	//int intVelocityX, intVelocityY; //Needed to parse velocityX and velocityY into ints
	BufferedImage pic; //Sets this bullet picture to a variable
	int time;
	
	public Bullet(int startX,int startY,double targetX,double targetY, BufferedImage pic, double speed,int time, boolean friendly){
		this(startX, startY, targetX, targetY, pic, speed,time, 1, friendly);
	}
	
	public Bullet(int startX,int startY,double targetX,double targetY, BufferedImage pic, double speed,int time, double damage, boolean friendly){
		this(startX, startY, targetX, targetY, pic, speed,time, damage, new StatusEffect(StatusType.NONE,0,0), friendly);
	}
	
	public Bullet(int startX,int startY,double targetX,double targetY, BufferedImage pic, double speed, int time,
			double damage,StatusEffect effect, boolean friendly){ //Bullet class, can calculate how the bullet travels and which bullet picture to use
		this.time=time;
		this.damage=damage;
		this.statusDealt=effect.copy();
		this.friendly=friendly;
		this.pic=pic; //Sets this variable to the pics specified, allowing different bullet pictures to be used depending on the parameter
		layer=RenderLayer.FRONT;
		
		int offsetX = pic.getWidth()/2 ; //Offset applied as in some cases the bullet spawns in the top right of the starting sprite, this difference can mainly be seen when player shoots bullets 
		int offsetY = pic.getHeight()/2;
		x=startX-offsetX;
		y=startY-offsetY;
		targetX-=offsetX;
		targetY-=offsetY;
		trueX=x;
		trueY=y;
		
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
		
		trueX+=velocityX; //Applies the velocity the the true variable, allowing the bullet to move in a specific direction and speed depending on the value of velocity
		trueY+=velocityY;
		x=((int)(trueX)); //Sets x to the trueX (including the offset) this updates x moving the bullet
		y=((int)(trueY)); //Sets y to the trueY (including the offset) this updates y moving the bullet
		
		time--;
		if(time<=0) {
			destroy();
		}
		
		//updating the hitboxes location
		bounds.setLocation(x,y);

		checkWalls((int)Math.round(velocityX),(int)Math.round(velocityY));
		
	}
	
	@Override
	public void damage() {
		for (Entity e : entityCollide()) {//checking what is colliding with itself
			//checking which side the thing that touched it is on 
			//(making sure enemies only attack the player, player can't attack the core, etc.)
			if ((e.isFriendly() != friendly||e.isSolid())&&e.hasCollisions()&&!(e instanceof Pickup)&&!(e instanceof Bullet)) {
				destroy();
			}
		}
	}
	@Override
	public void render(Graphics g, Camera camera){ //Renders bullets, depending on the value of bulletType the bullet sprite will be different, the x and y will also grow or shrink depending on the bullet picture, as x and y subtract the bullet pictures width and height respectively
		g.drawImage(pic, x-camera.getxOffset(),y- camera.getyOffset(), null);
	}
	
	private void checkWalls(int xVelocity, int yVelocity) {
		if(GameState.getFloor().checkWall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			destroy();//When this is set to true the bullet will be removed from entityManager, thus disappearing		//	Wall collisions using the checkwall method in floors 
		}																								 					//
		if(GameState.getFloor().checkWall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			destroy();																								//
		}																													//
		if(GameState.getFloor().checkWall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			destroy();																								//
		}																													//
		if(GameState.getFloor().checkWall((x+xVelocity)/16,(y+yVelocity)/16)){ 					//
			destroy();																									//
		}															
	}
	@Override
	public void destroy() {
		if(killed) {
			return;
		}
		killed=true;
		new InstantEffect(5, new Straight(new Point(x+width/2,y+height/2),0.75), 
				new OvalParticle(2, new Timed(10)), false);
		
	}
}

