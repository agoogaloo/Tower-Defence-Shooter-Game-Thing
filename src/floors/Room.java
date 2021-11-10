package floors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.mobs.pickups.GunTowerItem;
import entity.mobs.pickups.ItemList;
import entity.mobs.shops.ItemStand;
import entity.mobs.shops.ShopKeep;
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
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Room> connectedRooms = new  ArrayList<Room>();

	
	// the  constructor takes the location and width of the file that should be
	// loaded all and rooms are squares so the width will be the same as the height
	public Room(RoomTemplate template,int levelID, int maxTowers, int x, int y) {
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
		
		for(Point i:template.getTowerLocs()) {
			TowerSpawn tower=new TowerSpawn(TILESIZE*(x+i.x),TILESIZE*(y+i.y),false);
			entities.add(tower);
		}
		while(entities.size()>maxTowers) {
			entities.remove(ThreadLocalRandom.current().nextInt(0, entities.size()));
		}
		
		for(DoorTemplate i:template.getDoors()) {
			Door door= Door.getProperDoor(TILESIZE*(x+i.getX()),TILESIZE*(y+i.getY()),levelID, i.getDirection());
			doors.add(door);
			entities.add(door);
		}
		
		for(Point i:template.getBreakables()) {
			if(ThreadLocalRandom.current().nextBoolean()) {
				Breakable breakable=Breakable.getProperBreakable(levelID,TILESIZE*(x+i.x),TILESIZE*(y+i.y));
				entities.add(breakable);
			}
		}
		ShopKeep shopKeep=null;
		for(Point i:template.getShopKeep()) {
				shopKeep = new ShopKeep(TILESIZE*(x+i.x)+8,TILESIZE*(y+i.y)+8);
				entities.add(shopKeep);
			
		}
		for(Point i:template.getSaleItem()) {
			if(shopKeep!=null) {
				shopKeep.makeItemStand(TILESIZE*(x+i.x)+8,TILESIZE*(y+i.y)+8);
			}else {
				ItemStand item = new ItemStand(new GunTowerItem(0,0,ThreadLocalRandom.current().nextInt(
						ItemList.TOWERS_LEN+ItemList.GUNS_LEN)),15, TILESIZE*(x+i.x)+8,TILESIZE*(y+i.y)+8);
				entities.add(item);
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
			for(Entity i:entities) {
				Entity.getEntityManager().addEntity(i);
				i.init();
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
