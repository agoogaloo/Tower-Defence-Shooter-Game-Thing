package graphics.particles.movers.spawnPattern;

import java.util.concurrent.ThreadLocalRandom;

/**
 * soawns particles in a rectangular area
 * @author The Computer Man
 *
 */
public class RectangleSpawner extends SpawnPattern{
	private int width,height;
	
	/**
	 * @param x - x value of spawning rectangles top left corner
	 * @param y - y value of spawning rectangles top left corner
	 * @param width - width of spawning rectangle in pixels
	 * @param height - height of spawning rectangle in pixels
	 */
	public RectangleSpawner(int x, int y,int width, int height) {
		super(x, y);
		this.width=width;
		this.height=height;
	}
	
	/**
	 * spawns particles in a square range
	 * @param x - x of top left corner
	 * @param y - y of top left corner
	 * @param size - size of side length of spwning range square in pixels
	 */
	public RectangleSpawner(int x, int y, int size) {
		this(x,y,size,size);
	}

	@Override
	public int getNewX() {
		return ThreadLocalRandom.current().nextInt(x,x+width);
	}

	@Override
	public int getNewY() {
		return ThreadLocalRandom.current().nextInt(y,y+height);
	}
}
