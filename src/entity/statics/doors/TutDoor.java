package entity.statics.doors;

import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class TutDoor extends Door{
	
	public TutDoor(int x, int y, char direction, boolean vertical) {
		super(x, y, direction,vertical);
		if(vertical) {
			anim = new Animation(Assets.tutDoorVert);
			this.x=x-16;
			this.y=y-16;
			width=48;
			height=Assets.tutDoorVert[0].getHeight()-16;
			
		}else {
			anim = new Animation(Assets.tutDoorHor);
			this.x=x;
			this.y=y-38;
			width=Assets.tutDoorHor[0].getWidth();
			height=Assets.tutDoorHor[0].getHeight();
		}
		
		anim.setLooping(false);
		updateBounds();
		
	}
	
	@Override
	public void update() {
		if(!solid&&anim.getFrameIndex()!=anim.getlength()-1) {
			anim.update();
			if(anim.getFrameIndex()>anim.getlength()-3) {
				if(vertical) {
				new InstantEffect(20, new Straight(new RectangleSpawner(x,y+43,width,5),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				}else {
					new InstantEffect(20, new Straight(new RectangleSpawner(x,y+38,width,26),0.75), 
							new OvalParticle(2, new Timed(20)), false);
				}
			}
			
		}
		if(solid&&anim.getFrameIndex()!=0) {
			anim.update();
		}
		
	}
	
	@Override
	public void reLock() {
		super.reLock();
		anim.reverse();
	}

	
	
}
