package graphics.particles.movers.spawnPattern;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * spawns particles in a circular area
 * @author The Computer Man
 *
 */
public class CircleSpawn extends SpawnPattern{
	private int radius;
	
	
	public CircleSpawn(int x, int y,int radius) {
		super(x, y);
		this.radius=radius;
	}
	

	@Override
	public Point getNewCoord() {
		double angle=ThreadLocalRandom.current().nextDouble(0, 360);//getting a random angle
		angle=Math.toRadians(angle);//converting to radians
		double radius = this.radius*Math.sqrt(ThreadLocalRandom.current().nextDouble());
		
		int circleX =(int) Math.round(radius*Math.cos(angle));
		int circleY =(int) Math.round(radius*Math.sin(angle));
		return new Point(x+circleX, y+circleY);
	}
}
