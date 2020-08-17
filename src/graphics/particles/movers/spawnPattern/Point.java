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
	public int getNewX() {
		return x;		
	}

	@Override
	public int getNewY() {
		return y;
	}
	
	

}
