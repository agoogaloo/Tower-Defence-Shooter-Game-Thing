package graphics.UI;

import java.awt.Graphics;

public abstract class UIElement {
	/*
	 * this is an abstract class representing a part of the UI on the screen such as an icon or a health bar
	 */
	//this is the one UIManager that holds and renders all UI elements
	protected static UIManager UIManager=new UIManager();
	protected int x, y;//where it is on the screen
	public boolean visible=true, remove=false;//whether it is visible and if it should be deleted
	//this lets you add it to a different UIManager if you want
	public UIElement(int x, int y, UIManager manager) {
		this.x = x;
		this.y = y;
		//adding itself to the UIManager
		manager.addElement(this);
	}
	public UIElement(int x, int y) {
		this(x,y,UIManager);//making the ui element and adding it to its UIManager
	}
	
	public abstract void render(Graphics g);
	//some types of UI will need a parameter in their update 
	//methods but others wont so it doesnt have an update method
	public void destroy() {
		remove=true;
	}
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public static UIManager getUIManager() {
		return UIManager;
	}
	
}
