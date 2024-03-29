package window;

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import graphics.Assets;

/* 
 * by: Matthew Milum
 */
public class Window {
	/*
	 * this class represents the window that the game is played on and uses java
	 * swing not javaFX
	 */
	private JFrame frame;
	private static Display display;

	public Window(int width, int height) {
		// initializing variables
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		frame = new JFrame("Gunfire Spire");
		
		frame.setResizable(false);// not letting you resize the window so it doesn't mess things up when rendering
		resize(width, height);
		
		frame.setLocationRelativeTo(null);// centers the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// makes the program stop when you close the window
		frame.setCursor(toolkit.createCustomCursor(Assets.blank,new Point(0,0), "cursor"));
		frame.setVisible(true);// making the window visible

	}

	public void render() {
		display.repaint();
		// this tells the JFrame to repaint the window, which calls paintComponent, drawing everything
	}

	public void update() {
		display.update();//updating the display which then updates everyother part of the game

	}
	public void resize(int width, int height) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		display = new Display(width, height);//making the display
		//frame.setCursor(toolkit.createCustomCursor(Assets.cursors[Settings.getScale()-1],new Point(15,15), "cursor"));
		frame.add(display);// adding the display to the window so it can actually show it
		// pack needs to come after setResizable because it changes the window size, so
		// the window will be the wrong size
		frame.pack();// making the window fit the panel perfectly
	}

	// getters/setters
	public static Display getDisplay() {
		return display;
	}

	public JFrame getFrame() {
		return frame;
	}
}
