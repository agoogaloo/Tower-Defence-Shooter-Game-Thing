package states.console;

import entity.Entity;
import states.GameState;

public class CommandSelector {
	//the instance of the consolestate that holds this selector is needed for a few commands
	private ConsoleState console;
	//Parallel arrays to tell which commands match up with which classes
	private final String[] commandStrings = new String[] {"Help","ShowHitBox", "Freeze", "Nf", "EnemyWave"};
	private final Command[] commands = new Command[] {new Help(), new ShowHitBox(), new Freeze(), 
			new NextFrame(), new EnemyWave()};
	
	public CommandSelector(ConsoleState console) {
		this.console = console;
	}
	
	/**
	 * this will execute the right command with the right paramiters when it is called
	 * 
	 * @param command - the entire line that has been typed into the console to give the command including the paramiters 
	 * @return the text the the command wants to display on the console or to let you know it didnt recognize a command
	 */
	public String executeCommand(String command) {
		String[] splitCommand=getParams(command);
		for(int i=0;i<commandStrings.length;i++) {
			if(commandStrings[i].equalsIgnoreCase(splitCommand[0])) {
				return commands[i].execute(splitCommand[1]);
			}
		}
		return "'"+command+"' is not a valid command";
	}
	
	/**
	 * this splits the line into the command part and the paramiter part
	 * @param command - the entire line
	 * @return returns a string array where the 1st object is the command and the 2nd is the string of paramiters 
	 */
	private String[] getParams(String command) {
		if(!command.contains("(")) {
			return new String[] {command,""};
		}
		
		String[] returnValue=command.split("\\(", 2);
		if(returnValue[1].charAt(returnValue[1].length()-1)==')') {
			returnValue[1]=returnValue[1].substring(0, returnValue[1].length()-1);
		}
		
		return returnValue;
		
		
	}
	//all the different commands that can be executed
	
	private class Help extends Command{
		private Help() {
			helpText="gives info about all the commands";
		}
		
		public String execute(String params) {
			String text="\nCOMMANDLIST:\n\n";
			
			for(int i=0;i<commands.length;i++) {
				text+=commandStrings[i]+" -- "+commands[i].helpText+"\n";	
			}
			return text;
		}
	}
	
	private class ShowHitBox extends Command{
		private ShowHitBox(){
			helpText= "toggles if the hitboxen of all entities is shown or not";
		}
		public String execute(String params) {
			console.showHitBoxen=!ConsoleState.showHitBoxen;
			
			return "showing hotboxen: "+ConsoleState.showHitBoxen+"\n";
		}
	}
	
	private class Freeze extends Command{
		private Freeze(){
			helpText= "freeze/unfreeze the gameState to see whats happening better\n"+
					"  (only freezes the game states update method)";
		}
		public String execute(String params) {
			console.gameFrozen=!ConsoleState.gameFrozen;
			
			return "game frozen: "+ConsoleState.gameFrozen+"\n";
		}
	}
	
	private class NextFrame extends Command{
		private NextFrame(){
			helpText= "progresses the game by 1 frame and freezes it\n"+
					"  (can be unfrozen with the freeze command)";
		}
		public String execute(String params) {
			console.gameFrozen=false;
			console.game.update();
			console.gameFrozen=true;
			return "game state frozen on next frame"+"\n";
		}
	}
	
	private class EnemyWave extends Command{
		private EnemyWave(){
			helpText= "params: (int amount)\n"+
					"  spawns amount enemies at the end of the players current room";
		}
		public String execute(String params) {
			int amount;
			int spawnRoomX=(Entity.getEntityManager().getPlayer().getX()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			int spawnRoomY=(Entity.getEntityManager().getPlayer().getY()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			try {
				amount=Integer.parseInt(params);
			} catch (NumberFormatException e) {
				return "'"+params+"' is not an integer, no enemies spawned"; 
			}
			
			Entity.getEntityManager().getSpawner().newWave(spawnRoomX, spawnRoomY, amount);
			return "wave with "+amount+" enemies added to queue\n";
		}
	}
}
