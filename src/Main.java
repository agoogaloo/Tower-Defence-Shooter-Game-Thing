import window.Window;

public class Main {
	/*
	 * the main class that will create the window then 
	 * updates and renders it (basically holds the main game loop)
	 */
	public static void main(String[] args) {
		Window window=new Window(800,600);
		boolean run=true;
		
		while(run) {
			window.update();//updates the window
			window.render();//draws everything on the window
			
			try {//this will be handled better later and is just to test is render works
				Thread.sleep(10l);//stalls for 100 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}
