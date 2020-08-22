package states.console;

import java.util.ArrayList;

public class CommandSelector {
	//paralel arrays to tell which commands match up with which classes
	private final String[] commandStrings = new String[] {"help","showHitBox", "freeze", "nf"};
	
	private final Command[] commands = new Command[] {new Help(), new ShowHitBox(), new Freeze(), new NextFrame()};
	
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
			helpText=new String[]{"gives info about all the commands"};
		}
		public String[] execute(String params) {
			ArrayList<String> text=new ArrayList<String>();
			String[] returnValue;
			text.add("");
			text.add("COMMAND LIST:");
			for(int i=0;i<commands.length;i++) {
				String commandName=commandStrings[i]+" -- ";
				for(String helpLine:commands[i].helpText) {
					text.add(commandName+helpLine);
					commandName="";
				}
				text.add("");
			}
			
			returnValue=text.toArray(new String[0]);
			return returnValue;
		}
	}
	
	private class ShowHitBox extends Command{
		private ShowHitBox(){
			helpText= new String[]{"toggles if the hitboxen of all entities is shown or not"};
		}
		public String[] execute(String params) {
			ConsoleState.showHitBoxen=!ConsoleState.showHitBoxen;
			
			return new String[] {"showing hotboxen: "+ConsoleState.showHitBoxen,""};
		}
	}
	
	private class Freeze extends Command{
		private Freeze(){
			helpText= new String[] {"freeze/unfreeze the gameState to see whats happening better",
					"(only freezes the game states update method)"};
		}
		public String[] execute(String params) {
			ConsoleState.gameFrozen=!ConsoleState.gameFrozen;
			
			return new String[] {"game frozen: "+ConsoleState.gameFrozen,""};
		}
	}
	
	private class NextFrame extends Command{
		private NextFrame(){
			helpText= new String[] {"progresses the game by 1 frame (only really works after freeze)"};
		}
		public String[] execute(String params) {
			ConsoleState.nextFrame=!ConsoleState.nextFrame;
			
			return new String[] {"game state sent to next frame"};
		}
	}
}
