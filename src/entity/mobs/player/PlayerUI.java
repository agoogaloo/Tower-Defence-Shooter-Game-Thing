package entity.mobs.player;

import java.util.ArrayList;

import graphics.Animation;
import graphics.Assets;
import graphics.UI.AnimElement;
import graphics.UI.TextElement;

public class PlayerUI {
	/*
	 * this is all the ui such as health and money that the player will draw to the screen
	 */
	
	private TextElement money=new TextElement(18, 31, "");
	private AnimElement moneyIcon=new AnimElement(5, 20, new Animation(Assets.coin));
	private AnimElement[] healthIcons=new AnimElement[15];
	public PlayerUI() {
		for(int i=0;i<healthIcons.length;i++) {
			healthIcons[i]=new AnimElement(5+(15*i), 5, new Animation(Assets.healthIcon));
		}
	}
	//takes money and health as parameters so it knows how much to draw
	public void update(int health, int money) {
		System.out.println("reeee");
		for(int i=0;i<healthIcons.length;i++) {//drawing a heart for every health the player has
			healthIcons[i].update();
			if(i<health) {
				healthIcons[i].visible=true;
			}else {
				healthIcons[i].visible=false;
			}
		}
		moneyIcon.update();
		this.money.update(String.valueOf(money));
	}
}
