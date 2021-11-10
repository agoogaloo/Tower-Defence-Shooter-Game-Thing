package entity.mobs.shops;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.pickups.Pickup;
import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.ImageUtils;

public class ItemStand extends Tower{
	Animation anim = new Animation(Assets.itemStand);
	Pickup  item;
	private int price, animTime, itemHeight=0, itemTime=ThreadLocalRandom.current().nextInt(360);
	private boolean bought;
	
	public ItemStand(Pickup item, int price, int x, int y) {
		sellValue=0;
		upgradeIcon=Assets.towerIcons[2];
		
		this.price = price;
		
	
		anim.setLooping(false);
		width=anim.getCurrentFrame().getWidth();
		height=anim.getCurrentFrame().getHeight();
		this.x=x-width/2;
		this.y= y-height/2;
		updateBounds();
		
		this.item = item;
		item.move(this.x +width/2-item.getIcon().getWidth()/2,this.y+height);
		solid=true;
		
	}

	@Override
	public void update() {
		animTime--;
		anim.update();
		if(animTime<=0) {
			animTime=ThreadLocalRandom.current().nextInt(120,480);
			anim.setPaused(false);
		}
		itemTime+=2;
		itemHeight=(int) Math.round(2*Math.sin(Math.toRadians(itemTime)));
		if(itemTime>=3600) {
			itemTime=0;
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		//drawing the stand
		if(hovered&&!bought) {
			g.drawImage(ImageUtils.outline(anim.getCurrentFrame(),Color.white),x-1 - camera.getxOffset(), y-1 - camera.getyOffset(), null);
		}else {
			g.drawImage(anim.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
		}
		hovered=false;
		
		//drawing the item floating
		if(!bought) {
			
			g.setColor(Color.white);
			g.setFont(Assets.smallMonoFont);//setting the font to the right font
			g.drawString(""+price, x+12-camera.getxOffset()-(""+price).length()*2 ,y+27-camera.getyOffset());
			if(item.getIcon().getHeight()>=24) {
				g.drawImage(item.getIcon(),x +width/2-item.getIcon().getWidth()/2- camera.getxOffset(), 
						y-item.getIcon().getHeight()+16+itemHeight - camera.getyOffset(), null);
			}else {
				g.drawImage(item.getIcon(),x +width/2-item.getIcon().getWidth()/2- camera.getxOffset(), 
						y-item.getIcon().getHeight()/2+4+itemHeight - camera.getyOffset(), null);
			}
			
		}
	}
	
	public void damage(double d) {
		
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		if(money>=price&&!bought) {
			entityManager.addEntity(item);
			bought=true;
			upgradeIcon=Assets.blank;
			return price;
		}
		return 0;
	}
	

	@Override
	public String select(char leftRight) {
		if(!bought) {
			return "buy item for $ "+price;
		}
		return "";
	}
	@Override
	public void showRange(Graphics g, Camera camera) {}
	@Override
	public void destroy() {}

	@Override
	public Tower createNew(int x, int y, TowerSpawn spawn) {
		return null;
	}
	public boolean isBought() {
		return bought;
	}
	
	
	
	
}
