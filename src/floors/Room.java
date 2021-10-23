package floors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.statics.breakables.Breakable;
import entity.statics.doors.Door;
import entity.statics.towers.TowerSpawn;
import graphics.Assets;

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
	private int width,height, entranceLoc, exitLoc;
	private int x, y;
	private boolean opened=false;
	private ArrayList<Door> doors = new ArrayList<Door>();
	private ArrayList<TowerSpawn> towerLocs = new ArrayList<TowerSpawn>();
	private ArrayList<Breakable> breakables = new ArrayList<Breakable>();
	private ArrayList<Room> connectedRooms = new  ArrayList<Room>();
	
	// the  constructor takes the location and width of the file that should be
	// loaded all and rooms are squares so the width will be the same as the height
	public Room(RoomTemplate template,int levelID, int x, int y) {
		//this constructor lets us copy rooms so we dont change things we dont want to change
	
		tiles=template.getTiles();
		spawns=template.getSpawns();
		entrance=template.getEntrance();
		exit=template.getExit();
		entranceLoc=template.getEntranceLoc();
		exitLoc=template.getExitLoc();
		width=template.getWidth();
		height=template.getHeight();
		this.x=x;
		this.y=y;
		
		for(DoorTemplate i:template.getDoors()) {
			Door door= Door.getProperDoor(TILESIZE*(x+i.getX()),TILESIZE*(y+i.getY()),levelID, i.getDirection());
			doors.add(door);
		}
		for(Point i:template.getTowerLocs()) {
			TowerSpawn tower=new TowerSpawn(TILESIZE*(x+i.x),TILESIZE*(y+i.y),false);
			towerLocs.add(tower);
		}
		for(Point i:template.getBreakables()) {
			if(ThreadLocalRandom.current().nextBoolean()) {
				Breakable breakable=Breakable.getProperBreakable(levelID,TILESIZE*(x+i.x),TILESIZE*(y+i.y));
				breakables.add(breakable);
			}
		}
	}
	
	/**
	 * unlocks all doors in the room
	 * @return - the amount of doors it unlocks
	 */
	
	public int open() {
		int unlocks=0;
		if(!opened) {
			for(Door d:doors) {
				Entity.getEntityManager().addEntity(d);
			}
			for(TowerSpawn t:towerLocs) {
				Entity.getEntityManager().addEntity(t);
			}
			
			opened=true;
			return unlocks;
		}
		
		for(Door i:doors) {
			if(i.isSolid()) {
				unlocks++;
				i.unlock();
			}
		}
		for(Room r:connectedRooms) {
			r.open();
		}
		return unlocks;
	}
		
	public void unlock(int index) {
		doors.get(index).unlock();
		for(Door i:doors) {
			if(i.isSolid()) {
				return;
			}
		}
		opened=true;
	}
	public boolean isOpened() {
		return opened;
	}
	
	public void addConnectedRoom(Room room) {
		connectedRooms.add(room);
	}
	public ArrayList<Room> getConnectedRooms() {
		return connectedRooms;
	}
	
	public ArrayList<Door> getDoors() {
		return doors;
	}
	public ArrayList<TowerSpawn> getTowerLocs() {
		return towerLocs;
	}
	public ArrayList<Breakable> getBreakables() {
		return breakables;
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
	
	public Room getLastRoom() {
		for(Room r:connectedRooms) {
			if(r.isOpened()) {
				return r.getLastRoom();
			}
		}
		return this;
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
