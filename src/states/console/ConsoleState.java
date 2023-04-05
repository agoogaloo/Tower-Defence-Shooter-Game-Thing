package states.console;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import graphics.Assets;
import states.GameState;
import states.State;
import window.Window;

public class ConsoleState extends State{
	GameState game;
	Graphics backGround;
	CommandSelector commandSelector=new CommandSelector(this);
	private String currentLine="", allText="";
	boolean copyBackGround=true;
	//a bunch of booleans showing if certain commands are active or not
	static boolean showHitBoxen=false, gameFrozen=false, instaKillEnemy=false, showFPS=false; 
	public static boolean cheatsUsed = false;
	
	public ConsoleState(GameState game) {
		this.game=game;//this is used so it can continue the game and not restart it when you push f12 again
	}
	
	
	
	
	
    @Override
	public void update() {
		KeyEvent key=getInputs().getTypedKey();
		if(key!=null) {//updating the text if you push something
			updateText(key);
		}
		
		if(getInputs().isConsole()) {
			currentState=game;//continuing the game if you push the console button again
		}
		getInputs().update();
		
		
	}
	
	private void updateText(KeyEvent key) {
		switch(key.getKeyCode()) {
		
		//executing the command you typed when you push enter
		case KeyEvent.VK_ENTER:
			allText+="\n"+currentLine;//adding what you typed to the past text
			//executing the command you typed and printing the output
			allText+="\n"+ commandSelector.executeCommand(currentLine);
			currentLine="";//reseting the current line to nothing
			break;
		//deleting the last character when you push backspace	
		case KeyEvent.VK_BACK_SPACE:
			if(currentLine.length()>0) {//making sure there is text to delete
				//making the current line 1 character shorter
				currentLine=currentLine.substring(0, currentLine.length()-1);
			}
			break;
		}
		//making sure that you pushed a butting that can be typed so it wont try to type keys like the control key 
		if(KeyEvent.getKeyText(key.getKeyCode()).length() == 1||key.getKeyChar()==' ') {
			currentLine=currentLine+=key.getKeyChar();//adding the character you typed to the string
		}
	}
	
	@Override
	public void render(Graphics g) {
		//spliting the textbox with all the past commands into an array because the drawsting method 
		//doesnt work with linebreks
		String[] allTextArray=allText.split("\n");
		
		//clearing the screen so things dont overlap and look weird
		g.clearRect(0, 0, Window.getDisplay().getWidth(),  Window.getDisplay().getHeight());
		//drawing the box that you type in
		g.setColor(new Color(20,20,20));
		g.fillRect(5, 163, 250, 9);
		
		//drawing the text
		g.setColor(new Color(255,255,255));
		g.setFont(Assets.smallMonoFont);
		
		//drawing the line thats being typed in the rectangle at the bottom
		g.drawString(currentLine, 7, 170);
		
		//drawing all the previous outputs from the array at the right place so it scrolls up
		for(int i=0;i<allTextArray.length;i++) {
			g.drawString(allTextArray[i], 5, 165-allTextArray.length*7+i*7);
		}
	}
	
	public static boolean isShowHitBoxen() {
		return showHitBoxen;
	}
	public static boolean isGameFrozen() {
		return gameFrozen;
	}
	public static boolean isInstaKillEnemy() {
		return instaKillEnemy;
	}
	public static boolean isShowFPS() {
		return showFPS;
	}	

}
