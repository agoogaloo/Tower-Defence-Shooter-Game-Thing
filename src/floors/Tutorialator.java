package floors;

import java.awt.Color;
import java.awt.Graphics;

import Main.Constants;
import entity.Entity;
import entity.mobs.Bullet;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.GreenEnemy;
import entity.mobs.enemy.RedEnemy;
import entity.mobs.enemy.TankBot;
import entity.mobs.enemy.TutorialEnemy;
import entity.mobs.enemy.spawner.SpawnButton;
import entity.statics.Chest;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import entity.statics.towers.wizard.WizardTowerlvl2;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.TextElement;
import saveData.SaveData;
import states.GameState;

/*
 * things that need to be taught
 * - towers 
 * - enemies shooting?
 *
 * - core taking damage\,
 * 
 * 
 */
public class Tutorialator {
	private String textToAdd=""; 
	private TextElement text;
	private Chest chest = new Chest(488, 344);
	SpawnButton button = new SpawnButton();
	private boolean sectionInit=false;
	private int section=1, sectionTime=0;
	
	public Tutorialator() {
		 text=new TextElement(52, 172, "");
		 button.remove();
		 Entity.getEntityManager().getSpawner().setUseHeli(false);
		 Entity.getEntityManager().addEntity(chest);
		
	}
	public void update() {
		
		
		switch(section) {
		case 1:
			introSection();
			break;
		case 2:
			spinSection();
			break;
		case 3:
			invincibilitySection();
			break;
		case 4:
			coreSection();
			break;
		case 5:
			towerSection();
			break;
		case 6:
			upgradesSection();
			break;
		case 7:
			section4();
			break;
		case 8:
			section5();
			break;
		case 9:
			section6();
			break;
		case 10:
			section7();
			break;
		}
		
		
		if(textToAdd.length()>0) {
			text.update(text.getText()+textToAdd.charAt(0), 180);
			text.centre();
			text.move(Constants.screenWidth/2-text.getWidth()/2+5,Constants.screenHeight-15-text.getHeight()/2);
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
	private void introSection() {
		//lets you experiment/enemy=bad
		if(!sectionInit) {//initializing the section
			 Entity.getEntityManager().addEntity(new TutorialEnemy(141, 475,Enemy.DOWN, 0,999999999,1));
			 sectionInit=true;
		}
		if(sectionTime==180)
			textToAdd="Robots are bad. Shoot the robot with left click to kill it";
		if(!areEnemies()) {
			
			nextSection();
		}
	}
	private void spinSection() {
		//shows spinning
		if(!sectionInit) {//initializing the section
			 Entity.getEntityManager().addEntity(new TutorialEnemy(92, 475,Enemy.RIGHT, 0,999999999,20));
			 Entity.getEntityManager().addEntity(new TutorialEnemy(191, 475,Enemy.LEFT, 0,999999999,20));
			 sectionInit=true;
		}
		
		
		if(sectionTime==10)
			
			textToAdd="right click to do a melee spin attack. killing enemies with it gives you X2 the reward";
		if(!areEnemies()) {
			GameState.getFloor().getRoom(0, 0).unlock(3);
			
			nextSection();
		}
	}
	private void invincibilitySection() {
		//shows spinning
		if(!sectionInit) {//initializing the section
			 sectionInit=true;
		}
		if(sectionTime%10==0) {
			 Entity.getEntityManager().addEntity(new Bullet(130, 327,160,327,Assets.yellowBullet, 1,60,false));
			 Entity.getEntityManager().addEntity(new Bullet(130, 337,160,337,Assets.yellowBullet, 1,60,false));
			 Entity.getEntityManager().addEntity(new Bullet(130, 347,160,347,Assets.yellowBullet, 1,60,false));
		}
		
		if(sectionTime==10) {
			textToAdd="the spin attack also lets you slice through bullets without taking any damage.";
		}
		if(Entity.getEntityManager().getPlayer().getY()<=275) {
			GameState.getFloor().getRoom(0, 0).getDoors().get(3).reLock();

			nextSection();
		}
	}
	
	
	private void coreSection() {
		//teaches enemies following paths/coreness
		if(!sectionInit) {//initializing the section
			
			 
			 sectionInit=true;
		}
		
		
		if(sectionTime==10) {
			textToAdd="the big blue heart is your core thingy. You take damage if an enemy touches it, so you should stop them before they reach it";
		}
		if(sectionTime==30) {
			Entity.getEntityManager().addEntity(new TutorialEnemy(380, 182,Enemy.LEFT, 0.5, 999999999,50));
		}
		
		if(!areEnemies()&&sectionTime>30) {
			nextSection();
		}
	}
	
	private void towerSection() {
		//towers
		boolean hasTower=false;	
		
		if(sectionTime==10) {
			
			Entity.getEntityManager().addEntity(new TowerSpawn(295, 155,true));
			Entity.getEntityManager().addEntity(new TowerSpawn(245, 140,true));
			Entity.getEntityManager().addEntity(new TowerSpawn(85, 150,true));
			
			textToAdd="you can place towers to help you by pressing shift/e while hovering over tower platforms and releasing over the tower you want";
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
	private void upgradesSection() {
		//towers
		
		if(sectionTime==10) {
			
			textToAdd="right clicking a placed tower will let you upgrade or sell it";
		}
		
		for(Entity e: Entity.getEntityManager().getEntities()) {
			if(e instanceof WizardTowerlvl2) {
				GameState.getFloor().getRoom(0, 0).unlock(1);	
				text.update("");
				textToAdd="";
			}
		}
	
		if(Entity.getEntityManager().getPlayer().getX()>=350) {//going to the next section if they upgraded a tower
			nextSection();
		}
	}
	
	private void section4() {
		if(!sectionInit) {//initializing the section
			
			sectionInit=true;
		}
		
		if(sectionTime==10) 
			textToAdd="enemies will try to shoot back at you. try not to get hit.";
		else if(sectionTime==70) Entity.getEntityManager().addEntity(new TutorialEnemy(464, 16,Enemy.DOWN, 0.8,50,60));
		
		if(!areEnemies()&&sectionTime>70) {
			nextSection();
			GameState.getFloor().getRoom(0, 0).unlock(2);
			
		}
			
	}
	private void section5() {
		if(!sectionInit) {//initializing the section
			
			sectionInit=true;
		}
		if(sectionTime==10) {
			textToAdd="shoot the chest in the next room to open it";
		}
		if(chest.isKilled()) {
			nextSection();
		}
	}
	private void section6() {
		
		if(!sectionInit) {//initializing the section
			
			button.create();
			sectionInit=true;
		}
		button.update(0, 0 );
		button.move(464, 64);
		if(sectionTime==10) 
			textToAdd="as your final challenge you need to beat a wave of enemies. press the red button in the corner to start";
		
		if(button.isClicked()) {
			nextSection();
			button.remove();
		}
		
	}
	
	private void section7() {
		
		switch(sectionTime) {
		case 15:
			Entity.getEntityManager().addEntity(new RedEnemy(464, 16,Enemy.DOWN));
			break;
		case 60:
			Entity.getEntityManager().addEntity(new GreenEnemy(464, 16,Enemy.DOWN));
			break;
		case 60*4:
			Entity.getEntityManager().addEntity(new RedEnemy(464, 16,Enemy.DOWN));
			break;
		case (int) (60*5.5):
			Entity.getEntityManager().addEntity(new TankBot(464, 16,Enemy.DOWN));
			break;
		case 60*7:
			Entity.getEntityManager().addEntity(new RedEnemy(464, 16,Enemy.DOWN));
			break;
			
		}
		if(!areEnemies()&&sectionTime>60*9) {
			text.move(79, 172);
			GameState.getFloor().getRoom(0, 0).unlock(0);
			textToAdd="congratulations you have beaten the tutorial";
			SaveData.tutorialDone();
			section++;//going to the next section even though it doesnt exist to make this one stop updating
			
		}
		
		
	}


	public void render(Graphics g, Camera camera) {
		button.render(g, camera);
		g.setColor(new Color(23,70,79));
		if(text.getText().length()>0) {
			g.fillRoundRect( text.getX()-3,text.getY(), text.getWidth()+5, text.getHeight()+3, 7, 7);
			//g.setColor(Color.white);
			//g.drawRect(77, 170, 190, 20 );
		}
	}
}
