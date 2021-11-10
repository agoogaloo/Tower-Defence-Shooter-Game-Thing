package entity.mobs.shops;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.mobs.Mobs;
import entity.mobs.pickups.GunTowerItem;
import entity.mobs.pickups.Health;
import entity.mobs.pickups.ItemList;
import entity.mobs.pickups.Pickup;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.TextElement;

public class ShopKeep extends Mobs{
	private final String[] SPEECHOPTIONS = {"welcome to my shop","hello there","feel free to browse","how do you do?","peew peew",
			"you break, you buy", "what's crackalackin'", "buy somethin' will ya"};
	private final String[] LEAVEOPTIONS = {"","bye","see ya","have fun storming the castle", "come again"};
	private final String[] HITOPTIONS = {"ouch!", "hey!", "stop that", "how rude", "what was that for?", "help!"};
	private final String[] BUYOPTIONS = {"thanks!", "good choice", "no refunds"};
	private final String[] SOLDOUTOPTIONS = {"i'm sold out", "there's nothing left", "look's like i need to restock"};
	
	private ArrayList<ItemStand> items = new ArrayList<ItemStand>();
	private ArrayList<Integer> usedIds = new ArrayList<Integer>();
	private final int TEXTTIME=45;
	private Animation anim = new Animation(Assets.shopIdle, 7);
	private Rectangle talkBounds = new Rectangle(275, 175);
	private TextElement speechBox;
	private int speechWidth=98, leaveTimer, hitTimer, buyTimer;
	private boolean defaultText=false;
	
	public ShopKeep(int x, int y) {
		width=anim.getCurrentFrame().getWidth();
		height=anim.getCurrentFrame().getHeight();
		setLocation(x-width/2, y-height/2);
		updateBounds();
	
		talkBounds.x=this.x-talkBounds.width/2;
		talkBounds.y=this.y-talkBounds.height/2;
		speechBox = new TextElement(0,0, "");
		friendly=false;
	}
	@Override
	public void init() {
		for(ItemStand i:items) {
			entityManager.addEntity(i);
		}
	}

	@Override
	public void update() {
		anim.update();
		
		for(int i=0;i<items.size();i++) {
			if(items.get(i).isBought()) {
				System.out.println("bought");
				items.remove(i);
				buyTimer=TEXTTIME;
			}
		}
	
		if(talkBounds.contains(entityManager.getPlayer().getBounds())){
			leaveTimer=TEXTTIME;
			if(buyTimer>0) {
				buyText();
			}else if(hitTimer>0) {
				hitText();	
			}else if(!defaultText) {
				defaultText();
			}
		}else {
			leaveText();
		}	
	}
	
	private void buyText() {
		buyTimer--;
		if(buyTimer==TEXTTIME-1) {
			speechBox.update(BUYOPTIONS[ThreadLocalRandom.current().nextInt(BUYOPTIONS.length)], speechWidth);
			speechBox.centre();
			defaultText=false;
		}
	}
	private void hitText() {
		hitTimer--;
		if(hitTimer==TEXTTIME-3) {
			speechBox.update(HITOPTIONS[ThreadLocalRandom.current().nextInt(HITOPTIONS.length)], speechWidth);
			speechBox.centre();
			defaultText=false;
		}
	}
	private void defaultText() {
		if(items.size()>=1) {
			speechBox.update(SPEECHOPTIONS[ThreadLocalRandom.current().nextInt(SPEECHOPTIONS.length)], speechWidth);
		}else {
			speechBox.update(SOLDOUTOPTIONS[ThreadLocalRandom.current().nextInt(SOLDOUTOPTIONS.length)], speechWidth);
		}
		speechBox.centre();
		defaultText=true;
	}
	
	private void leaveText() {
		if(leaveTimer==TEXTTIME&&hitTimer<=0) {
			speechBox.update(LEAVEOPTIONS[ThreadLocalRandom.current().nextInt(LEAVEOPTIONS.length)], speechWidth);
			speechBox.centre();
			leaveTimer--;
			defaultText=false;
		}
		else if(leaveTimer<=0) {
			speechBox.update("");
			
		}else {
			leaveTimer--;
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		speechBox.move(x+width/2-speechBox.getWidth()/2-camera.getxOffset(), y-2-speechBox.getHeight()-camera.getyOffset());
		g.drawImage(anim.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
	}
	
	@Override
	public void damage(double d) {
		if(d>0&&hitTimer<=10) {
			hitTimer=TEXTTIME;
		}
	}
	public void makeItemStand(int x, int y) {
		int[] list=ItemList.getFindableItems();
		int id=0;
		boolean validID=false;
		//looping until it finds an id that works
		while(!validID){
			id=ThreadLocalRandom.current().nextInt(0, ItemList.getFindableItems().length+1);
			boolean tower=false, gun=false, health=false;
			//seeing what types of items have already been made, so there will always be health, a gun, and a tower
			for(int i:usedIds) {
				if(i==0) {
					health=true;
				}else if(ItemList.isGun(list[i-1])){
					gun=true;
				}else if (ItemList.isTower(list[i-1])){
					tower=true;
				}else {
					System.out.println("item id"+list[i-1] +"isn't a tower, gun or health" );
				}
			}
			
			//actually checking if the item is valid
			if(health&&gun&&tower) {
				validID=true;
			}else if(id==0&&health) {
				validID=false;
			}else if(id!=0&&ItemList.isGun(list[id-1])&&gun) {
				validID=false;
			}else if(id!=0&&ItemList.isTower(list[id-1])&&tower) {
				validID=false;
			}else {
				validID=true;
			}
			if(id!=0&&usedIds.contains(id)) {
				validID=false;
			}
			
		
		}
		
		usedIds.add(id);
		
		if(id==0) {
			items.add(new ItemStand(new Health(0, 0),5+ThreadLocalRandom.current().nextInt(0, 3),x,y));
		}else {
			
			Pickup item= new GunTowerItem(x, y,list[id-1]);
			items.add(new ItemStand(item,10+ThreadLocalRandom.current().nextInt(0, 5),x,y));
		}
		
	}
}
