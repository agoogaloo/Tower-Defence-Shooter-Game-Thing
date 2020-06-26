package window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import settings.Settings;
import states.State;

/*
 * by: Matthew Milum
 */
public class Display extends JPanel {
	/*
	 * this is the display where everything is actually drawn onto the display is then put onto
	 * the window so we can see it
	 * extending JPanel so that you have access to the PaintComponent to actually draw things
	 * everything is drawn onto a display which is then added onto the window,
	 * because you aren't supposed to draw directly onto it
	 */
	private int width, height, scale;
	public Display(int width, int height) {
		// setting the proper size so that the window will pack properly
		// the display is scaled up to look 8-bit so the
		// resolution is actually 1/3 of the screen width
		scale=Settings.getScale();
		this.width = width / scale;
		this.height = height / scale;
		
		this.setPreferredSize(new Dimension(width, height));
		
		// setting the preferred size to the inputed one so that the pack method will work
	
	}

	@Override
	public void paintComponent(Graphics g) {// where everything is actually drawn
		// all rendering code goes here
		
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(State.getState()!=null) {
			State.getState().render(image.getGraphics());
		}
		g.drawImage(image,0,0,width*scale, height*scale, null);
		/*
		// a normal graphics object cannot scale so I cast it to a graphics2D which can
		Graphics2D g2d = (Graphics2D) g;

		g2d.scale(scale, scale);// scaling the graphics so the pixel art looks the right size
		//g2d.clearRect(0, 0, width, height);//we dont really need to clear the screen each frame but we cna here if we need to
		//g2d.setColor(new Color(38,12,38));
		//g2d.fillRect(0, 0, width, height);// clearing the previous frame
		if(State.getState()!=null) {
			State.getState().render(g2d);
		}
		*/
	}

	public void update() {
		State.getState().update();
	}
	
	//getters
	public int getScale() {
		return scale;
	}
}
