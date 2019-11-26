import javax.swing.JFrame;

/*
 * this class represents the window that the game is played on
 * I used swing/JFrame instead of javaFX because I can't find an easy tutorial
 * on it but I can for JFrame because it's old
 */
public class Window {
	int width, height;//how big the screen is
	JFrame window;

	public Window(int width, int height) {
		this.width = width;//initializing variables
		this.height = height;
		window=new JFrame("a title");
		
		window.setSize(width, height);//setting size of the window
		window.setVisible(true);//making the window visible
		window.setLocationRelativeTo(null);//centers the window
	}

}
