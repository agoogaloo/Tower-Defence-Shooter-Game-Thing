package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import window.Window;

public class MainMenu extends State{
	MainMenu thisState=this;
	
	private Menu menu = new Menu(new Rectangle(20,20,100,8), new MenuObject[] {
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-30,
					Window.getDisplay().getRelativeHeight()/2+20, 100,8), "Play") {
				public void select() {
					currentState=new GameState();
				};
			},
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-30,
					Window.getDisplay().getRelativeHeight()/2+30,100,8), "Settings") {
				
				public void select() {
					currentState=new SettingsState(thisState);
				};
			},
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-30,
					Window.getDisplay().getRelativeHeight()/2+40, 100,8), "Quit") {
				public void select() {
					Main.Main.quitGame();
				};
			},	
	});
	
	public MainMenu() {
		menu.select();
	}

	@Override
	public void update() {
		getInputs().update();
		menu.update(getInputs());	
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
		menu.render(g);
		
		
	
		
		g.setFont(Assets.smallMonoFont);
		g.drawString("VERSION 0.8.0",2, 7);
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
