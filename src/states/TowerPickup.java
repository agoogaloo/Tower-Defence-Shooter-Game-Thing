package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.ItemList;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import states.menus.Menu;
import states.menus.MenuObject;
import states.menus.MenuSelection;
import states.menus.PictureSelection;
import window.Window;

public class TowerPickup extends State{

	int towerId;
	Animation towerAnim;
	State previousState;
	int[] currentTowers=Entity.getEntityManager().getPlayer().getTowers();
	Menu menu;
	int towerToSwitch=-1;
	public TowerPickup(int towerId) {
		this.towerId=towerId;
		towerAnim=ItemList.getTower(towerId).getAnimation();
		previousState=State.currentState;
		currentState=this;
		BufferedImage[] selectionPics=new BufferedImage[currentTowers.length];
		for(int i=0;i<currentTowers.length;i++) {
			BufferedImage initial=ItemList.getTower(currentTowers[i]).getBuyIcon();
			selectionPics[i] = new BufferedImage(initial.getWidth(), initial.getHeight(), initial.getType());
		    Graphics g = selectionPics[i].getGraphics();
		    g.drawImage(initial, 0, 0, null);
		    g.drawImage(Assets.towerIcons[1],0,0, null);
		    g.dispose();
		}
		
		menu = new Menu(new Rectangle(),new MenuObject[] {
				new PictureSelection(new Rectangle(100,75,25,25),
						ItemList.getTower(currentTowers[0]).getBuyIcon().getSubimage(0, 0, 25, 25), 
						selectionPics[0].getSubimage(0, 0, 25, 25)) {
					@Override
					public void select() {
						super.select();
						towerToSwitch=0;
					}
				},
				new PictureSelection(new Rectangle(125,75,25,25),
						ItemList.getTower(currentTowers[1]).getBuyIcon().getSubimage(25, 0, 25, 25), 
						selectionPics[1].getSubimage(25, 0, 25, 25)){
					@Override
					public void select() {
						super.select();
						towerToSwitch=1;
					}
				},
				new PictureSelection(new Rectangle(100,100,25,25),
						selectionPics[2].getSubimage(0, 25, 25, 25)){
					@Override
					public void select() {
						super.select();
						towerToSwitch=2;
					}
				},
				new PictureSelection(new Rectangle(125,100,25,25),
						ItemList.getTower(currentTowers[3]).getBuyIcon().getSubimage(25, 25, 25, 25), 
						selectionPics[3].getSubimage(25, 25, 25, 25)){
					@Override
					public void select() {
						super.select();
						towerToSwitch=3;
					}
				}, new MenuSelection(new Rectangle(Window.getDisplay().getRelativeWidth()/2-10,
						Window.getDisplay().getRelativeHeight()/2+50,50,10),"leave it", Color.white, new Color(84,204,60)) {
					public void select() {currentState=previousState;}
				}
			});
		menu.select();
	
	}

	@Override
	public void update() {
		getInputs().update();
		menu.update(getInputs());
		towerAnim.update();
		if(towerToSwitch!=-1) {
			Entity.getEntityManager().getPlayer().swapTower(towerId, towerToSwitch);
			currentState=previousState;
		}
		
	}

	@Override
	public void render(Graphics g) {
		previousState.render(g);
		g.setColor(new Color(1,1,26));//setting the background colour
		g.fillRect(Window.getDisplay().getRelativeWidth()/2-150,Window.getDisplay().getRelativeHeight()/2-90,300,180); 		
		
		menu.render(g);
		
	
		g.setColor(new Color(46,153,64));
		g.drawString("CHOOSE A TOWER TO SWITCH OUT", Window.getDisplay().getRelativeWidth()/2-79, 21);
		g.drawString("CHOOSE A TOWER TO SWITCH OUT", Window.getDisplay().getRelativeWidth()/2-80, 21);
		g.setColor(Color.white);
		g.drawString("CHOOSE A TOWER TO SWITCH OUT", Window.getDisplay().getRelativeWidth()/2-80, 20);
		
		g.setColor(new Color(46,153,64));
		g.fillRect(Window.getDisplay().getRelativeWidth()/2+25,Window.getDisplay().getRelativeHeight()/2-25,50,50);
		g.drawImage(towerAnim.getCurrentFrame(),Window.getDisplay().getRelativeWidth()/2+50-towerAnim.getCurrentFrame().getWidth()/2,
				Window.getDisplay().getRelativeHeight()/2-towerAnim.getCurrentFrame().getHeight ()/2,null);
		
		
	}

}
