package window;
import javax.swing.JFrame;



/* by: Matthew Milum
 * this class represents the window that the game is played on
 * I used swing/JFrame instead of javaFX because I can't find an easy tutorial
 * on it but I can for JFrame because it's old
 */
public class Window {
	private JFrame window;
	private Display display;
	
	public Window(int width, int height) {
		//initializing variables
		window=new JFrame("According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible. Yellow, black. ");
		display=new Display(width, height, 3);

		window.add(display);//adding the display to the window so it can actually show it
		window.setResizable(false);//not letting you resize the window so it doesn't mess things up when rendering
		//pack needs to come after setResizable because it changes the window size, so the window will be the wrong size
		window.pack();//making the window fit the panel perfectly 
		
		window.setLocationRelativeTo(null);//centers the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes the program stop when you close the window
		window.setVisible(true);//making the window visible
	}
	
	public void render() {
		display.repaint();//this tells the JFrame to repaint the window, which calls paintComponent 
	}
	
	public void update() {
		display.update();//a test method that changes things to tell its working
	}
	
	//getters/setters
	public Display getDisplay() {
		return display;
	}
	
	

}
