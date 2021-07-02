package entity.statics.pickups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.ItemList;
import entity.mobs.player.Player;
import graphics.Camera;
import graphics.ImageUtils;

public class GunTowerItem extends Pickup{
	private int id;
	BufferedImage icon;
	public GunTowerItem(int x, int y, int id) {
		super(x,y);
		this.id=id;
		icon=ImageUtils.outline(ItemList.getIcon(id),Color.white);
		setSize(icon.getWidth(), icon.getHeight());
		
		updateBounds();//making its bounds the right size and place
	}

	@Override
	void playerCollide(Player p) {
		p.giveItem(id);
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(icon, x-camera.getxOffset(), y-camera.getyOffset(), null);
		//drawing itself to the screen
	}
}
