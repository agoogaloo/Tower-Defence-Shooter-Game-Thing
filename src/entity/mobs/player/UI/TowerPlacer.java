package entity.mobs.player.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.statics.towers.Tower;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.PicElement;
import graphics.UI.TextElement;
import saveData.Settings;
import states.State;

public class TowerPlacer {
	// an enum that will be used to tell whether the player is placing or upgrading a tower or just doing nothing
	private enum Mode{PLACING,UPGRADING,WAITING}
	private Tower[] towers;
	private Tower selectedTower;
	private char mouseUpDown='n',mouseLeftRight='n';//set to l,r,u,d,or n depending on where the mouse is
	private PicElement wheelMenu=new PicElement(100, 0, Assets.blank);
	private BufferedImage menuPic;
	private Graphics2D menuGraphics;
	private TextElement infoText=new TextElement(100, 0,7,"");
	private PicElement background=new PicElement(100, 0, Assets.blank);//created 1st so it will be at the back
	private int x, y, moneySpent;
	private Mode mode=Mode.WAITING;
	
	private void updateMouseAngle() {
		if(State.getInputs().getMouseY()-y>7) {
			//this means the mouse is in the bottom half
			mouseUpDown='d';
		}else if(State.getInputs().getMouseY()-y<-7) {
			//this means it is on the top of the circle
			mouseUpDown='u';
		}else{
			//otherwise it is in the middle
			mouseUpDown='n';
		}
		if(State.getInputs().getMouseX()-x>7) {
			//this means it is to the right
			mouseLeftRight='r';
		}else if(State.getInputs().getMouseX()-x<-7) {
			//this means it is to the left
			mouseLeftRight='l';
		}else {
			mouseLeftRight='n';
		}	
	}
	private Tower place(int money,Camera camera, char direction) {
		Tower hoveredTower=null;
		if(mouseUpDown=='u') {
			if(mouseLeftRight=='l') {
				hoveredTower=towers[0];
			}else if(mouseLeftRight=='r') {
				hoveredTower=towers[1];
			}
		}else if(mouseUpDown=='d') {
			if(mouseLeftRight=='l') {
				hoveredTower=towers[2];
			}else if(mouseLeftRight=='r') {
				hoveredTower=towers[3];
			}
		}
		
		if(hoveredTower==null) {
			infoText.update("");
			return null;
			}
		
		infoText.update(hoveredTower.getInfoText());
		if(!State.getInputs().isPlace()&&money>=hoveredTower.getPrice()) {
			moneySpent=hoveredTower.getPrice();
			return hoveredTower.createNew(x+camera.getxOffset(), y+camera.getyOffset());
		}
		return null;
	}
	private Tower upgrade(int money,Camera camera) {		
		if(mouseUpDown=='u') {
			infoText.update(selectedTower.hover(mouseLeftRight));
			if(!State.getInputs().isPlace()) {
				moneySpent+=selectedTower.upgrade(mouseLeftRight, money);
			}
		}else if(mouseUpDown=='d') {
			infoText.update("sell for $"+selectedTower.getSellValue());
			if(!State.getInputs().isPlace()) {
				moneySpent=-selectedTower.getSellValue();//giving some money back when the tower is sold
				selectedTower.destroy();//destroying the selected tower
			}
		}else {
			infoText.update("");
		}
		return null;
	}
	public Tower update(int money,Tower[] towers,Camera camera, ArrayList<Entity> entities, char direction) {
		Tower tower=null;//the tower that will be returned at the end of the method
		this.towers=towers;
		//determining if the player is waiting, placing, or upgrading a tower
		if(State.getInputs().isPlace()) {//checking if the right click is pressed
			if(mode==Mode.WAITING) {//checking if they just pressed the button this frame
				x=State.getInputs().getMouseX();//setting the location of the menu 
				y=State.getInputs().getMouseY();//and where the tower will be placed
				infoText.move(x-55, y+29);
				mode=Mode.PLACING;
			}
		}
		for(Entity e:entities) {
			if(e instanceof Tower&&e.getBounds().contains(x+camera.getxOffset(), y+camera.getyOffset())){
				if(mode==Mode.WAITING) {
					//TODO: hover over tower
					
				}else if(mode==Mode.PLACING) {
					mode=Mode.UPGRADING;
					selectedTower=(Tower)(e);//making the entity a tower so it can it can be set as the selected tower
				}
			}
		}
		
		updateMouseAngle();
		if(mode==Mode.PLACING) {
			tower = place(money,camera, direction);
		}else if(mode==Mode.UPGRADING) {
			tower=upgrade(money,camera);
		}else if(mode==Mode.WAITING) {
			
		}
		if(!State.getInputs().isPlace()) {
			mode=Mode.WAITING;//if the button is not being pushed the mode can be set back to waiting
		}
		
		//modifying the text based on what tower info settings are chosen
		if(Settings.getTowerInfo().equals("off")) {
			infoText.update("");//removing the info text if it is turned off in settings
		}else if(Settings.getTowerInfo().equals("price")) {
			//only showing the price by updating the text to only show the 1st line
			infoText.update(infoText.getText().split("\n")[0]);
		}
		return tower;
	}
	public void render(Graphics g, Camera camera) {
		//drawing the text background if there is upgrade/placing tower text
		if(infoText.getText()!="") {
			//making the image the right size
			background.update(Assets.infobackground.getSubimage(0, 0,120, infoText.getHeight()+6));
			background.move(x-60, y+27);//putting it into the right place
		}else {
			background.update(Assets.blank);//making the background disappear if there is no text
		}
		menuPic= new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
		menuGraphics= menuPic.createGraphics();
		//menuGraphics.clearRect(0, 0, 50, 50);
		if(mode==Mode.PLACING) {
			menuGraphics.drawImage(towers[0].getBuyIcon().getSubimage(0, 0, 25, 25), 0,0, null);
			menuGraphics.drawImage(towers[1].getBuyIcon().getSubimage(25, 0, 25, 25), 25,0, null);
			menuGraphics.drawImage(towers[2].getBuyIcon().getSubimage(0, 25, 25, 25), 0,25, null);
			menuGraphics.drawImage(towers[3].getBuyIcon().getSubimage(25, 25, 25, 25), 25,25, null);
		}else if(mode==Mode.UPGRADING) {
			menuGraphics.drawImage(Assets.towerIcons[2],0,0, null);
			menuGraphics.drawImage(selectedTower.getUpgradeIcon(),0,0, null);
			selectedTower.showRange(g, camera);
			
		}else {
			infoText.update("");
			//topPic.update(Assets.blank);
			//bottomPic.update(Assets.blank);
		}
		wheelMenu.update(menuPic);
		wheelMenu.move(x-25, y-25);
	}
	
	public int getSpentMoney() {
		int money=moneySpent;
		moneySpent=0;
		return money;
	}
}
