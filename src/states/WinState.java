package states;

import java.awt.Color;
import java.awt.Graphics;

import Main.Constants;
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
		g.setColor(new Color(1,1,26));
		g.fillRect(0, 0, Constants.screenWidth, Constants.screenHeight);
		g.setColor(new Color(22,32,102));
		g.fillRect(Constants.screenWidth/2-230/2-10, 19, 250, 31);
		g.fillRect(Constants.screenWidth/2-21*4/2-5, 60, 21*4+10, 16);
		g.setFont(Assets.boldfont);
		g.setColor(new Color(45,135,153));
		g.drawString("THANKS ALOT FOR PLAYING",Constants.screenWidth/2-230/2+1, 41);
		g.setColor(Color.white);
		g.drawString("THANKS ALOT FOR PLAYING",Constants.screenWidth/2-230/2, 40);

	
		//time taken
		g.setFont(Assets.smallMonoFont);

		g.setColor(new Color(45,135,153));
		g.drawString("Time taken: "+String.format("%02d",minutes%60)+":"+
		String.format("%02d",seconds%60) +"."+String.format("%03d",millis%1000)+"", Constants.screenWidth/2-21*4/2+1,71);
		g.setColor(Color.white);
		g.drawString("Time taken: "+String.format("%02d",minutes%60)+":"+
		String.format("%02d",seconds%60) +"."+String.format("%03d",millis%1000)+"", Constants.screenWidth/2-21*4/2, 70);

		g.drawString("IF YOU HAVE ANY SUGGESTIONS AND STUFF THERE'S",Constants.screenWidth/2-45*4/2, 100);
		g.drawString("A DISCORD ON THE ITCH PAGE YOU CAN JOIN",Constants.screenWidth/2-39*4/2, 110);

		g.setColor(new Color(45,135,153));
		if(ConsoleState.cheatsUsed){
			g.drawString("cheat menu used", 5, 10);
			
		}else{
			g.drawString("press f12 in game to open the cheat menu!", 5, 10);
		}
		g.setColor(Color.white);
		g.setFont(Assets.boldfont);
		g.drawString("CLICK TO RESTART",Constants.screenWidth/2-16*10/2, 160);

		
		
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
