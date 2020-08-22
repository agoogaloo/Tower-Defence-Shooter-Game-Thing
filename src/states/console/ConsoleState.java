package states.console;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import graphics.Assets;
import states.GameState;
import states.State;
import window.Window;

public class ConsoleState extends State{
	GameState game;
	Graphics backGround;
	CommandSelector commandSelector=new CommandSelector();
	private String currentLine="";
	private ArrayList<String> allText=new ArrayList<String>();
	boolean copyBackGround=true;
	static boolean showHitBoxen=false;
	
	public ConsoleState(GameState game) {
		this.game=game;
	}
	
	
	@Override
	public void update() {
		KeyEvent key=getInputs().getTypedKey();
		if(key!=null) {
		updateText(key);
		}
		
		getInputs().update();
		
		if(getInputs().isConsole()) {
			currentState=game;
		}
		
		
	}
	
	private void updateText(KeyEvent key) {
		
		switch(key.getKeyCode()) {
		
		case KeyEvent.VK_ENTER:
			allText.add(currentLine);
			allText.addAll(Arrays.asList(commandSelector.executeCommand(currentLine)));
			currentLine="";
			break;
			
		case KeyEvent.VK_BACK_SPACE:
			if(currentLine.length()>0) {
				currentLine=currentLine.substring(0, currentLine.length()-1);
			}
		}
		
		if(KeyEvent.getKeyText(key.getKeyCode()).length() == 1||key.getKeyChar()==' ') {
			currentLine=currentLine+=key.getKeyChar();	
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(copyBackGround) {
			backGround=g;
			copyBackGround=false;
		}
		//g=backGround;
		g.clearRect(0, 0, Window.getDisplay().getWidth(),  Window.getDisplay().getHeight());
		g.setColor(new Color(20,20,20));
		g.fillRect(5, 183, 250, 9);
		g.setColor(new Color(255,255,255));
		g.setFont(Assets.myfont);
		g.drawString(currentLine, 7, 190);
		for(int i=0;i<allText.size();i++) {
			g.drawString(allText.get(i), 7, 185-allText.size()*7+i*7);
		}
	}
	

}
