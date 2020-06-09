package states;

import java.awt.Graphics;

import inputs.Inputs;

public abstract class State {
	/*
	 * states are the different "modes" the game can be in like being paused or on the main menu they 
	 * let us hold an entire state in one place so when the game is paused we dont need to store everything
	 * somewhere and then reload it when it is unpaused 
	 */
	//the current state the game is in
	protected static State currentState;
	private static Inputs inputs=new Inputs();
	
	public static void init() {
		currentState=new MainMenu();
	}
	public abstract void update();
	public abstract void render(Graphics g);
	
	public static State getState() {
		return currentState;
	}
	public static Inputs getInputs() {
		return inputs;
	}
	
	
}
