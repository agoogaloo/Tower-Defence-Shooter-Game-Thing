package floors;

import java.util.Arrays;

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
	public Room(Room original) {
		//this constructor lets us copy rooms so we dont change things we dont want to change
	
		tiles=new int[original.tiles.length][];
		entrance=original.entrance;
		exit=original.exit;
		doorX=original.doorX;
		doorY=original.doorY;
		
		for(int i = 0; i < original.tiles.length; i++) {
			tiles[i] = Arrays.copyOf(original.tiles[i], original.tiles[i].length);
		}
	}
	
	public Room(JSONObject object) {
		int width=(int)((long)object.get("width"));
		int height=(int)((long)object.get("height"));
		tiles = new int[width][height];
		JSONArray data=(JSONArray)object.get("data");
		System.out.print("width="+width+"height="+height+"size="+data.size()+"map:\n");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) data.get((y *width) + x ));			
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
				if(tiles[x][y]==64||tiles[x][y]==69) {
					doorX=x;
					doorY=y;
					return;
				}					
			}		
		}
		doorX=-1;
		doorY=-1;
	}
	public void unlock() {
		if(doorX==-1&&doorY==-1) {
			return;
		}
		if(exit=='u'||exit=='d') {
			tiles[doorX][doorY]=60;
			tiles[doorX][doorY+1]=67;
			tiles[doorX][doorY+2]=74;//changing the left rows tiles to open ones
			
			tiles[doorX+1][doorY]=9;
			tiles[doorX+1][doorY+1]=7;
			tiles[doorX+1][doorY+2]=10;//making a path in the middle
			
			tiles[doorX+2][doorY]=61;
			tiles[doorX+2][doorY+1]=68;
			tiles[doorX+2][doorY+2]=75;//changing the right doors tiles to open ones
			
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
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
			return 44;
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
