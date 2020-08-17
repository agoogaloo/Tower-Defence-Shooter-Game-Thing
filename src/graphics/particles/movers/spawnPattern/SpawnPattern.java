package graphics.particles.movers.spawnPattern;

/**spawn patterns control where particles will spawn but do not influence 
 * where they can end up
 * @author The Computer Man 
 * */

public abstract class SpawnPattern {
	int x,y;
	
	public SpawnPattern (int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	/**
	 * @return - returns a possible starting x coordinate depending on spawning pattern type */
	public abstract int getNewX();
	/**
	 * @return - returns a possible starting x coordinate depending on spawning pattern type */
	public abstract int getNewY();
}
