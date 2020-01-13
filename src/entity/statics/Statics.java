package entity.statics;

import entity.Entity;

// @author Matthew Milum
public abstract class Statics extends Entity {
	/*
	 *this is for entities that never move such as the core, heath, or money drops
	 */
	@Override
	public void move() {
		//static entities need the move method but can't move
		//so it is left blank here so you don't need to do it in every class
	}
}
