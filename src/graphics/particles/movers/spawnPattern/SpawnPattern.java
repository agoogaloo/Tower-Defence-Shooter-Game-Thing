package graphics.particles.movers.spawnPattern;

import java.awt.Point;

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
	
	public abstract Point getNewCoord();
	
}
