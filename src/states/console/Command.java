package states.console;

public abstract class Command {
	
	public abstract String execute(String params);
	String helpText="no description";
			
	private String[] splitParams(String param) {
		return param.split(",");
		
	}
}
