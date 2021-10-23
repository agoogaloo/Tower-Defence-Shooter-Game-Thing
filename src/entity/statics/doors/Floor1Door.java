package entity.statics.doors;

import graphics.Animation;
import graphics.Assets;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class Floor1Door extends Door{
	float speed=0;
	
	
	public Floor1Door(int x, int y, char direction) {
		
		super(x, y, direction);
		
		switch(direction) {
		case 'l':
			anim = new Animation(Assets.lv1DoorL,5,0,-2);
			width=Assets.lv1DoorL[0].getWidth();
			height=48;
			this.x=x-Assets.lv1DoorL[0].getWidth();
			this.y=y-height+32-5;
			break;
			
		case 'r':
			anim = new Animation(Assets.lv1DoorR,5,0,-2);
			width=Assets.lv1DoorR[0].getWidth();
			height=48;
			this.x=x;
			this.y=y-height+32-5;
			break;
			
		case 'u':
			anim = new Animation(Assets.lv1DoorU,5,10,0);
			width=48;
			height=Assets.lv1DoorU[0].getHeight()-16;
			this.x=x-16;
			this.y=y-height;
			break;
			
		case 'd':
			anim = new Animation(Assets.lv1DoorD,5,10,0);
			width=48;
			height=Assets.lv1DoorU[0].getHeight()-16;
			this.x=x-16;
			this.y=y;
			break;
		default:
			anim = new Animation(Assets.lv1DoorL,5);
			width=Assets.lv1DoorL[0].getWidth();
			height=48;
			this.x=x-Assets.lv1DoorL[0].getWidth();
			this.y=y-height+32;
			break;
		}
		anim.setLooping(false);
		updateBounds();
		
	}
	
	@Override
	public void update() {
		if(!solid&&anim.getFrameIndex()!=anim.getlength()-1) {
			anim.update();
			speed+=0.25;
			switch(direction) {
			case'l':
				x-=speed;
				new InstantEffect(1, new Straight(new Point(x+width,y+12),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				new InstantEffect(1, new Straight(new Point(x+width,y+36),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				break;
			case'r':
				x+=speed;
				new InstantEffect(1, new Straight(new Point(x,y+12),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				new InstantEffect(1, new Straight(new Point(x,y+36),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				break;
			case'u':
				y-=speed;
				new InstantEffect(1, new Straight(new Point(x+12,y+height),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				new InstantEffect(1, new Straight(new Point(x+36,y+height),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				break;
			case'd':
				y+=speed;
				new InstantEffect(1, new Straight(new Point(x+12,y),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				new InstantEffect(1, new Straight(new Point(x+36,y),0.75), 
						new OvalParticle(2, new Timed(20)), false);
				break;
			}
			updateBounds();
			
		}
		
	}
	
	@Override
	public void unlock() {
		super.unlock();
		anim.setCurrentFrame(1);
		
	}

	
	
}
