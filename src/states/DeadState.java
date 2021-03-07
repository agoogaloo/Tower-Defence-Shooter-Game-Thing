package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import graphics.Assets;
import inputs.Inputs;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import window.Window;

public class DeadState extends State{

	private int floorIndex;
	BufferedImage background;
	Menu menu = new Menu(new Rectangle(), new MenuObject[] {
			new MenuSelection(new Rectangle(110, 130,50,10), "SHOOT TO RESTART") {
				public void select() { currentState=new GameState();};
			}
	});
	public DeadState(GameState gameState) {
		floorIndex=GameState.getFloorIndex();
		
		background=new BufferedImage(Window.getDisplay().getRelativeWidth(),
				Window.getDisplay().getRelativeHeight(),BufferedImage.TYPE_INT_ARGB);
		gameState.render(background.getGraphics());
		
		menu.select();
	}
	@Override
	public void update() {
		getInputs().update();
		menu.update(getInputs());
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(background,0,0,null);
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(Window.getDisplay().getRelativeWidth()/2-125,Window.getDisplay().getRelativeHeight()/2-75,250,150); 
		g.setFont(Assets.boldfont);
		g.setColor(new Color(45,135,153));
		g.drawString("YOU DIED",131, 41);
		g.setColor(Color.white);
		g.drawString("YOU DIED",130, 40);
		
		String floor="";
		switch (floorIndex) {
		case GameState.HUBINDEX:
			floor="hub";
			break;
		case GameState.TUTORIALINDEX:
			floor="tutorial";
			break;

		case GameState.FLOOR1:
			floor="1";
			break;
		case GameState.FLOOR2:
			floor="2";
			break;
		}
		g.drawString("FLOOR"+": "+floor.toUpperCase(),70, 70);
		g.drawString("KILLS"+": "+Entity.getEntityManager().getKills(),70, 90);
		g.drawString("MONEY"+": "+Entity.getEntityManager().getPlayer().getMoney(),70, 110);
		menu.render(g);
				
		
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
