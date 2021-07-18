package graphics;

//@author Kevin (everything but getCurrentFrame)

import java.awt.image.BufferedImage;
public class Animation {
	
	private boolean looping=true, paused=false;
	private int frameCount; //Keeps track of the frames so it can change at proper times
	private int frameDelay; //The delay between frame changing
	private int currentFrame; //This is the frame the program will draw
	private int totalFrames; //The amount of frames in the animation
	private BufferedImage[] pics; //The list that contains all the frames, is different depending on the parameter
	
	private int xOffset=0,yOffset=0;
	
	//if you dont specify the framerate it will default to 10
	public Animation(BufferedImage[] pics) {
		this(pics,6);
	}
	//a constructor that lets you specify framerate
	public Animation(BufferedImage[] pics, int frameDelay) {
		this(pics,frameDelay,0,0);
	}
	public Animation(BufferedImage[] pics, int frameDelay, int xOffset, int yOffset) {
		this.frameDelay = frameDelay;	
		this.totalFrames = pics.length; //Sets the total frames of animation to how many frames there are in the arraylist
		this.pics=pics;
		this.xOffset=xOffset;
		this.yOffset=yOffset;
		
	}
	
	public void update() {
		if(!paused) {
			frameCount++; //Every frame this variable will receive +1, this is to keep track when to change to the next picture
		}
		
		if (frameCount > frameDelay) { //When this frame count surpasses the delay specified run this code
			frameCount = 0; //Resets frame count so this way it can keep track of when to change to the next picture,
			currentFrame++; //Move on to the next frame/picture 
			if (currentFrame > totalFrames -1) { //Similar to when you run an array in a for loop. In this case it will run through all the frames, when you reach the last frame reset from the 1st frame 
				if(!looping) {
					paused=true;
				}
				currentFrame = 0; //All pictures are made to loop
			}
		}
	}
	
	
	public void setLooping(boolean looping) {
		this.looping = looping;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	public void setCurrentFrame(int index) {
		//this lets you set the animation to a specific frame
		currentFrame=index;
		frameCount=0;//reseting frame count so it will last the right amount of time
	}
	//getters
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
	public boolean isPaused() {
		return paused;
	}
	
	public int getlength() {
		return totalFrames;
	}
	public int getxOffset() {
		return xOffset;
	}
	public int getyOffset() {
		return yOffset;
	}
	
}