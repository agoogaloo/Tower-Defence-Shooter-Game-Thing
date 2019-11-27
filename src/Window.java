import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * this class represents the window that the game is played on
 * I used swing/JFrame instead of javaFX because I can't find an easy tutorial
 * on it but I can for JFrame because it's old
 */
public class Window{
	private int width, height;//how big the screen is
	private JFrame window;
	private JPanel panel;

	/*
	 * render is used to draw things onto the JPanel which then gets added onto the window
	 */
	public void render() {
		panel.setBackground(Color.GREEN);//setting the background colour to see if it works
		//window.add(panel);
	}
	
	public Window(int width, int height) {
		this.width = width;//initializing variables
		this.height = height;
		window=new JFrame("a title");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(width, height));//setting the size of the panel to the desired width and height
		window.add(panel);//adding the panel to the window so it can actually show it
		window.pack();//making the window fit the panel perfectly 
		window.setVisible(true);//making the window visible
		window.setLocationRelativeTo(null);//centers the window
		window.setResizable(false);//not letting you resize the window so it doesn't mess things up when rendering
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes the program stop when you close the window
	}

}
