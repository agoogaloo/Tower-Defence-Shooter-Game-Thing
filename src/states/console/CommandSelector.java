package states.console;

import entity.Entity;
import states.GameState;

public class CommandSelector {
	//the instance of the consolestate that holds this selector is needed for a few commands
	private ConsoleState console;
	
	//Parallel arrays to tell which commands match up with which classes
	private final String[] commandStrings = new String[] {"Help","EntityCount","ShowHitBox", "ShowFPS",
			"Freeze", "Fs", "NewWave","InstaKillEnemy", "Unlock","Money","Heal","Chest", "Give", "Ghost"};
	private final Command[] commands = new Command[] {new Help(),new EntityCount(), new ShowHitBox(), 
			new ShowFPS(), new Freeze(), new FrameSkip(), new EnemyWave(), new InstaKillEnemies(), 
			new Unlock(),new Money(), new Heal(), new Chest(), new Give(), new Ghost()};
	
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
	private class EntityCount extends Command{
		private EntityCount() {
			helpText="tells you how many entities there are in the gamestate currently";
		}
		
		public String execute(String params) {
			return "there are currently"+Entity.getEntityManager().getEntities().size()+" entities\n";
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
	
	private class ShowFPS extends Command{
		private ShowFPS(){
			helpText= "toggles if you can see the framerate in the top left corner";
		}
		public String execute(String params) {
			ConsoleState.showFPS=!ConsoleState.showFPS;
			
			return "showing frame rate: "+ConsoleState.showFPS+"\n";
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
			int spawnRoomX=0;
					//(Entity.getEntityManager().getPlayer().getX()/GameState.getFloor().
					//TILESIZE)/GameState.getFloor().getRoomSize();
			int spawnRoomY=0;
			//(Entity.getEntityManager().getPlayer().getY()/GameState.getFloor().
			//		TILESIZE)/GameState.getFloor().getRoomSize();
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
	
	private class Unlock extends Command{
		private Unlock() {
			helpText="unlocks all rooms in the current floor";
		}

		@Override
		public String execute(String params) {
			for (int y = 0; y < GameState.getFloor().getSize(); y++) {// looping though all the tiles
				for (int x = 0; x < GameState.getFloor().getSize()*2; x++) {
					try {
						GameState.getFloor().getRoom(x, y).unlock();
					} catch (NullPointerException e) {
						
					}
				}
			}
			return "rooms unlocked";
		}
	}
	private class Money extends Command{
		private Money() {
			helpText="params: (int amount)      gives you money";
		}
		@Override
		public String execute(String params) {
			
			try {
				int amount = Integer.parseInt(params);
				Entity.getEntityManager().getPlayer().giveMoney(amount);
				
			}catch(NumberFormatException e) {
				return"paramiter given is not an int";
			}
			
			return "gave "+params+" money to the player";
		}
		
	}
	private class Heal extends Command{
		private Heal() {
			helpText="params: (int amount)      gives you amount health";
		}
		@Override
		public String execute(String params) {
			
			try {
				int amount = Integer.parseInt(params);
				Entity.getEntityManager().getPlayer().heal(amount);
				
			}catch(NumberFormatException e) {
				return"paramiter given is not an int";
			}
			
			return "gave "+params+" health to the player";
		}
		
	}
	private class Chest extends Command{
		private Chest(){
			helpText= "spawns a chest with a random item inside";
		}
		public String execute(String params) {
			Entity.getEntityManager().addEntity(new entity.statics.Chest(Entity.getEntityManager().getPlayer().getX()
					, Entity.getEntityManager().getPlayer().getY()));
			return "spawned a chest";
		}
	}
	private class Give extends Command{
		private Give(){
			helpText= "params: (int id)  gives you an item of id";
		}
		public String execute(String params) {
			try {
				int id = Integer.parseInt(params);
				Entity.getEntityManager().getPlayer().giveItem(id);
				return "gave item "+id;
				
			}catch(NumberFormatException e) {
				return"paramiter given is not an int";
			}
			
		}
	}
	private class Ghost extends Command{
		private Ghost(){
			helpText= "lets you go through walls";
		}
		public String execute(String params) {
			Entity.getEntityManager().getPlayer().toggleGhost();
			return "ghost mode toggled";
		}
	}
	
}
