package states;

import java.awt.Graphics;

public class PauseState extends State{
	private GameState game;
	
	public PauseState(GameState game) {
		this.game=game;
	}

	@Override
	public void update() {
		getInputs().update();
		if(getInputs().isPause()) {
			currentState=game;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawString("its paused now", 50,50);
		
	}

}
