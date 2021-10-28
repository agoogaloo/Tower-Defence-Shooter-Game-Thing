package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;

public class MainMenu extends State{

	@Override
	public void update() {
		getInputs().update();
		if(getInputs().isSelect()) {
			currentState=new GameState();
		}	
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.infobackground,0,0,null);
		g.drawImage(Assets.infobackground.getSubimage(0, 0,117,100),216,0,null);
		g.drawImage(Assets.infobackground,0,100,null);
		g.drawImage(Assets.infobackground.getSubimage(0, 0,117,100),216,100,null);
		g.setFont(Assets.boldfont);
		g.setColor(new Color(45,135,153));
		g.drawString("IMAGINE A GOOD MENU HERE",83, 71);
		g.setColor(Color.white);
		g.drawString("IMAGINE A GOOD MENU HERE",82, 70);
		
		
	
		g.drawString("CLICK TO START",120, 138);
		
		g.setFont(Assets.smallMonoFont);
		g.drawString("VERSION 0.8.DEV",2, 7);
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
