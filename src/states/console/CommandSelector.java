package states.console;

import entity.Entity;
import states.GameState;

public class CommandSelector {
	//the instance of the consolestate that holds this selector is needed for a few commands
	private ConsoleState console;
	
	//Parallel arrays to tell which commands match up with which classes
	private final String[] commandStrings = new String[] {"Help","ShowHitBox", "Freeze", "Fs",
			"EnemyWave","InstaKillEnemy"};
	private final Command[] commands = new Command[] {new Help(), new ShowHitBox(), new Freeze(), 
			new FrameSkip(), new EnemyWave(), new InstaKillEnemies()};
	
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
		//spliting the command into the actual command and the paramiters
		String[] splitCommand=getParams(command);
		
		//looping through the list of valid commands you can type
		for(int i=0;i<commandStrings.length;i++) {
			//checking if they match ignoring case because it is kind of pointless
			if(commandStrings[i].equalsIgnoreCase(splitCommand[0])) {
				//executing the matching command in the parallel array with paramiters given
				return commands[i].execute(splitCommand[1]);
			}
		}
		//saying that you didnt type a valid command if it isnt a command
		return "'"+command+"' is not a valid command. Type 'help' for the list of commands ";
	}
	
	/**
	 * this splits the line into the command part and the paramiter part
	 * @param command - the entire line
	 * @return returns a string array where the 1st object is the command and the 2nd is the string of paramiters 
	 */
	private String[] getParams(String command) {
		//checking if there is an open brake to signal the start of paramiters
		if(!command.contains("(")) {
			//returning an empty string if there is no bracket
			return new String[] {command,""};
		}
		//spliting the string at the open bracket giving an array where the 1st sting is the 
		//command and the 2nd is the paramiters
		String[] returnValue=command.split("\\(", 2);
		//removing the closing bracket from the paramiters if they put one
		if(returnValue[1].charAt(returnValue[1].length()-1)==')') {
			returnValue[1]=returnValue[1].substring(0, returnValue[1].length()-1);
		}
		//retrning the array
		return returnValue;
	}
	
	//all the different commands that can be executed
	private class Help extends Command{
		private Help() {
			helpText="gives info about all the commands";
		}
		
		public String execute(String params) {
			String text="\nCOMMAND LIST:\n\n";
			
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
			ConsoleState.showHitBoxen=!ConsoleState.showHitBoxen;
			
			return "showing hotboxen: "+ConsoleState.showHitBoxen+"\n";
		}
	}
	
	private class Freeze extends Command{
		private Freeze(){
			helpText= "freeze/unfreeze the gameState to see whats happening better\n"+
					"    (only freezes the game states update method)";
		}
		public String execute(String params) {
			ConsoleState.gameFrozen=!ConsoleState.gameFrozen;
			
			return "game frozen: "+ConsoleState.gameFrozen+"\n";
		}
	}
	
	private class FrameSkip extends Command{
		private FrameSkip(){
			helpText= "params: (int amount)      skips the game forward by amount frames and\n"
					+ "    freezes it. If no amount is given it will skip 1 frame\n"
					+ "    (can be unfrozen with the freeze command)";
		}
		public String execute(String params) {
			int amount;
			if(params.equals("")) {
				amount=1;
			}else {
				try {
					amount=Integer.parseInt(params);
				} catch (NumberFormatException e) {
					return "'"+params+"' is not an integer, game state not changed"; 
				}
			}
			ConsoleState.gameFrozen=false;
			for(int i=0;i<amount;i++) {
				console.game.update();
			}
			ConsoleState.gameFrozen=true;
			
			return "game state updated "+amount+" times and frozen\n "
					+ "use freeze command to make the game continue normally";
		}
	}
	
	private class EnemyWave extends Command{
		private EnemyWave(){
			helpText= "params: (int amount)      spawns amount enemies at the end of the \n    players current room";
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
	
	private class InstaKillEnemies extends Command{
		private InstaKillEnemies() {
			helpText="toggles if enemies get 1 shotted by anything that\n    normally deals damage";
		}

		@Override
		public String execute(String params) {
			ConsoleState.instaKillEnemy=!ConsoleState.instaKillEnemy;
			return "instakilling enemies: "+ConsoleState.instaKillEnemy+"\n";
		}
	}
}
