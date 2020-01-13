package window;

import javax.swing.JFrame;

/* 
 * by: Matthew Milum
 */
public class Window {
	/*
	 * this class represents the window that the game is played on and uses java
	 * swing not javaFX
	 */
	private JFrame frame;
	private Display display;

	public Window(int width, int height) {
		// initializing variables
		frame = new JFrame("a title");
		display = new Display(width, height, 3);

		frame.add(display);// adding the display to the window so it can actually show it
		frame.setResizable(false);// not letting you resize the window so it doesn't mess things up when rendering
		// pack needs to come after setResizable because it changes the window size, so
		// the window will be the wrong size
		frame.pack();// making the window fit the panel perfectly

		frame.setLocationRelativeTo(null);// centers the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// makes the program stop when you close the window
		frame.setVisible(true);// making the window visible

	}

	public void render() {
		display.repaint();
		// this tells the JFrame to repaint the window, which calls paintComponent, drawing everything

	}

	public void update() {
		display.update();//updating the display which then updates everyother part of the game

	}

	// getters/setters
	public Display getDisplay() {
		return display;
	}

	public JFrame getFrame() {
		return frame;
	}
}
