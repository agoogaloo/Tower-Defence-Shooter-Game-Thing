package floors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * by: Matthew Milum
 */


public class Room {
	/*
	 * this class represents rooms the individual rooms in a floor
	 * the files they are loaded from should have each entry split by 1 
	 * or more spaces and can have line breaks but there still needs to be a space at the end.
	 * the 1st entry is the side the player would enter and the 2nd is the players exit
	 * after that there should be the numbers of each tile separated by spaces again 
	 */
	//declaring instance variables
	private int[][] tiles;
	private char entrance, exit;
	//a constructor that takes the path and width of the file that should be loaded
	public Room(String path, int width){
		String fileText="";//the entire text of the file
		String[] entries;//an array that holds all the individual entries the file should hold
		tiles=new int[width][width];
		try{
			File file = new File(path);//creating a file object that stores the actual file
			Scanner scanner = new Scanner(file);//a scanner that can read the data in the file
			while (scanner.hasNextLine()) {//looping until the end of the file is reached
				fileText+=scanner.nextLine();//adding the data into the fileText string
				
			}
			scanner.close();//closing the scanner
		}catch (FileNotFoundException e) {//catching the error
			System.out.println("could not find the file '"+path+"'");//saying what file could not be loaded
			e.printStackTrace();//printing the error
		}
		//splitting the String every blank character so that I can get each entry in the text
		entries=fileText.split("\\s+");
		entrance=entries[0].charAt(0);//setting the entrance to the 1st entry of the file
		exit=entries[1].charAt(0);//setting the entrance to the 2nd entry of the file
		//rooms should be a square so it won't throw an out of bounds exception
		for(int y=0;y<width;y++){//looping though the height of the room
				for(int x=0;x<width;x++){//looping though the width of the room
					//setting the x and y of the array to the one that is in the file
					tiles[x][y]=Integer.parseInt(entries[(y*width)+x+2]);
			}
		}	
	}
	
	public int getTile(int x, int y){
		return tiles[x][y];
	}
	
	//getters/setters
	public char getEntrance() {
		return entrance;
	}
	
	public char getExit() {
		return exit;
	}	
}
