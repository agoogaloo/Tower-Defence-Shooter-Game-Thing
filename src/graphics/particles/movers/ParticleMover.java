package graphics.particles.movers;

import java.awt.Point;

/**
 * particle movers determine how particles should move and things like whether it should obunce or not
 * @author The Computer Man
 *
 */
public abstract class ParticleMover {
	
	protected int x, y;
	protected double trueX, trueY;
	protected boolean remove=false;
	
	public ParticleMover(int x, int y) {
		this.x = x;
		this.y = y;
		trueX = x;
		trueY = y;
	}
	public ParticleMover(Point point) {
		this.x = point.x;
		this.y = point.y;
		trueX = x;
		trueY = y;
	}
	
	public abstract void update();
	
	public abstract ParticleMover copy();
	
	public boolean isRemove() {
		return remove;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
