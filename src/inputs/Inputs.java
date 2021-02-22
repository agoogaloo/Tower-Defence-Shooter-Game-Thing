package inputs;
/*
 * by: Matthew Milum
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.Main;
import saveData.Settings;

//@author Matthew (Organized all player inputs into it's own class, did basically all of this class)
//@author Kevin (Only control key, mouse, and direction in updates)

public class Inputs implements MouseListener, MouseMotionListener, KeyListener{  //Implements different interfaces to allow for mouse presses, mouse motion, and key presses 
	/*
	 *this class separates all the keyboard and mouse inputs from the player class
	 *so that it is smaller and easier to manage. it has a variable for each input 
	 *which is true or false depending on if it is pushed. these variables can be used by 
	 *the is__() methods at the end
	 */
	
	//declaring variables
	private KeyEvent typedKey;
	private boolean[] keys = new boolean[256]; //Contains all possible keys in an array list
	private boolean up, down, left, right, shoot, place; //Keys and actions that are game needs
	private PushButton console = new PushButton(), pause = new PushButton(), upPushed=new PushButton(),
			downPushed=new PushButton(), leftPushed=new PushButton(),rightPushed=new PushButton();
	
	private int mouseX, mouseY;
	
	public Inputs() {
		//adding itself to the Jframe so that the inputs will actually work when the window is open

		Main.getWindow().getFrame().addKeyListener(this);
		Main.getWindow().getFrame().addMouseListener(this);
		Main.getWindow().getFrame().addMouseMotionListener(this);
	}
	public void update() { 
		typedKey=null;
		up = keys[KeyEvent.VK_UP]; //Setting the right index of the array to a boolean 
		down = keys[KeyEvent.VK_DOWN]; //If the key is pushed return the specific boolean associated to the key as true or if it is not pushed return false 
		left = keys[KeyEvent.VK_LEFT]; 
		right = keys[KeyEvent.VK_RIGHT];
		pause.update(keys[KeyEvent.VK_ESCAPE]);
		console.update(keys[KeyEvent.VK_F12]);
		upPushed.update(up);
		downPushed.update(down);
		leftPushed.update(left);
		rightPushed.update(right);
	}
	//mouse position methods
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX=e.getX();//setting the mouse location whenever the mouse is dragged
		mouseY=e.getY();
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX=e.getX();//setting the mouse location whenever the mouse is moved
		mouseY=e.getY();
		
	}
	//mouse input methods
	@Override //Despite not being used these methods are needed by the interface, otherwise it will cause errors
	public void mouseClicked(MouseEvent e) {
		//needed because it implements mouse listener
	}
	@Override

	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1) {
			shoot=true;//the shoot command will be activated when the mouse is clicked
		}else if(e.getButton()==3) {
			place=true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1) {
			shoot=false;//it has been released so the command can stop now
		}else if(e.getButton()==3) {
			place=false;
		};
	}
	//keyboard input methods
	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()]= true;//setting the index of the correct key to true when it is pushed
		
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()]= false;//setting the index of the correct key to false when it is let go
		typedKey=key;
	}
	@Override
	public void keyTyped(KeyEvent key) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//accessor methods for the keys and mouse so that other classes can tell what keys are being pressed
	public boolean isUp() {
		return up;
	}
	public boolean isDown() {
		return down;
	}
	public boolean isLeft() {
		return left;
	}
	public boolean isRight() {
		return right;
	}
	public boolean isShoot() {
		return shoot;
	}
	public boolean isPlace() { 
		return place;	
	}
	public boolean isPause() { 
		return pause.getPushed();	
	}
	public boolean isConsole() { 
		return console.getPushed();	
	}
	public boolean isUpPushed() {
		return upPushed.getPushed();
	}
	public boolean isDownPushed() {
		return downPushed.getPushed();
	}
	public boolean isLeftPushed() {
		return leftPushed.getPushed();
	}
	public boolean isRightPushed() {
		return rightPushed.getPushed();
	}
	
	
	public int getMouseX() {
		return mouseX/Settings.getScale();//the window is scaled so the location for the mouse needs to match it
	}
	public int getMouseY() {
		//the position needs to be a bit offset because if the bar at the top of the window
		return (mouseY-24)/Settings.getScale();//the window is scaled so the location for the mouse needs to match it
	}
	public KeyEvent getTypedKey() {
		return typedKey;
	}
}
