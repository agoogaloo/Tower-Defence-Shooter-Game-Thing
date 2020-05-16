package floors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/*
 * by: Matthew Milum
 */

public class Room {
	/*
	 * this class represents rooms the individual rooms in a floor each layer in one of the 
	 * json files from tiled represents one room.
	 */
	// declaring instance variables
	public final int TILESIZE = 16, ROOMSIZE = 30;
	private int[][] tiles;
	private char entrance, exit;
	private int doorX,doorY;
	
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
		findDoor();
	}
	private void findDoor() {
		for(int y=0;y<tiles[0].length;y++) {
			
			for(int x=0;x<tiles[0].length;x++) {
				if(tiles[x][y]==43||tiles[x][y]==48) {
					doorX=x;
					doorY=y;
					return;
				}					
			}		
		}
	}
	public void unlock() {
		if(exit=='u'||exit=='d') {
			tiles[doorX][doorY]=46;
			tiles[doorX][doorY+1]=52;
			tiles[doorX][doorY+2]=58;//changing the left rows tiles to open ones
			
			tiles[doorX+1][doorY]=5;
			tiles[doorX+1][doorY+1]=6;
			tiles[doorX+1][doorY+2]=4;//making a path in the middle
			
			tiles[doorX+2][doorY]=47;
			tiles[doorX+2][doorY+1]=53;
			tiles[doorX+2][doorY+2]=59;//changing the right doors tiles to open ones
			
		}else if(exit=='l'||exit=='r') {
			tiles[doorX][doorY-2]=61;
			tiles[doorX][doorY-1]=67;//the left of thetop door
			tiles[doorX][doorY]=63;
			tiles[doorX][doorY+1]=69;
			
			tiles[doorX+1][doorY-2]=62;
			tiles[doorX+1][doorY-1]=68;//the right of thetop door
			tiles[doorX+1][doorY]=64;
			tiles[doorX+1][doorY+1]=70;
			
			tiles[doorX][doorY+3]=65;//the bottom door
			tiles[doorX+1][doorY+3]=71;
			
			tiles[doorX][doorY+2]=2;//fixing the path
			
			
			
		}
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
