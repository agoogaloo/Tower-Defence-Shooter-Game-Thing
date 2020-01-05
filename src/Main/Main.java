package Main;
import graphics.Camera;
import window.Window;

/*
 * by Matthew Milum
 */
public class Main {
	/*
	 * the main class that will create the window then updates and renders it
	 * (basically holds the main game loop)
	 */
	private static Window window;
	public static void main(String[] args) {
		window = new Window(800, 600);
		boolean run = true;

		// variables for limiting frame rate
		final int FPS = 60, DELAY = 1000000000 / FPS;
		long delta = 0, frameStart = System.nanoTime(), fpsTimer = frameStart;
		int frames = 0;

		while (run) {// running the game while run is true
			frameStart = System.nanoTime();// getting the time the frame started
			// this is where everything in the game actualy happens
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
				System.out.println(frames + "fps");
				fpsTimer = frameStart;// reseting the timer
				frames = 0;// reseting the number of frames
			}

			do {// this needs to be a do while so that delta can be updated before it is checked
				delta = System.nanoTime() - frameStart;// setting delta to how long the frame has lasted for
			} while (delta < DELAY);// looping until the frame has lasted the target number of nanoseconds
		}
	}
	public static Window getWindow() {
		return window;
	}
}
