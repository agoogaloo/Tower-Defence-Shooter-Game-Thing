package states.console;

/**
 * this class if for the different commands the consolestate can execute. each command is a different 
 * instance of this class
 * @author The Computer Man
 *
 */
public abstract class Command {
	String helpText="no description";
	/**
	 * this is the method that runs when a command is run from the console
	 * 
	 * @param params - the string of paramiters that can be given by adding brackets at the end of the command
	 * @return - a string with whatever the console should output when the command is finished
	 */
	public abstract String execute(String params);
	
			
	private String[] splitParams(String param) {
		return param.split(",");
		
	}
}
