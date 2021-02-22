package states.menus;

import java.awt.Graphics;
import java.awt.Rectangle;

import inputs.Inputs;

public class Menu extends MenuObject{
	
	private MenuObject[] menuObjects;
	private int selection=-1;
	private int hovered=0;
	
	public Menu(Rectangle bounds,  MenuObject[] menuObjects) {
		super(bounds);
		this.menuObjects=menuObjects;
	}

	
	public void update(Inputs input) {
		for(int i=0;i<menuObjects.length;i++) {
			if(menuObjects[i].getBounds().contains(input.getMouseX(), input.getMouseY())) {
				hovered=i;
			}
				
		}
		
		if(input.isDownPushed()) {
			hovered++;
			if(hovered>menuObjects.length-1) {
				hovered=0;
			}
		}
		if(input.isUpPushed()) {
			hovered--;
			if(hovered<0) {
				hovered=menuObjects.length-1;
			}
		}
		menuObjects[hovered].hover();
		
		
		if(selection!=-1) {
			if(input.isLeftPushed()) {
				menuObjects[selection].leftPress();
			}
			if(input.isRightPushed()) {
				menuObjects[selection].rightPress();
			}
		}
		
		
		if(input.isShoot()) {
			if(selection!=-1) {
				menuObjects[selection].deselect();
			}
			menuObjects[hovered].select();
			selection=hovered;
			System.out.println("selected menu thing");			
		}

		
		
	}
		
	@Override
	public void render(Graphics g) {
		for(MenuObject i: menuObjects) {
			i.render(g);
		}
	}
	
	@Override
	public void select() {	
		
	}

	@Override
	public void leftPress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightPress() {
		// TODO Auto-generated method stub
		
	}

	

}
