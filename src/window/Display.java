package window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import graphics.Assets;
import saveData.Settings;
import states.State;
import states.console.ConsoleState;

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
		//the image the everything is drawn onto
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=(Graphics2D)image.getGraphics();
		
		//drawing everything onto the image 
		if(State.getState()!=null) {
			g2d.setRenderingHint( RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
			State.getState().render(g2d);
		}
		
		
		if(ConsoleState.isShowFPS()) {
			g2d.setFont(Assets.myfont);
			g2d.drawString("fps: "+Main.Main.getFPS(), 2, 7);
		}
		//putting the image onto the display and scaling it
		g.drawImage(image,0,0,width*scale, height*scale, null);
		g.drawImage(Assets.cursor,State.getInputs().getTrueMouseX()-3*Settings.getScale(),State.getInputs().getTrueMouseY()-3*Settings.getScale(),
				Assets.cursor.getWidth()*scale,Assets.cursor.getHeight()*scale,null);
		
	}

	public void update() {
		State.getState().update();
	}
	
	public int getRelativeWidth() {
		return width;
	}

	public int getRelativeHeight() {
		return height;
	}
	//getters
	public int getScale() {
		return scale;
	}
}
