package entity.mobs;
/*
 * by: Matthew Milum
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Main.Main;

public class PlayerInput implements MouseListener, KeyListener{
	private boolean[] keys = new boolean[256];
	public boolean up, down, left, right, shoot;
	
	public PlayerInput() {
		Main.getWindow().getWindow().addKeyListener(this);
		Main.getWindow().getWindow().addMouseListener(this);
	}
	public void update() {
		up = keys[KeyEvent.VK_UP];//setting the input to true if its button
		down = keys[KeyEvent.VK_DOWN];//is pushed or false if its not pushed 
		left = keys[KeyEvent.VK_LEFT];//by setting it to the right index in the array
		right = keys[KeyEvent.VK_RIGHT];
		
	}
	
	//mouse input methods
	@Override
	public void mouseClicked(MouseEvent e) {
		// needed because it implements mouseListener
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// needed because it implements mouseListener
	}

	@Override
	public void mousePressed(MouseEvent e) {
		shoot=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		shoot=false;
	}
	
	//keyboard input methods
	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()]= true;//setting the index of the right key to true when it is pushed	
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()]= false;//setting the index of the right key to false when it is let go
	}
	@Override
	public void keyTyped(KeyEvent key) {
		// needed because it implements KeyListener
	}
	//getters for the keys so that other classes can tell what keys are being pressed
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
}
