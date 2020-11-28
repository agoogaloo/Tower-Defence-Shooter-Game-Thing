package floors;

import java.awt.Color;
import java.awt.Graphics;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.GreenEnemy;
import entity.mobs.enemy.RedEnemy;
import entity.mobs.enemy.TutorialEnemy;
import entity.mobs.enemy.YellowEnemy;
import entity.mobs.enemy.spawner.SpawnButton;
import entity.statics.towers.Tower;
import graphics.Camera;
import graphics.UI.TextElement;
import saveData.SaveData;
import states.GameState;

/*
 * things that need to be taught
 * - towers 
 * - enemies shooting?
 * - you shooting?? \,
 * - core taking damage\,
 * 
 * 
 */
public class Tutorialator {
	private String textToAdd=""; 
	private TextElement text;
	SpawnButton button = new SpawnButton();
	private boolean sectionInit=false;
	private int section=1, sectionTime=0;
	
	public Tutorialator() {
		 text=new TextElement(52, 172, "");
		 button.remove();
		
	}
	public void update() {
		
		
		switch(section) {
		case 1:
			section1();
			break;
		case 2:
			section2();
			break;
		case 3:
			section3();
			break;
		case 4:
			section4();
			break;
		case 5:
			section5();
			break;
		case 6:
			section6();
			break;
		}
		
		
		if(textToAdd.length()>0) {
			System.out.println(section);
			text.update(text.getText()+textToAdd.charAt(0));
			textToAdd=textToAdd.substring(1);
		}
		sectionTime++;
		
	}
	
	private void nextSection() {
		text.update("");
		textToAdd="";
		section++;
		sectionTime=0;
		sectionInit=false;
	}
	private boolean areEnemies() {
		for(Entity e: Entity.getEntityManager().getEntities()) {
			if(e instanceof Enemy) {
				return true;
			}
		}
		return false;
		
	}
	private void section1() {
		//lets you experiment/enemy=bad
		if(!sectionInit) {//initializing the section
			 Entity.getEntityManager().addEntity(new TutorialEnemy(142, 480,'d', 0,999999999));
			 sectionInit=true;
		}
		
		text.move(60, 172);
		if(sectionTime==180)
			textToAdd="Robots are bad. Shoot the robot with left click to kill it";
		if(!areEnemies()) {
			GameState.getFloor().getRoom(0, 0).unlock(8, 19);
			nextSection();
		}
	}
	
	private void section2() {
		//teaches enemies following paths/coreness
		if(!sectionInit) {//initializing the section
			 Entity.getEntityManager().addEntity(new TutorialEnemy(380, 160,'l', 0.5, 999999999));
			 text.move(48, 167);
			 sectionInit=true;
		}
		
		
		if(sectionTime==10)
			textToAdd=" the big blue heart is your core thingy. You take damage if an \nenemy touches it, so you should stop them before they reach it";
		
		if(!areEnemies()) nextSection();
	}
	
	private void section3() {
		
		//towers
		boolean hasTower=false;		
		if(sectionTime==10) {
			text.move(48, 167);
			textToAdd="you can place or upgrade towers to help you by holding right \n      click and dragging the mouse over the tower you want";
		}
		
		for(Entity e: Entity.getEntityManager().getEntities()) {
			if(e instanceof Tower) {
				hasTower=true;			
			}
		}
	
		if(hasTower) {//going to the next section if they place a tower
			nextSection();
		}
	}
	
	private void section4() {
		if(!sectionInit) {//initializing the section
			text.move(50, 172);
			sectionInit=true;
		}
		
		if(sectionTime==10) 
			textToAdd="enemies will shoot back at you. make sure you don't get hit.";
		else if(sectionTime==40) Entity.getEntityManager().addEntity(new TutorialEnemy(380, 160,'l', 0.8,50,10));
		
		if(!areEnemies()&&sectionTime>40) {
			nextSection();
			GameState.getFloor().getRoom(0, 0).unlock(21, 8);
			
		}
			
	}
	
	private void section5() {
		
		if(!sectionInit) {//initializing the section
			text.move(85, 167);
			button.create();
			sectionInit=true;
		}
		button.update(0, 0, 'u');
		button.move(464, 64);
		if(sectionTime==10) 
			textToAdd=" as your final callenge you need to beat a \nwave of enemies. press the button to start";
		
		if(button.isClicked()) {
			nextSection();
			button.remove();
		}
		
	}
	private void section6() {
		
		switch(sectionTime) {
		case 15:
			Entity.getEntityManager().addEntity(new RedEnemy(464, 16,'d'));
			break;
		case 60*3:
			Entity.getEntityManager().addEntity(new YellowEnemy(464, 16,'d'));
			break;
		case 60*7:
			Entity.getEntityManager().addEntity(new GreenEnemy(464, 16,'d'));
			break;
		case 60*9:
			Entity.getEntityManager().addEntity(new RedEnemy(464, 16,'d'));
			break;
		}
		if(!areEnemies()&&sectionTime>60*9) {
			text.move(77, 172);
			GameState.getFloor().getRoom(0, 0).unlock(28, 1);
			textToAdd="congratulations you have beaten the tutorial";
			SaveData.tutorialDone();
			section++;//going to the next section even though it doesnt exist to make this one stop updating
			
		}
		
		
	}


	public void render(Graphics g, Camera camera) {
		button.render(g, camera);
		g.setColor(new Color(23,70,79));
		if(text.getText().length()>0)
			g.fillRoundRect(45, 170, 250, 20, 5, 5);
	}
}
