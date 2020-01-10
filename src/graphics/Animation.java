package graphics;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import graphics.Assets;
public class Animation {
	
	private int frameCount; //Keeps track of the frames so it can change at proper times
	private int frameDelay; //Delay between frames changing
	private int currentFrame; //The current frame the program draws
	private int animationDirection; //Either runs through the frames in order or reverse order
	private int totalFrames; //The amount of frames in the animation
	private BufferedImage[] pics;
	private boolean run; //The animation is running
	
	public Animation(BufferedImage[] pics, int frameDelay) {
		this.frameDelay = frameDelay;	
		this.totalFrames = pics.length; //Sets the total frames of animation to how many frames there are in the arraylist
		this.pics=pics;
		
	}
	
	public BufferedImage getCurrentFrame() {
		return pics[currentFrame];
	}
	

	public void update() {
		frameCount++;
		if (frameCount > frameDelay) {
			frameCount = 0;
			currentFrame++;
			if (currentFrame > totalFrames -1) {
				currentFrame = 0;
			}
		}
//	
	}
}