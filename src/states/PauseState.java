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
	private int selection=0;//where they are in the menu
	private Menu main=new Menu(new String[]{"continue", "options", "quit"}), //creating all the menus/sub menus
			options=new Menu(new String[]{"screenshake", "tower info", "back"});
	
	public PauseState(GameState game) {
		this.game=game;
	}

	@Override
	public void update() {
		Mode initialMenu=currentMenu;
		getInputs().update();
		if(getInputs().isUpPushed()) {
			selection--;//moving up if they press up
		}
		if(getInputs().isDownPushed()) {
			selection++;//moving down the menu if they press down
		}
		switch (currentMenu) {
		case MAIN:
			mainMenu();//doing main pause menu stuff if they are on the main pause menu
			break;
		case OPTIONS:
			options();//doing options stuff if they are in the options menu
			break;	
		}
		if (initialMenu!=currentMenu) {//checking if you have switched menus
			selection=0;//moving you back to the top of the menu
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(0, 0, Window.getDisplay().getWidth()/3, Window.getDisplay().getHeight()/3);//filling the background
		//drawing the rombus for the menu in the middle
		g.drawImage(Assets.MenuPic,Window.getDisplay().getWidth()/6-100,Window.getDisplay().getHeight()/6-80,null);
		g.setColor(Color.WHITE);//setting the colour for the menu text
		g.setFont(Assets.bigpixelfont);//setting the font
		
		//drawing the right menu text
		switch(currentMenu) {
		case MAIN:
			drawMenu(g, main);
			break;
		case OPTIONS:
			drawMenu(g, options);
		
		}		
	}
	
	private void drawMenu(Graphics g, Menu menu) {
		for(int i=0;i<menu.getOptions().length;i++) {
			if(i==menu.getSelected()) {
				g.setColor(new Color(1,1,26));//setting the background shadow colour
				//drawing the shadow of the option
				g.drawString(menu.getOptions()[i].toUpperCase(), Window.getDisplay().getWidth()/6-84+i*7,
						Window.getDisplay().getHeight()/6-59+i*14);
				g.drawString(menu.getOptions()[i].toUpperCase(), Window.getDisplay().getWidth()/6-85+i*7,
						Window.getDisplay().getHeight()/6-59+i*14);
				//drawing the shadow of the value
				g.drawString(menu.getValue(i).toUpperCase(), Window.getDisplay().getWidth()/6-3+i*7,
						Window.getDisplay().getHeight()/6-59+i*14);
				g.drawString(menu.getValue(i).toUpperCase(), Window.getDisplay().getWidth()/6-2+i*7,
						Window.getDisplay().getHeight()/6-59+i*14);
				
				g.setColor(Color.WHITE);//reseting the colour
			}
			//drawing the option
			g.drawString(menu.getOptions()[i].toUpperCase(), Window.getDisplay().getWidth()/6-85+i*7,
					Window.getDisplay().getHeight()/6-60+i*14);
			//drawing the value of the setting if it exists
			g.drawString(menu.getValue(i).toUpperCase(), Window.getDisplay().getWidth()/6-2+i*7,
					Window.getDisplay().getHeight()/6-60+i*14);
		}
	}
	
	private void mainMenu() {
		main.setSelected(selection);//letting the menu know which option is selected
		selection=main.getSelected();//setting selection back in case it has looped
		if(getInputs().isPause()||getInputs().isLeftPushed()) {
			currentState=game;//going back to the game if they go back(press escape or left)
		}else if(getInputs().isRightPushed()) {//selecting the option if they press select(click or right)
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
		options.setSelected(selection);//letting the menu know which option is selected
		selection=options.getSelected();//setting selection back in case it has looped
		
		//puting the values of the options into the menu
		Properties newProperties=Settings.getProperties();
		
		//showing the various options current values
		options.setValue(0,String.valueOf(Settings.getScreenShake()));//letting you know the amount of screen shake
		options.setValue(1, Settings.getTowerInfo());
		
		if(getInputs().isPause()||getInputs().isLeftPushed()) {
			currentMenu=Mode.MAIN;//going back if they go back(press escape or left)
		}else if(getInputs().isRightPushed()) {//selecting the option if they press select(click or right)
			switch(options.getSelected()) {//doing different things depending on the selected option
			
			case 0://screenshake settings
				if(Settings.getScreenShake()==15) {
					newProperties.setProperty("screenShake","0");
				}else if(Settings.getScreenShake()==0) {
					newProperties.setProperty("screenShake","5");
				}else {
					newProperties.setProperty("screenShake",String.valueOf(Settings.getScreenShake()+1));
				}	
				break;
				
			case 1://tower info settings
				if(Settings.getTowerInfo().equals("on")) {
					newProperties.setProperty("towerInfo", "price");
				}else if(Settings.getTowerInfo().equals("price")) {
					newProperties.setProperty("towerInfo", "off");
				}else if(Settings.getTowerInfo().equals("off")) {
					newProperties.setProperty("towerInfo", "on");
				}
				break;
				
			case 2://back button
				currentMenu=Mode.MAIN;//going back for the back option menu
				break;
			}
			Settings.writeproperties(newProperties);
		}
	}
}
