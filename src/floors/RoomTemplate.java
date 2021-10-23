package floors;

import java.awt.Point;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import graphics.Assets;

public class RoomTemplate {
	/*
	 * this class represents rooms the individual rooms in a floor each layer in one of the 
	 * json files from tiled represents one room.
	 */
	// declaring instance variables
	public final int DOORU=8, DOORR=9,DOORD=10,DOORL=11, TOWERSPAWN=12, BREAKABLE=16;
	private int[][] tiles;
	private int[][] spawns;
	private char entrance, exit;
	private int width,height, entranceLoc, exitLoc;
	private ArrayList<DoorTemplate> doors = new ArrayList<DoorTemplate>();
	private ArrayList<Point> towerLocs = new ArrayList<Point>();
	private ArrayList<Point> breakables = new ArrayList<Point>();
	
	
	
	public RoomTemplate(JSONObject object) {
		width=(int)((long)object.get("width"));
		height=(int)((long)object.get("height"));
		tiles = new int[width][height];
		spawns = new int[width][height];
		
		JSONArray layers=(JSONArray)object.get("layers");//the array of layers
		
		//getting map data
		JSONObject mapLayer=(JSONObject) layers.get(0);
		JSONArray mapData=(JSONArray)mapLayer.get("data");
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) mapData.get((y *width) + x ));			
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
				
		System.out.print("width="+width+" height="+height+" entr="+entrance+" exit="+exit);
		
		
		//getting spawn data
		JSONObject spawnLayer=(JSONObject) layers.get(1);
		JSONArray spawnData=(JSONArray)spawnLayer.get("data");
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				spawns[x][y] =(int)((long) spawnData.get((y *width) + x ));
				
				switch(spawns[x][y]-Assets.level1tiles.length) {
				case DOORU:
					//System.out.println("door");
					doors.add(new DoorTemplate(x, y, 'u'));
					break;
				case DOORD:
					//System.out.println("door");
					doors.add(new DoorTemplate(x, y, 'd'));
					break;
				case DOORL:
					//System.out.println("door");
					doors.add(new DoorTemplate(x, y, 'l'));
					break;
				case DOORR:
					//System.out.println("door");
					doors.add(new DoorTemplate(x, y, 'r'));
					break;
				case TOWERSPAWN:
					//System.out.println("tower");
					towerLocs.add(new Point(x, y));
					break;
				case BREAKABLE:
					breakables.add(new Point(x, y));
				}
				
			}
		}
		System.out.println("\n");
	}
	
	
	
	// getters/setters
	
	public int[][] getTiles() {
		return tiles;
	}
	
	public int[][] getSpawns() {
		return spawns;
	}
	
	public ArrayList<DoorTemplate> getDoors() {
		return doors;
	}
	
	public ArrayList<Point> getTowerLocs() {
		return towerLocs;
	}
	
	public ArrayList<Point> getBreakables() {
		return breakables;
	}
	
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
	
	
}
class DoorTemplate{
	private int x, y;
	private char direction;
	
	public DoorTemplate(int x, int y, char direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public char getDirection() {
		return direction;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
}
