package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import graphics.Assets;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import window.Window;

public class DeadState extends State{

	private int floorIndex;
	private int gameUpdates=30
			,updatespeed=3;
	BufferedImage background;
	GameState gameState;
	Menu menu;

	long timeTaken;
	
	public DeadState(GameState gameState, long startTime) {
		this.gameState=gameState;
		timeTaken = System.currentTimeMillis()-startTime;
		floorIndex=GameState.getFloorIndex();
		
		menu = new Menu(new Rectangle(), new MenuObject[] {
				new MenuSelection(new Rectangle(90, 130,45,10), "RESTART",Color.white,Color.red) {
					public void select() { 
						if(floorIndex==GameState.TUTORIALINDEX) {
							currentState=new GameState(GameState.TUTORIALINDEX);
						}else {
							currentState=new GameState(GameState.FLOOR1);
						}
						
					};
				},new MenuSelection(new Rectangle(180, 130,50,10), "BACK TO HUB", Color.white,Color.red) {
					public void select() { currentState=new GameState();};
				}
		});
		
		background=new BufferedImage(Window.getDisplay().getRelativeWidth(),
				Window.getDisplay().getRelativeHeight(),BufferedImage.TYPE_INT_ARGB);
		
		gameState.render(background.getGraphics());
		
		menu.select();
	}
	@Override
	public void update() {
		if(gameUpdates>0) {
			gameUpdates--;
			if(gameUpdates%updatespeed==0) {
				gameState.update();
				gameState.render(background.getGraphics());
			}
			return;
		}
		getInputs().update();
		menu.update(getInputs());		
	}

	@Override
	public void render(Graphics g) {
		long millis = timeTaken;
		long seconds =timeTaken/(1000);
		long minutes =seconds/60;
		g.drawImage(background,0,0,null);
		if(gameUpdates>0) {
			return;
		}
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(Window.getDisplay().getRelativeWidth()/2-125,Window.getDisplay().getRelativeHeight()/2-75,
		250,150); 
		g.setFont(Assets.boldfont);
		g.setColor(Color.red);
		g.drawString("YOU DIED",135, 41);
		g.setColor(Color.white);
		g.drawString("YOU DIED",134, 40);
		
		g.setFont(Assets.smallMonoFont);
		String floor="";
		switch (floorIndex) {
		case GameState.HUBINDEX:
			floor="hub";
			break;
		case GameState.TUTORIALINDEX:
			floor="tutorial";
			break;

		case GameState.FLOOR1:
			floor="1";
			break;
		case GameState.FLOOR2:
			floor="2";
			break;
		}
		g.drawString("FLOOR"+": "+floor.toUpperCase(),145, 70);
		g.drawString("KILLS"+": "+Entity.getEntityManager().getKills(),145, 80);
		g.drawString("MONEY"+": "+Entity.getEntityManager().getPlayer().getMoney(),145, 90);
		g.drawString("TIME"+": "+String.format("%02d",minutes%60)+":"+String.format("%02d",seconds%60) +
		"."+String.format("%03d",millis%1000)+"",145, 100);
		menu.render(g);
				
		
		/*g.drawLine(333/2,0, 333/2,200);
		g.drawLine(333/2-50,0, 333/2-50,200);
		g.drawLine(333/2+50,0, 333/2+50,200);
		
		g.drawLine(0,200/3, 333,200/3);
		g.drawLine(0,400/3,333, 400/3);*/
		
	}

}
