package entity.mobs.player;

import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;

public class Animator {
	private PlayerAnimations anim;
	private Animation currentAnim;
	
	private Animation idleDown = new Animation(Assets.playerIdleD,6); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation idleLeft = new Animation(Assets.playerIdleL,6);
	private Animation idleUp = new Animation(Assets.playerIdleU,6);
	private Animation idleRight = new Animation(Assets.playerIdleR,6);
	
	private Animation runDown = new Animation(Assets.playerRunD,6); //Different animations depending on the direction the player is facing, direction is set in PlayerInput
	private Animation runLeft = new Animation(Assets.playerRunL,6);
	private Animation runUp = new Animation(Assets.playerRunU,6);
	private Animation runRight = new Animation(Assets.playerRunR,6);
	
	public BufferedImage update(char direction, boolean moving) {
		PlayerAnimations initialAnim=anim;

		setAnim(direction, moving);
		if(anim!=initialAnim) {
			currentAnim.setCurrentFrame(0);
		}else {
			currentAnim.update();
		}
		
		return currentAnim.getCurrentFrame();
	}
	
	private void setAnim(char direction, boolean moving) {
		if(moving) {
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
}
