package entity.mobs;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;
import graphics.UI.TextElement;

public class ShopKeep extends Mobs{
	private final String[] SPEECHOPTIONS = {"welcome to my shop","hello there","feel free to browse","how do you do?","peew peew",
			"you break, you buy", "what's crackalackin'", "buy somethin' will ya"};
	private final String[] LEAVEOPTIONS = {"","bye","see ya","good luck storming the castle", "come again"};
	private final String[] HITOPTIONS = {"ouch!", "hey!", "stop that", "how rude", "what was that for?", "help!"};
	private final int TEXTTIME=45;
	private Animation anim = new Animation(Assets.shopIdle, 7);
	private Rectangle talkBounds = new Rectangle(260, 150);
	private TextElement speechBox;
	private int speechWidth=100, leaveTimer, hitTimer;
	private boolean talking=false;
	
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
	public void update() {
		anim.update();
		if(talkBounds.contains(entityManager.getPlayer().getBounds())){
			if(hitTimer>0) {
				hitTimer--;
				leaveTimer=TEXTTIME;
				if(hitTimer==TEXTTIME-5) {
					speechBox.update(HITOPTIONS[ThreadLocalRandom.current().nextInt(HITOPTIONS.length)], speechWidth);
					speechBox.centre();
					talking=false;
					
				}
				
			}else if(!talking) {
				speechBox.update(SPEECHOPTIONS[ThreadLocalRandom.current().nextInt(SPEECHOPTIONS.length)], speechWidth);
				speechBox.centre();
				talking=true;
				leaveTimer=TEXTTIME;
			}
		}else {
			if(leaveTimer==TEXTTIME&&hitTimer<=0) {
				speechBox.update(LEAVEOPTIONS[ThreadLocalRandom.current().nextInt(LEAVEOPTIONS.length)], speechWidth);
				speechBox.centre();
				leaveTimer--;
				talking=false;
			}
			else if(leaveTimer<=0) {
				speechBox.update("");
				
			}else {
				leaveTimer--;
			}
			
			
		}
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		speechBox.move(x+width/2-speechBox.getWidth()/2-camera.getxOffset(), y-2-speechBox.getHeight()-camera.getyOffset());
		g.drawImage(anim.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
	}
	@Override
	public void damage(double d) {
		if(d>0) {
			hitTimer=TEXTTIME;
		}
	}
}
