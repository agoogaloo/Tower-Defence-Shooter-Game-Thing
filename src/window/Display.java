package window;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * extending JPanel so that you have access to the PaintComponent to actually draw things
 * everything is drawn onto a display which is then added onto the window,
 * because you aren't supposed to draw directly onto it
 */
public class Display extends JPanel{
	public int rectX=0, rectY=0,width, height;;
	
	public Display(int width, int height) {
		//setting the proper size so that the window will pack properly
		this.width=width;
		this.height=height;
		this.setPreferredSize(new Dimension(width, height));
	
		
	}
	@Override
    public void paintComponent(Graphics g) {//where everything is actually drawn	
		g.clearRect(0, 0, width, height);//clearing the previous frame
        g.fillRect(rectX, rectY, 50, 50);//creating a rectangle to test stuff
        
	}
	/*
	 * the display probably souldn't have an update method and everything will be updated
	 * from the window class but the rectangle is in the display class so I did it this way
	 */
	public void update() {
		rectX+=1;
		rectY+=1;
	}

}
