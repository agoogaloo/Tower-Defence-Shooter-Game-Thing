package entity.mobs;
/*
 * by: Matthew Milum
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Main.Main;

//@author Matthew (Organized all player inputs into it's own class, did basically all of this class)
//@author Kevin (Only control key, mouse, and direction in updates)

public class PlayerInput implements MouseListener, MouseMotionListener, KeyListener{  //Implements different interfaces to allow for mouse presses, mouse motion, and key presses 
	/*
	 *this class separates all the keyboard and mouse inputs from the player class
	 *so that it is smaller and easier to manage. it has a variable for each input 
	 *which is true or false depending on if it is pushed. these variables can be used by 
	 *the is__() methods at the end
	 */
	
	//declaring variables
	private boolean[] keys = new boolean[256]; //Contains all possible keys in an array list
	private boolean up, down, left, right, control, shoot; //Keys and actions that are game needs
	private char direction='d'; //Sets player's direction to down by default at the start
	private int mouseX, mouseY;
	
	public PlayerInput() {
		//adding itself to the Jframe so that the inputs will actually work when the window is open

		Main.getWindow().getFrame().addKeyListener(this);
		Main.getWindow().getFrame().addMouseListener(this);
		Main.getWindow().getFrame().addMouseMotionListener(this);
	}
	public void update() { 
		up = keys[KeyEvent.VK_UP]; //Setting the right index of the array to a boolean 
		down = keys[KeyEvent.VK_DOWN]; //If the key is pushed return the specific boolean associated to the key as true or if it is not pushed return false 
		left = keys[KeyEvent.VK_LEFT]; 
		right = keys[KeyEvent.VK_RIGHT];
		control = keys[KeyEvent.VK_CONTROL];

		//setting the diection the player is facing so it is easy to tell which animation should be playing
		if(up) {
			direction = 'u';
		}else if(down) { //Same but for the other directions
			direction = 'd';
		}else if(left) {
			direction = 'l';
		}else if(right) {
			direction = 'r';
		}
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
		shoot=true;//the shoot command will be activated when the mouse is clicked
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		shoot=false;//it has been released so the command can stop now
	}
	//keyboard input methods
	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()]= true;//setting the index of the correct key to true when it is pushed	
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()]= false;//setting the index of the correct key to false when it is let go
	}
	@Override
	public void keyTyped(KeyEvent key) {
		// needed because it implements KeyListener
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
	public boolean isControl() {
		return control;
	}
	public char getDirection(){
		return direction;
	}
	public int getMouseX() {
		return mouseX/3;//the window is scaled so the location for the mouse needs to match it
	}
	public int getMouseY() {
		//the position needs to be a bit offset because if the bar at the top of the window
		return (mouseY-24)/3;//the window is scaled so the location for the mouse needs to match it
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
}
