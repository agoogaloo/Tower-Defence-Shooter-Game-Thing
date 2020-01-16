package entity.mobs.player;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;

public class PlayerUI {
	int health, money;
	Animation healthAnim;
	Animation coinAnim;
	public PlayerUI() {
		healthAnim=new Animation(Assets.healthIcon,6);
		coinAnim=new Animation(Assets.coin,7);
	}
	public void update(int health, int money) {
		this.money=money;
		this.health=health;
		coinAnim.update();
		healthAnim.update();
	}
	public void render(Graphics g) {
		for(int i=0;i<health;i++) {
			g.drawImage(healthAnim.getCurrentFrame(), 5+(15*i), 5, null);
		}
		g.drawImage(coinAnim.getCurrentFrame(), 5, 20, null);
		g.drawString(String.valueOf(money), 21, 30);
	}

}
