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
	public final int TILESIZE = 16, ROOMSIZE = 30;
	private int[][] tiles;
	private char entrance, exit;
	
	// the  constructor takes the location and width of the file that should be
	// loaded all and rooms are squares so the width will be the same as the height
	public Room(JSONObject object) {
		int width=(int)((long)object.get("width"));
		tiles = new int[width][width];
		JSONArray data=(JSONArray)object.get("data");
		System.out.print("width="+width+"size="+data.size()+"map:\n");
		for(int y=0;y<width;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) data.get((y * width) + x ));			
			}
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
