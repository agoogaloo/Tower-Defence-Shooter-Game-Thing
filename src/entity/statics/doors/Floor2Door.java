package entity.statics.doors;

import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class Floor2Door extends Door{
	float speed=0;
	
	
	public Floor2Door(int x, int y, char direction) {
		
		super(x, y, direction);
		
		switch(direction) {
		case 'l': case 'r':
			anim = new Animation(Assets.tutDoorHor);
			width=Assets.tutDoorHor[0].getWidth();
			height=48;
			this.x=x-Assets.tutDoorHor[0].getWidth();
			this.y=y-height+32-5;
			break;
			
			
		case 'u': case 'd':
			anim = new Animation(Assets.lv2DoorVert);
			width=48;
			height=48;
			this.x=x-16;
			this.y=y-16;
			break;
		default:
			anim = new Animation(Assets.lv2DoorVert);
			width=48;
			height=48;
			this.x=x-16;
			this.y=y-16;
			break;
		}
		anim.setLooping(false);
		updateBounds();
		
	}
	
	@Override
	public void update() {
		if(!solid&&anim.getFrameIndex()!=anim.getlength()-1) {
			anim.update();
			if(anim.getFrameIndex()>anim.getlength()-3) {
				if(direction=='u'||direction=='d') {
				new InstantEffect(20, new Straight(new RectangleSpawner(x,y+45,width,5),0.75), 
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
	public void unlock() {
		super.unlock();
		anim.setCurrentFrame(1);
		
	}
	
	@Override
	public void reLock() {
		super.reLock();
		anim.reverse();
	}

	
	
}
