package window;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

//extending JPannel so that you have access to the PaintComponent to actually draw things
public class Display extends JPanel{
	public int x=0, y=0,width, height;;
	
	public Display(int width, int height) {
		//setting the proper size so that the window will pack properly
		this.width=width;
		this.height=height;
		this.setPreferredSize(new Dimension(width, height));
		
	}
	@Override
    public void paintComponent(Graphics g) {//where everything is actually drawn
		g.clearRect(0, 0, width, height);
        g.fillRect(x, y, 50, 50);//creating a rectangle to test stuff
	}
	public void update() {//a test method that changes things to tell that render is working
		x+=5;
		y+=5;
	}

}
