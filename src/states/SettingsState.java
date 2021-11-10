package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Main;
import graphics.Assets;
import saveData.Settings;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import states.menus.SettingOption;
import window.Window;

public class SettingsState extends State{
	private State prevState;
	
	private Menu settings = new Menu(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6,
			Window.getDisplay().getRelativeHeight()/2-67+12,100,8), "settings", new MenuObject[] {
					
			new SettingOption(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85,
					Window.getDisplay().getRelativeHeight()/2-67,100,8),80, "towerInfo", new String[] {"on", "price", "off"}),
			
			new SettingOption(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6,
					Window.getDisplay().getRelativeHeight()/2-67+12,100,8),80, "pixelScale", new String[] {"1","2","3","4","5","6"}) {
				public void leftPress() {super.leftPress();
				Main.resetWindow();
				};
				public void rightPress() {super.rightPress();
					Main.resetWindow();
				};},
			
			new SettingOption(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6*2,
					Window.getDisplay().getRelativeHeight()/2-67+12*2,100,8),80, "screenShake", new String[] {"0","5","6","7","8","9",
					"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"}),
			
			new SettingOption(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6*3,
					Window.getDisplay().getRelativeHeight()/2-67+12*3,100,8),80, "cameraSpeed", new String[] {"30","35","40","45",
							"50","55","60","65","70","75","80","85","90","95","100"}){
				public void leftPress() {
					super.leftPress();
					GameState.getCamera().setSpeed(Settings.getCamSpeed()/100f);
				};
				public void rightPress() {
					super.rightPress();
					GameState.getCamera().setSpeed(Settings.getCamSpeed()/100f);
				};},
			
			new SettingOption(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6*4,
					Window.getDisplay().getRelativeHeight()/2-67+12*4,100,8),80, "aimLook", new String[] {"0","5","10","15",
							"20","25","30","35","40","45","50","55","60","65","70","75","80","85","90"}){
				public void leftPress() {
					super.leftPress();
					GameState.getCamera().setMouseWeight(Settings.getAimLook()/100f);
				};
				public void rightPress() {
					super.rightPress();
					GameState.getCamera().setMouseWeight(Settings.getAimLook()/100f);
				};},
			
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6*6,
					Window.getDisplay().getRelativeHeight()/2-67+12*6, 100,8), "restore default") {
					
				public void select() {
					Settings.resetData();
					Main.resetWindow();
					currentState=new SettingsState(prevState);
					
				};
			},	
			new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-85+6*7,
					Window.getDisplay().getRelativeHeight()/2-67+12*7, 100,8), "back") {
				public void select() {
					currentState=prevState;
				};
			},	
	});
	
	
	public SettingsState(State prevState) {
		this.prevState=prevState;
		settings.select();
	}

	@Override
	public void update() {
		getInputs().update();
		settings.update(getInputs());
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(0, 0, Window.getDisplay().getWidth()/Settings.getScale(), 
				Window.getDisplay().getHeight()/Settings.getScale());//filling the background
		//drawing the rombus for the menu in the middle
		g.drawImage(Assets.MenuPic,Window.getDisplay().getWidth()/(Settings.getScale()*2)-100,
				Window.getDisplay().getHeight()/(Settings.getScale()*2)-80,null);
		settings.render(g);
		
	}

}
