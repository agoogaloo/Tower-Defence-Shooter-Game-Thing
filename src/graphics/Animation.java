package graphics;

//@author Kevin (everything but getCurrentFrame)

import java.awt.image.BufferedImage;
public class Animation {
	
	private int frameCount; //Keeps track of the frames so it can change at proper times
	private int frameDelay; //The delay between frame changing
	private int currentFrame; //This is the frame the program will draw
	private int totalFrames; //The amount of frames in the animation
	private BufferedImage[] pics; //The list that contains all the frames, is different depending on the parameter
	
	//if you dont specify the framerate it wil default to 10
	public Animation(BufferedImage[] pics) {
		this(pics,6);
	}
	//a constructor that lets you speify framerate
	public Animation(BufferedImage[] pics, int frameDelay) {
		this.frameDelay = frameDelay;	
		this.totalFrames = pics.length; //Sets the total frames of animation to how many frames there are in the arraylist
		this.pics=pics;
		
	}
	//@author Matthew
	public BufferedImage getCurrentFrame() { //This is what classes call when they need to know what frame to draw
		//this need to give the picture from a new array so that if the image that it returns is changed by 
		//something else this animations pictures wont change as well
		BufferedImage[] newList=pics.clone();
		
		return newList[currentFrame]; //First draw the 1st  frame, when frame count surpasses the specified frame 
		//delay increase this currentFrame by 1, which will return the next frame, thus rendering the next frame
	}
	
	public int getFrameIndex() {
		return currentFrame;
	}
	
	public void update() {
		frameCount++; //Every frame this variable will receive +1, this is to keep track when to change to the next picture
		if (frameCount > frameDelay) { //When this frame count surpasses the delay specified run this code
			frameCount = 0; //Resets frame count so this way it can keep track of when to change to the next picture,
			currentFrame++; //Move on to the next frame/picture 
			if (currentFrame > totalFrames -1) { //Similar to when you run an array in a for loop. In this case it will run through all the frames, when you reach the last frame reset from the 1st frame 
				currentFrame = 0; //All pictures are made to loop
			}
		}
	}
}