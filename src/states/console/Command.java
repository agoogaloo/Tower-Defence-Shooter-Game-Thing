package states.console;

public abstract class Command {
	
	public abstract String[] execute(String params);
	String[] helpText=new String[] {"no description"};
			
	private String[] splitParams(String param) {
		return new String[]{param};
		
	}
}
