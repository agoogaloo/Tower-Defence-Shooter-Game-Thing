package states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Properties;

import graphics.Assets;
import settings.Settings;
import window.Window;

public class PauseState extends State{
	private GameState game;
	private enum Mode{MAIN, OPTIONS}
	private Mode currentMenu=Mode.MAIN;
	private Menu main=new Menu(new String[]{"continue", "options", "quit"}), //creating all the menus/sub menus
			options=new Menu(new String[]{"screenshake", "tower info text", "back"});
	
	public PauseState(GameState game) {
		this.game=game;
	}

	@Override
	public void update() {
		getInputs().update();
		switch (currentMenu) {
		case MAIN:
			mainMenu();//doing main pause menu stuff f they are on the main pause menu
			break;
		case OPTIONS:
			options();
			break;	
		}	
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(1,1,26));
		g.fillRect(0, 0, Window.getDisplay().getWidth()/3, Window.getDisplay().getHeight()/3);
		g.drawImage(Assets.MenuPic,Window.getDisplay().getWidth()/6-100,Window.getDisplay().getHeight()/6-80,null);
		g.setColor(Color.WHITE);
		g.setFont(Assets.myfont);
		g.drawString("IT'S PAUSED NOW", 50,50);
		System.out.println("main:"+main.getSelectedValue()+", settings:"+options.getSelectedValue()+
				", menu:"+currentMenu.toString());
		
	}
	private void mainMenu() {
		if(getInputs().isPause()||getInputs().isLeft()) {
			currentState=game;//going back to the game if they go back(press escape or left)
		}
		if(getInputs().isUp()) {
			main.setSelected(main.getSelected()-1);//moving up if they press up
		}
		if(getInputs().isDown()) {
			main.setSelected(main.getSelected()+1);//moving down the menu if they press down
			
		}else if(getInputs().isPlace()||getInputs().isRight()) {//selecting the option if they press select(click or right)
			switch(main.getSelected()) {//doing different things depending on the selected option
			case 0:
				currentState=game;//going back for the back option
				break;
			case 1:
				currentMenu=Mode.OPTIONS;//going to the options menu
				break;
			case 2:
				currentState=new MainMenu();//quiting to the main menu
			}		
		}
	}
	
	private void options() {
		Properties newProperties=Settings.getProperties();
		if(getInputs().isPause()||getInputs().isLeft()) {
			currentMenu=Mode.MAIN;//going back if they go back(press escape or left)
		}
		if(getInputs().isUp()) {
			options.setSelected(options.getSelected()-1);//moving up if they press up
		}
		if(getInputs().isDown()) {
			options.setSelected(options.getSelected()+1);//moving down the menu if they press down
			
		}else if(getInputs().isPlace()||getInputs().isRight()) {//selecting the option if they press select(click or right)
			switch(options.getSelected()) {//doing different things depending on the selected option
			case 0:
				newProperties.setProperty("towerInfo", Boolean.toString(!Settings.isTowerInfo()));
			case 1:
				
			case 2:
				currentMenu=Mode.MAIN;//going back for the back option menu
				break;
			}
			Settings.writeproperties(newProperties);
		}
	}
}
