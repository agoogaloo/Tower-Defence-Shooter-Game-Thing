package states.menus;

import java.awt.Graphics;
import java.awt.Rectangle;

import inputs.Inputs;

public class Menu extends MenuSelection{
	
	
	
	private MenuObject[] menuObjects;
	private int selection=-1;
	private int hovered=0;
	private boolean inSubMenu=false;
	
	public Menu(Rectangle bounds,String name,  MenuObject[] menuObjects) {
		super(bounds,name);
		this.menuObjects=menuObjects;
	}
	public Menu(Rectangle bounds,  MenuObject[] menuObjects) {
		this(bounds, "", menuObjects);
	}

	
	public void update(Inputs input) {
		if(inSubMenu) {
			((Menu) menuObjects[selection]).update(input);
			if(!menuObjects[selection].selected) {
				inSubMenu=false;
			}
			return;
		}
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
		
		
		if(input.isSelect()) {
			if(selection!=-1) {
				menuObjects[selection].deselect();
			}
			menuObjects[hovered].select();
			selection=hovered;
			if(menuObjects[selection] instanceof Menu) {
				inSubMenu=true;
			}
		}

		
		
	}
		
	@Override
	protected void renderObject(Graphics g) {
		if(inSubMenu) {
			menuObjects[selection].render(g);
			return;
		}
		if(selected) {
			renderOptions(g);
		}else {
			super.renderObject(g);
		}
		
		
	}
	
	private void renderOptions(Graphics g) {
		if(inSubMenu||!selected) {
			return;
		}
		for(MenuObject i: menuObjects) {
			i.render(g);
		}
	}
	
	public void closeSubMenu() {
		selected=false;
		if(inSubMenu) {
			((Menu) menuObjects[selection]).closeSubMenu();
			inSubMenu=false;
			
		}
	}
}
