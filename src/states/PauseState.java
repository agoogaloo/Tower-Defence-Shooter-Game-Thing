package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Main;
import graphics.Assets;
import saveData.Settings;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.SettingOption;
import window.Window;

public class PauseState extends State{
	private GameState game;
	
	private Menu menu = new Menu(new Rectangle(20,20,100,8), new MenuObject[] {
			new SettingOption(new Rectangle(50,20,100,8),70, "towerInfo", new String[] {"on", "price", "off"}),
			new SettingOption(new Rectangle(60,40,100,8),70, "pixelScale", new String[] {"1","2","3","4","5"}) {
				public void leftPress() {super.leftPress();
				Main.resetWindow();
				};
				public void rightPress() {super.rightPress();
					Main.resetWindow();
				};},
			new SettingOption(new Rectangle(70,60,100,8),70, "screenShake", new String[] {"0","5","6","7","8","9",
					"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"})
				
	});
	
	public PauseState(GameState game) {
		this.game = game;
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
