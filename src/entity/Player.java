package entity;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Player extends Mobs implements MouseListener{
	int currency;
	int width=50, height=50;
	int x=540, y=960;
	double targetX, targetY;
	double velocityX, velocityY;
	double bulletPath;
	int shotBuffer = 0;
	
	public void shoot(){
		if (shotBuffer == 0){
			targetX = MouseInfo.getPointerInfo().getLocation().getX();
			targetY = MouseInfo.getPointerInfo().getLocation().getY();
			
			entityManager.add(new bullet (x,y,targetX,targetY));
			shotBuffer = 30;
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0){
		 shoot();
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}