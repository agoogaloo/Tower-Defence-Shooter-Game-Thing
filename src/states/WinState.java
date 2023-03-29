package states;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Assets;
import states.console.ConsoleState;

public class WinState extends State{

	long millis, seconds, minutes;
	public WinState(long startTime) {
		long timeTaken = System.currentTimeMillis() - startTime;
		millis = timeTaken;
		seconds =timeTaken/(1000);
		minutes =seconds/60;
		 
	}

	@Override
	public void update() {
		getInputs().update();
		if(getInputs().isSelect()) {
			currentState=new MainMenu();
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
		g.drawString("THANKS ALOT FOR PLAYING",83, 41);
		g.setColor(Color.white);
		g.drawString("THANKS ALOT FOR PLAYING",82, 40);

	
		g.setFont(Assets.smallMonoFont);

		g.setColor(new Color(45,135,153));
		g.drawString("Time taken: "+": "+String.format("%02d",minutes%60)+":"+
		String.format("%02d",seconds%60) +"."+String.format("%03d",millis%1000)+"", 81,66);
		g.setColor(Color.white);
		g.drawString("Time taken: "+": "+String.format("%02d",minutes%60)+":"+
		String.format("%02d",seconds%60) +"."+String.format("%03d",millis%1000)+"", 80, 65);

		g.drawString("THATS ALL THERE IS FOR NOW, BUT I'LL TRY TO ADD MORE STUFF SOON",25, 80);
		g.drawString("IF YOU HAVE ANY SUGGESTIONS, FEEDBACK, OR ANYTIHNG ELSE",40, 110);
		g.drawString("YOU CAN JOIN THE DISCORD OR SOMETHING",10, 130);

		if(ConsoleState.cheatsUsed){
			g.setColor(new Color(45,135,153));
			g.drawString("cheat menu used", 2, 7);
			g.setColor(Color.white);
		}
		g.setFont(Assets.boldfont);
		g.drawString("CLICK TO RESTART ",112, 160);

		
		
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
