package states;

import java.awt.Graphics;

public class MainMenu extends State{

	@Override
	public void update() {
		getInputs().update();
		if(getInputs().isShoot()) {
			currentState=new GameState();
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawString("menu thingy click to start",50, 50);
		
	}

}
