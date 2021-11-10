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
	private static boolean run=true;
	public static void main(String[] args) {
		Settings.reload();
		SaveData.reload();
		window= new Window(333*Settings.getScale(), 200*Settings.getScale());
		State.init();

		// variables for limiting frame rate
		final int TARGETFPS = 60, DELAY = 1000000000 / TARGETFPS;
		long delta = 0, frameStart = System.nanoTime(), fpsTimer = frameStart;

		while (run) {// running the game while run is true
			frameStart = System.nanoTime();// getting the time the frame started
			delta=0;
			// this is where everything in the game actually happens
			window.update();// updates the window
			window.render();// draws everything on the window
			
			
			
			delta = System.nanoTime() - frameStart;
			if(delta >= DELAY) {
				
				System.out.println("\nuh oh things are lagging");
				System.out.println("it is "+(delta-DELAY) +" nanoseconds behind");
				System.out.println("FPS is "+1f/(delta/1000000000f));
				
			}
			while (delta < DELAY) {
				
				 delta = System.nanoTime() - frameStart;// setting delta to how long the frame has lasted for// looping until the frame has lasted the target number of nanoseconds
			 
			 }
			fps=Math.round(1f/(delta/1000000000f));
		}
		
		window.getFrame().dispose();
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
	public static void quitGame() {
		run=false;
	}
}
