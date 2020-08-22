package states.console;

public class CommandSelector {
	private final String[] commandStrings = new String[] {"help","showHitBox"};
	
	private final Command[] commands = new Command[] {new Help(), new ShowHitBox()};
	
	public String[] executeCommand(String command) {
		for(int i=0;i<commandStrings.length;i++) {
			if(commandStrings[i].equalsIgnoreCase(command)) {
				return commands[i].execute("");
			}
		}
		return new String[] {"'"+command+"' is not a valid command"};
	}

	private class Help extends Command{
		private Help() {
			helpText="gives info about all the commands";
		}
		public String[] execute(String params) {
			String[] text=new String[commands.length+3];
			text[0]="";
			text[1]="COMMAND LIST:";
			text[text.length-1]="";
			for(int i=0;i<commands.length;i++) {
				text[i+2]=commandStrings[i]+" -- "+commands[i].helpText;
			}
			
			return text;
		}
	}	
	private class ShowHitBox extends Command{
		private ShowHitBox(){
			helpText="shows the hitboxen of all entities";
		}
		public String[] execute(String params) {
			ConsoleState.showHitBoxen=!ConsoleState.showHitBoxen;
			
			return new String[] {"showing hotboxen: "+ConsoleState.showHitBoxen,""};
		}
	}
}
