package entity.mobs.player.UI;

import graphics.Animation;
import graphics.Assets;
import graphics.UI.AnimElement;
import graphics.UI.TextElement;

public class PlayerUI {
	/*
	 * this is all the ui such as health and money that the player will draw to the screen
	 */
	//used to tell if you have healed/taken damage
	private int oldHealth;
	private TextElement money=new TextElement(18, 20, "", Assets.MozartNbp);//the number saying how much money you have
	private AnimElement moneyIcon=new AnimElement(5, 20, new Animation(Assets.coin));//the spinning coin in the corner
	private AnimElement[] healthIcons=new AnimElement[22];//the hearts the show how much health you have left 
	//after 22 hearts it just goes off screen so the array doesnt need to go much higher
	public PlayerUI() {
		for(int i=0;i<healthIcons.length;i++) {
			healthIcons[i]=new AnimElement(5+(15*i), 5, new Animation(Assets.healthIcon));
			//this creates all the ui elements for the hearts but most of them will just be invisible
		}
	}
	//takes money and health as parameters so it knows how much to draw
	public void update(int health, int money) {
		for(int i=0;i<healthIcons.length;i++) {//drawing a heart for every health the player has
			healthIcons[i].update();//updating the animation
			if(i<health) {//making the icon visible if you have more health than its place
				healthIcons[i].visible=true;
			}else {//otherwise this icon is after the amount of health you have so it should be invisible
				healthIcons[i].visible=false;
			}
		}
		if(health!=oldHealth) {
			oldHealth=health;
			for(AnimElement i: healthIcons) {
				i.move(i.getX(), i.getY()-2);
			}
			
		}else if(healthIcons[0].getY()!=5) {
			for(AnimElement i: healthIcons) {
				i.move(i.getX(),5);
				i.flash();
			}
		}
		moneyIcon.update();//updating the coin icon
		this.money.update(String.valueOf(money));//updating the money counter
	}
}
