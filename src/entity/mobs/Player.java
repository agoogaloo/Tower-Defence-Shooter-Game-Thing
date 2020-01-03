package entity.mobs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import entity.EntityManager;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
/**
 * @author Sahib
 */
public class Player extends Mobs implements MouseListener{
	int currency;
	int width=50, height=50;
	int x=540, y=960;
	double targetX, targetY;
	double velocityX, velocityY;
	double bulletPath;
	int shotBuffer=0;
	int speed=1;
	int health=100;
	public void keypressed(KeyEvent e){
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_A){
			changeX=-speed;
		}
		if (key==KeyEvent.VK_D){
			changeX=+speed;
		}
		if (key==KeyEvent.VK_W){
			changeY=+speed;
		}
		if (key==KeyEvent.VK_S){
			changeY=-speed;
		}
	}
	public void keyreleased(KeyEvent e){
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_A){
			changeX=0;
		}
		if (key==KeyEvent.VK_D){
			changeX=0;
		}
		if (key==KeyEvent.VK_W){
			changeY=0;
		}
		if (key==KeyEvent.VK_S){
			changeY=0;
		}
	}
	
	/**
	 * @author Kevin Tea
	 */
	public void shoot(){
		if (shotBuffer == 0){
			targetX = MouseInfo.getPointerInfo().getLocation().getX();
			targetY = MouseInfo.getPointerInfo().getLocation().getY();
			
			EntityManager.addEntity(new Bullet (x,y,targetX,targetY));
			shotBuffer = 30;
		}
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
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
	
	@Override
	public void update(){
		x+=changeX;
		y+=changeY;
//		if (EntityManager.getEntities().contains(Bullet)){
//			health-=2;
//		}
//		if (EntityManager.getEntities().contains(enemies)){
//			health-=1;
//		}
		shoot();
		shotBuffer-=1;
	}
	
//	@Override
//	public void render() {
//		
//	}
}
