package inputs;

public class PushButton {
	/*
	 * this is for buttons that should only say they are pushed the 1st 
	 * time they are pushed such as the pause or place tower button and not the entire time 
	 * they are held like movement buttons 
	 */
	private boolean held, pushed;
	public boolean update(boolean isHeld) {
		if(isHeld) {//checking if the button is being held down on this frame
			if(!held) {//checking f the button has been pushed down on the other frames so it only gives the signal once
				held=true;
				pushed=true;
				return true;
			}
		}
		else {//the button is not being held down so pushed can be set to false again so it will be able to register the next inputs
			held=false;
		}
		pushed=false;
		return false;
	}
	public boolean getPushed() {
		return pushed;
	}

}
