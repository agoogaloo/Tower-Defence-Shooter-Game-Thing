package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import graphics.Assets;
import saveData.Settings;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import window.Window;

public class PauseState extends State{
	private GameState game;
	private PauseState thisState=this;
	
	private Menu menu = new Menu(new Rectangle(20,20,100,8), new MenuObject[] {
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85,
					Window.getDisplay().getRelativeHeight()/2-67, 100,8), "resume") {
				public void select() {
					currentState=game;
				};
			},
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6,
					Window.getDisplay().getRelativeHeight()/2-67+12,100,8), "settings") {
				
				public void select() {
					currentState=new SettingsState(thisState);
				};
			},
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+12,
					Window.getDisplay().getRelativeHeight()/2-67+24, 100,8), "quit") {
				public void select() {
					currentState=new MainMenu();
				};
			},	
	});
	
	
	public PauseState(GameState game) {
		this.game = game;
		menu.select();
	}

	@Override
	public void update() {
		getInputs().update();
		menu.update(getInputs());
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(0, 0, Window.getDisplay().getWidth()/Settings.getScale(), 
				Window.getDisplay().getHeight()/Settings.getScale());//filling the background
		//drawing the rombus for the menu in the middle
		g.drawImage(Assets.MenuPic,Window.getDisplay().getWidth()/(Settings.getScale()*2)-100,
				Window.getDisplay().getHeight()/(Settings.getScale()*2)-80,null);
		menu.render(g);
		
	}

}
