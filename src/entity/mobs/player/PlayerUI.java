package entity.mobs.player;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;

public class PlayerUI {
	/*
	 * this is all the ui such as health and money that the player will draw to the screen
	 */
	int health, money;
	Animation healthAnim=new Animation(Assets.healthIcon,6);
	Animation coinAnim=new Animation(Assets.coin,7);//the animaions it will use
	
	public void update(int health, int money) {
		//it needs to know how much money and health the player has so it can draw that much to the screen
		this.money=money;
		this.health=health;
		
		coinAnim.update();
		healthAnim.update();//updating the animations
	}
	public void render(Graphics g) {//the ui is never offset so it doesn't need the camera
		for(int i=0;i<health;i++) {//drawing a heart for every health the player has
			g.drawImage(healthAnim.getCurrentFrame(), 5+(15*i), 5, null);
			//offsetting each heart by 15 pixels by multiplying by i
		}
		g.drawImage(coinAnim.getCurrentFrame(), 5, 20, null);//drawing the coin icon
		g.setColor(new Color(250,250,250));
		g.setFont(Assets.MozartNbp);
		g.drawString(String.valueOf(money), 18, 31);//printing how much money the player has
	}

}
