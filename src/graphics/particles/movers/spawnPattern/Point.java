package graphics.particles.movers.spawnPattern;

/**
 * this spawns all particles at one single point
 * @author The Computer Man
 *
 */
public class Point extends SpawnPattern{

	public Point(int x, int y) {
		super(x, y);
	}

	@Override
	public java.awt.Point getNewCoord() {
		return new java.awt.Point(x, y);
	}
	
	

}
