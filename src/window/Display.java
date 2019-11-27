package window;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

//extending JPannel so that you have access to the PaintComponent to actually draw things
public class Display extends JPanel{
	
	public Display(int width, int height) {
		//setting the proper size so that the window will pack properly
		this.setPreferredSize(new Dimension(width, height));
	}
	@Override
    public void paintComponent(Graphics g) {//where everything is actually drawn
        g.fillRect(50, 10, 100, 500);//creating a rectangle to test stuff
	}

}
