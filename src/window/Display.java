package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * extending JPanel so that you have access to the PaintComponent to actually draw things
 * everything is drawn onto a display which is then added onto the window,
 * because you aren't supposed to draw directly onto it
 */
public class Display extends JPanel{
	private int imgX=0, imgY=0,width, height;
	private BufferedImage image;
	
	public Display(int width, int height) {
		//setting the proper size so that the window will pack properly
		this.width=width/3;//the display is scaled up to look 8-bit so the 
		this.height=height/3;//resolution is actually 1/3 of the screen width
		/*this will probably be done in a separate class to hold all the assets and stuff but I
		 * just wanted to test the scaling effect
		 */
		try { //this can throw an error so it needs to be in a try catch to run
			image =ImageIO.read(new File ("res/test.png"));//loading the test image
		} catch (IOException e) {
			System.out.println("picture not loaded");//showing what the problem is
		}
		
		this.setPreferredSize(new Dimension(width, height));
		//setting the preferred size to the inputed one so that the pack method will work properly
	
		
	}
	@Override
    public void paintComponent(Graphics g) {//where everything is actually drawn
		//a normal graphics object cannot scale so I cast it to a graphisc2D which can
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.scale(3, 3);//scaling the graphics
		g2d.clearRect(0, 0, width, height);//clearing the previous frame
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
