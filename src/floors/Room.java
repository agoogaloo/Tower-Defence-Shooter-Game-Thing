package floors;

import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import graphics.Assets;
/*
 * by: Matthew Milum
 */

public class Room {
	/*
	 * this class represents rooms the individual rooms in a floor each layer in one of the 
	 * json files from tiled represents one room.
	 */
	// declaring instance variables
	public final int TILESIZE = 16;
	private int[][] tiles;
	private int[][] spawns;
	private char entrance, exit;
	private int doorX,doorY, width,height, entranceLoc, exitLoc;
	private int x, y;
	
	// the  constructor takes the location and width of the file that should be
	// loaded all and rooms are squares so the width will be the same as the height
	public Room(Room original, int x, int y) {
		//this constructor lets us copy rooms so we dont change things we dont want to change
	
		tiles=new int[original.tiles.length][];
		spawns=new int[original.spawns.length][];
		entrance=original.entrance;
		exit=original.exit;
		entranceLoc=original.entranceLoc;
		exitLoc=original.exitLoc;
		doorX=original.doorX;
		doorY=original.doorY;
		width=original.width;
		height=original.height;
		this.x=x;
		this.y=y;
		
		for(int i = 0; i < original.tiles.length; i++) {
			tiles[i] = Arrays.copyOf(original.tiles[i], original.tiles[i].length);
		}
		for(int i = 0; i < original.spawns.length; i++) {
			spawns[i] = Arrays.copyOf(original.spawns[i], original.spawns[i].length);
		}
	}
	public Room(JSONObject object) {
		width=(int)((long)object.get("width"));
		height=(int)((long)object.get("height"));
		tiles = new int[width][height];
		spawns = new int[width][height];
		
		JSONArray layers=(JSONArray)object.get("layers");//the array of layers
		
		//getting map data
		JSONObject mapLayer=(JSONObject) layers.get(0);
		JSONArray mapData=(JSONArray)mapLayer.get("data");
		System.out.print("width="+width+" height="+height+" size="+mapData.size()+" map:\n");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) mapData.get((y *width) + x ));			
			}
		}
		
		//getting spawn data
		JSONObject spawnLayer=(JSONObject) layers.get(1);
		JSONArray spawnData=(JSONArray)spawnLayer.get("data");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				spawns[x][y] =(int)((long) spawnData.get((y *width) + x ));
			}
		}
		

		//getting entrance/exits and their locations
		JSONArray properties=(JSONArray)object.get("properties");
		//the map properties are stored alphabetically so we know where each property is in the list
		JSONObject entrDirObject=(JSONObject)properties.get(0);
		JSONObject entrLocObject=(JSONObject)properties.get(1);
		JSONObject exitDirObject=(JSONObject)properties.get(2);
		JSONObject exitLocObject=(JSONObject)properties.get(3);
		
		entrance=((String)entrDirObject.get("value")).charAt(0);
		exit=((String)exitDirObject.get("value")).charAt(0);
		
		entranceLoc=Integer.parseInt((String) entrLocObject.get("value"));
				
		exitLoc=Integer.parseInt((String) exitLocObject.get("value"));
		findDoor();
	}
	
	/*public  Room(JSONObject object) {
		width=(int)((long)object.get("width"));
		height=(int)((long)object.get("height"));
		tiles = new int[width][height];
		spawns = new int[width][height];
		
		JSONArray layers=(JSONArray)object.get("layers");//the array of layers
		
		JSONObject mapLayer=(JSONObject) layers.get(0);
		JSONArray mapData=(JSONArray)mapLayer.get("data");
		System.out.print("width="+width+" height="+height+" size="+mapData.size()+" map:\n");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) mapData.get((y *width) + x ));			
			}
		}
		
		JSONObject spawnLayer=(JSONObject) layers.get(1);
		JSONArray spawnData=(JSONArray)spawnLayer.get("data");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				spawns[x][y] =(int)((long) spawnData.get((y *width) + x ));
			}
		}
		

		//getting entrance/exits
		JSONArray properties=(JSONArray)object.get("properties");
		JSONObject entranceObject=(JSONObject)properties.get(0);
		JSONObject exitObject=(JSONObject)properties.get(1);
		entrance=((String)entranceObject.get("value")).charAt(0);
		exit=((String)exitObject.get("value")).charAt(0);
		findDoor();
	}*/
	private void findDoor() {
		for(int y=0;y<tiles[0].length;y++) {
			for(int x=0;x<tiles.length;x++) {				
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
		unlock(doorX,doorY);
	}
	public void unlock(int doorX, int doorY) {
		if(doorX==-1&&doorY==-1) {
			return;
		}
		if(tiles[doorX][doorY]==64) {
			tiles[doorX][doorY]=60;
			tiles[doorX][doorY+1]=67;
			tiles[doorX][doorY+2]=74;//changing the left rows tiles to open ones
			
			tiles[doorX+2][doorY]=61;
			tiles[doorX+2][doorY+1]=68;
			tiles[doorX+2][doorY+2]=75;//changing the right doors tiles to open ones
			
			//if(tiles[doorX+1][doorY-1]!=21) {//checking if there is a path right above the door
				tiles[doorX+1][doorY]=8;
				tiles[doorX+1][doorY+1]=8;
				tiles[doorX+1][doorY+2]=8;//making a path in the middle
			/*}else {
				tiles[doorX+1][doorY]=21;
				tiles[doorX+1][doorY+1]=21;
				tiles[doorX+1][doorY+2]=21;//making a path in the middle
			}*/
			
		}else if(tiles[doorX][doorY]==69) {
			tiles[doorX][doorY-2]=41;
			tiles[doorX][doorY-1]=48;//the left of thetop door
			tiles[doorX][doorY]=55;
			tiles[doorX][doorY+1]=62;
			
			tiles[doorX+1][doorY-2]=42;
			tiles[doorX+1][doorY-1]=49;//the right of thetop door
			tiles[doorX+1][doorY]=56;
			tiles[doorX+1][doorY+1]=63;
			
			tiles[doorX][doorY+3]=57;//the bottom door
			tiles[doorX+1][doorY+3]=58;
			
			tiles[doorX][doorY+2]=1;//fixing the path
			
			
			
		}
	}
	public int getTile(int x, int y) {
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
			return 44;
		}
		return tiles[x][y];
	}

	public int getSpawnData(int x, int y) {
		if (x < 0 || x >= spawns.length || y < 0 || y >= spawns[0].length) {
			return 0;
		}
		return spawns[x][y]-Assets.level1tiles.length;
	}
	// getters/setters
	public char getEntrance() {
		return entrance;
	}

	public char getExit() {
		return exit;
	}
	public int getEntranceLoc() {
		return entranceLoc;
	}
	public int getExitLoc() {
		return exitLoc;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
}
