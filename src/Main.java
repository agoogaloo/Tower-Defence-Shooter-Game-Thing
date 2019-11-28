import window.Window;

public class Main {
	
	public static void main(String[] args) {
		Window window=new Window(800,600);
		while(true) {
			window.update();//updates the window
			window.render();//draws everything on the window
			
			try {//this will be handled better later and is just to test is render works
				Thread.sleep(100l);//stalls for 100 milliseconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}
