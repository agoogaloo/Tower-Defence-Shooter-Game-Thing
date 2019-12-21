package window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/*
 * by: Matthew Milum
 * extending JPanel so that you have access to the PaintComponent to actually draw things
 * everything is drawn onto a display which is then added onto the window,
 * because you aren't supposed to draw directly onto it
 */
public class Display extends JPanel{
	private int imgX=0, imgY=0,width, height, scale;
	private BufferedImage image=Window.getAssets().getTest();//the image that moves around the screen
	
	public Display(int width, int height, int scale){
		 //setting the proper size so that the window will pack properly
		 //the display is scaled up to look 8-bit so the 
		 //resolution is actually 1/3 of the screen width
		this.width=width/scale;
		this.height=height/scale;
		this.scale=scale;
		
		this.setPreferredSize(new Dimension(width, height));
		//setting the preferred size to the inputed one so that the pack method will work properly
	
		
	}
	@Override
    public void paintComponent(Graphics g) {//where everything is actually drawn
		//a normal graphics object cannot scale so I cast it to a graphisc2D which can
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.scale(scale, scale);//scaling the graphics
		g2d.clearRect(0, 0, width, height);//clearing the previous frame
		Window.getFloor().render(g2d);
        g2d.drawImage(image, imgX, imgY, null);//drawing the image to the screen
      
        
	}
	/*
	 * the display probably shouldn't have an update method and everything will be updated
	 * from the window class but the rectangle is in the display class so I did it this way
	 */
	public void update() {
		imgX+=1;//changing the x and y so it moves
		imgY+=1;
	}

}
