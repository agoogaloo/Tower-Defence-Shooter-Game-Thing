package Main;
import saveData.SaveData;
import saveData.Settings;
import states.State;
import window.Window;

/*
 * by Matthew Milum
 */
public class Main {
	/*
	 * the main class that will create the window then updates and renders it
	 * (basically hold
	 * s the main game loop)
	 */
	private static Window window;//creating a window so we can see things
	private static int fps=0;
	public static void main(String[] args) {
		Settings.reload();
		SaveData.reload();
		window= new Window(333*Settings.getScale(), 200*Settings.getScale());
		State.init();
		boolean run = true;

		// variables for limiting frame rate
		final int TARGETFPS = 60, DELAY = 1000000000 / TARGETFPS;
		long delta = 0, frameStart = System.nanoTime(), fpsTimer = frameStart;
		int frames = 0;

		while (run) {// running the game while run is true
			frameStart = System.nanoTime();// getting the time the frame started
			delta=0;
			// this is where everything in the game actually happens
			window.update();// updates the window
			window.render();// draws everything on the window
			frames++;// adding one to the number of frames that passed

			// checking if it has been one second sense the FPS has been displayed
			// note: you could get the current time instead of frame start, however would be
			// a bit slower, and we are just printing the FPS so it's ok if its a bit off.
			// another note: if you want it more accurate you can take take the frame
			// rate every few seconds and just divide frames by how many second you
			// increased
			if (frameStart - fpsTimer >= 1000000000) {
				// outputting the number of frames that happened that second
				//System.out.println(frames + "fps");
				fpsTimer = frameStart;// reseting the timer
				frames = 0;// reseting the number of frames
			}

			if(delta >= DELAY) {
				System.out.println("uh oh things are lagging");
				
			}
			while (delta < DELAY) {
				 delta = System.nanoTime() - frameStart;// setting delta to how long the frame has lasted for// looping until the frame has lasted the target number of nanoseconds
			 
			 }
			fps=Math.round(1000000000f/delta);
		}
	}
	//lets other classes get the window so they can know things like window size 
	public static Window getWindow() {
		return window;
	}
	public static int getFPS() {
		return fps;
	}
	public static void resetWindow() {//this lets us reset the window to apply things like window scaling
		window.resize(333*Settings.getScale(), 200*Settings.getScale());
	}
}
