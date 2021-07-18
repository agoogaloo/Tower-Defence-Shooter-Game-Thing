package entity.mobs.player;

import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;

public class Animator {
	private PlayerAnimations anim;
	private Animation currentAnim;
	
	private Animation idleDown = new Animation(Assets.playerIdleD,6,1,2); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation idleLeft = new Animation(Assets.playerIdleL,6,1,2);
	private Animation idleUp = new Animation(Assets.playerIdleU,6,1,2);
	private Animation idleRight = new Animation(Assets.playerIdleR,6,1,2);
	
	private Animation runDown = idleDown;// Animation(Assets.playerRunD,6,2,1); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation runLeft = idleLeft;// new Animation(Assets.playerRunL,6);
	private Animation runUp = idleUp;// new Animation(Assets.playerRunU,6);
	private Animation runRight = idleRight;// new Animation(Assets.playerRunR,6);
	
	private Animation spin= new Animation(Assets.spinD,4,11,7);
	
	
	public BufferedImage update(char direction, boolean moving, boolean spinStart) {
		PlayerAnimations initialAnim=anim;

		setAnim(direction, moving, spinStart);
		if(anim!=initialAnim) {
			currentAnim.setCurrentFrame(0);
		}else {
			currentAnim.update();
		}
		
		return currentAnim.getCurrentFrame();
	}
	
	private void setAnim(char direction, boolean moving, boolean spinStart) {
		if(spinStart||(anim==PlayerAnimations.SPIN&&currentAnim.getFrameIndex()<currentAnim.getlength()-1)) {
			anim=PlayerAnimations.SPIN;
			currentAnim=spin;
			
		}else if(moving) {
			switch(direction) {
			case 'd':
				anim=PlayerAnimations.RUNDOWN;
				currentAnim=runDown;
				break;
			case 'l':
				anim=PlayerAnimations.RUNLEFT;
				currentAnim=runLeft;
				break;
			case'u':
				anim=PlayerAnimations.RUNUP;
				currentAnim=runUp;
				break;
			case'r':
				anim=PlayerAnimations.RUNRIGHT;
				currentAnim=runRight;
			}
			
		}else {
			switch(direction) {
			case 'd':
				anim=PlayerAnimations.IDLEDOWN;
				currentAnim=idleDown;
				break;
			case 'l':
				anim=PlayerAnimations.IDLELEFT;
				currentAnim=idleLeft;
				break;
			case'u':
				anim=PlayerAnimations.IDLEUP;
				currentAnim=idleUp;
				break;
			case'r':
				anim=PlayerAnimations.IDLERIGHT;
				currentAnim=idleRight;
			}
		}
	}
	
	public PlayerAnimations getCurrentAnimation() {
		return anim;
	}
	public int getxOffest() {
		return currentAnim.getxOffset();
	}
	public int getyOffest() {
		return currentAnim.getyOffset();
	}
}
