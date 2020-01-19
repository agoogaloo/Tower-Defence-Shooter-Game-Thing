package floors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/*
 * by: Matthew Milum
 */

public class Room {
	/*
	 * this class represents rooms the individual rooms in a floor the files they
	 * are loaded from should have each entry split by a comma then 1 or more spaces and can have
	 * line breaks but there still needs to be a space at the end. the 1st entry is
	 * the direction the player would be when they enter would enter and the 2nd is
	 * the players exit after that there should be the numbers of each tile separated
	 * by commas and spaces
	 */
	// declaring instance variables
	public final int TILESIZE = 16, ROOMSIZE = 20;
	private int[][] tiles;
	private char entrance, exit;
	
	// the  constructor takes the location and width of the file that should be
	// loaded all and rooms are squares so the width will be the same as the height
	public Room(String path, int width) {
		String fileText = "";// the entire text of the file
		String[] entries;// an array that holds all the individual entries the file should hold
		tiles = new int[width][width];
		try {
			File file = new File(path);// creating a file object that stores the actual file
			Scanner scanner = new Scanner(file);// a scanner that can read the data in the file
			while (scanner.hasNextLine()) {// looping until the end of the file is reached
				fileText += scanner.nextLine();// adding the data into the fileText string

			}
			scanner.close();// closing the scanner
		} catch (FileNotFoundException e) {// catching the error if the file doesn't exist
			System.out.println("could not find the file '" + path + "'");// saying what file could not be loaded
			e.printStackTrace();// printing the error
		}
		// splitting the String every blank character so that I can get each entry in the string
		entries = fileText.split(",\\s+");
		try {
			entrance = entries[0].charAt(0);// setting the entrance to the 1st entry of the file
			exit = entries[1].charAt(0);// setting the entrance to the 2nd entry of the file
			// rooms should be a square so it won't throw an out of bounds exception
			for (int y = 0; y < width; y++) {// looping though the height of the room
				for (int x = 0; x < width; x++) {// looping though the width of the room
					// setting the x and y of the array to the one that is in the file
					tiles[x][y] = Integer.parseInt(entries[(y * width) + x + 2]);
				}
			}
		} catch (Exception e) {
			//if there is a problem with the way the file is formated this 
			//will tell you without stopping the entire program
			System.out.println("coulndt read " + path+" (probably formating error)");
			e.printStackTrace();//saying the error so if you need to you can still tell what it is
		}
	}
	public Room(JSONObject object) {
		int width=(int)((long)object.get("width"));
		tiles = new int[width][width];
		JSONArray data=(JSONArray)object.get("data");
		System.out.print("width="+width+"size="+data.size()+"map:\n");
		for(int y=0;y<width;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) data.get((y * width) + x ));	
				System.out.print(tiles[x][y]+" ");
			}
			System.out.print("\n");
		}
		
		JSONArray properties=(JSONArray)object.get("properties");
		JSONObject entranceObject=(JSONObject)properties.get(0);
		JSONObject exitObject=(JSONObject)properties.get(1);
		entrance=((String)entranceObject.get("value")).charAt(0);
		exit=((String)exitObject.get("value")).charAt(0);
	}
	public int getTile(int x, int y) {
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles.length) {
			return 27;
		}
		return tiles[x][y];
	}

	// getters/setters
	public char getEntrance() {
		return entrance;
	}

	public char getExit() {
		return exit;
	}
}
