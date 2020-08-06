package graphics.particles.movers;

import java.util.concurrent.ThreadLocalRandom;

public class Straight extends ParticleMover {
	private ConstructorType conType;
	private double moveX, moveY, speed;
	private int spawnWidth, spawnHeight,angle,angleRange;
	
	/**
	 * this doesnt work yet
	 * @param x - x coordinate of top right corner of particle spawning range in pixels
	 * @param y - y coordinate of top right corner of particle spawning range in pixels
	 * @param width - width of spawning box in pixels
	 * @param height - height of spawning box in pixels
	 * @param moveX - how many pixels particles move on the x axis per frame (supports negatives and decimals)
	 * @param moveY - how many pixels particles move on the y axis per frame (supports negatives and decimals)
	 */
	public Straight(int x, int y,int width,int height, double moveX, double moveY) {
		super(ThreadLocalRandom.current().nextInt(x,x+width),ThreadLocalRandom.current().nextInt(y,y+height));
		this.moveX = moveX;
		this.moveY = moveY;
		this.spawnWidth=width;
		this.spawnHeight=height;
		this.speed=moveX+moveY;
		
		conType=ConstructorType.SET_ANGLE_RANDOM_SPAWN;
	}
	
	/**
	 * creates particles that move in the specified range of angles at the specified speed
	 * @param x - x coordinate of particles in pixels
	 * @param y - y coordinate of particles in pixels
	 * @param angle - angle in degrees that they move
	 * @param angleRange - amount of degrees that can be randomly added or subtracted from a particle's direction
	 * @param speed - speed in pixels/frame that particles move
	 */
	public Straight(int x, int y,int angle, int angleRange, double speed) {
		super(x,y);
		
		//the math class uses radians but they dont make sence so i convert degrees to radians
		
		double radians=ThreadLocalRandom.current().nextInt(angle-angleRange, angle+angleRange)*Math.PI/180;
		moveX = speed*Math.cos(radians); //calculates the direction and speed needed to move along the x axis
		moveY = speed*Math.sin(radians); 
		this.speed=speed;
		this.angleRange=angleRange;
		this.angle=angle;
		
		conType=ConstructorType.RANDOM_ANGLE_SET_SPAWN;
	}
	public Straight(int x, int y, double speed) {
		this(x,y,0,360,speed);
	}

	@Override
	public void update() {
		trueX+=moveX;
		trueY+=moveY;
		x=(int)Math.round(trueX);
		y=(int)Math.round(trueY);
		
	}

	@Override
	public ParticleMover copy() {
		switch (conType) {
		case RANDOM_ANGLE_SET_SPAWN:
			return new Straight(x, y, angle, angleRange, speed);
		default:
			return new Straight(x, y,speed);
		}
		
	}

}
