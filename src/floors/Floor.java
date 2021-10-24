package floors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entity.Entity;
import entity.statics.doors.Door;
import entity.statics.towers.TowerSpawn;
import graphics.Camera;

/*
 * by: Matthew Milum
 */

public class Floor {
	/*
	 * this class is the randomly generated floor made out of several rooms that
	 * have been created in the json files in the res folder
	 */
	
	// declaring variables
	private Room[] rooms;
	private Rectangle[] roomBounds;
	private int[][] tiles;
	private int[][] spawns;
	
	private int size;// how many rooms big the floor is
	private int endRoomX, endRoomY;
	private int width=300, height=300;
	
	private final int towersPerRoom=4; 
	private int levelID;

	// constants
	public final int TILESIZE = 16, SCREENWIDTH, SCREENHEIGHT;
	private final BufferedImage[] PICS;// the tileset it uses to render itself
	private final int[] WALLS = new int[] {36,37,38,39,40,43,44,45,46,47,50,51,52}, 
			PITS = new int[] {48,49,55,56,61,62,63,68,69,70,75,76,77};
	// it holds its own tileset so that it is easy if we want to have different
	// floor with different themes
	public Floor(String folder,int levelID, int size, int screenWidth, int screenHeight, BufferedImage[] pics) {
		// initializing variables
		this.levelID=levelID;
		this.size = size;
		PICS = pics;
		SCREENHEIGHT = screenHeight;//needs the screen width/height 
		SCREENWIDTH = screenWidth;//so it knows if tiles should be rendered or not
		rooms = new Room[size];
		// there are no down rooms so that it wont loop on itself which means that the
		// tallest the floor will be it however many rooms it has
		if(new File(folder).isDirectory()) {
			RoomTemplate[] startRooms = loadAllRooms(folder+"/start");
			RoomTemplate[] midRooms = loadAllRooms(folder+"/mid");
			RoomTemplate[] endRooms = loadAllRooms(folder+"/end");
			RoomTemplate[] shops = loadAllRooms(folder+"/shops");
			
			boolean success = false;
			while(!success) {
				success = generateFloor(startRooms[ThreadLocalRandom.current().nextInt(0, startRooms.length)],
						midRooms,endRooms,shops);// generating a random floor layout
			}
			
		}else {
			rooms =new Room[] {new Room(loadRoomTemplate(folder), levelID, 0,0)};
			width=rooms[0].getWidth();
			height=rooms[0].getHeight();
			roomBounds= new Rectangle[] {new Rectangle(0,0,width,height)};
			tiles=new int[width+1][height+1];
			spawns=new int[width+1][height+1];

			for(int x=0;x<=width;x++) {
				for(int y=0;y<=height;y++) {
					tiles[x][y]=rooms[0].getTile(x, y);
					spawns[x][y]=rooms[0].getSpawnData(x, y);
				}	
			}
		}
		
		for(Room r:rooms) {
			ArrayList<TowerSpawn> arr = r.getTowerLocs();
			while(arr.size()>towersPerRoom) {
				arr.remove(ThreadLocalRandom.current().nextInt(0, arr.size()));
			}
		}
		
		
		
		
		
		
		
	}
	
	/**
	 * this lets us make a floor out of one json file for things like the hub or tutorial area
	 * @param path
	 * @param screenWidth
	 * @param screenHeight
	 * @param pics
	 */
	public Floor(String path, int levelID, int screenWidth, int screenHeight, BufferedImage[] pics) {
		// initializing variables
		this.size = 1;
		PICS = pics;
		SCREENHEIGHT = screenHeight;//needs the screen width/height 
		SCREENWIDTH = screenWidth;//so it knows if tiles should be rendered or not
		rooms =new Room[] {new Room(loadRoomTemplate(path),levelID, 0,0) };
		width=rooms[0].getWidth();
		height=rooms[0].getHeight();
		roomBounds= new Rectangle[] {new Rectangle(0,0,width,height)};
		tiles=new int[width][height];
		spawns=new int[width][height];

		for(int x=0;x<=width;x++) {
			for(int y=0;y<=height;y++) {
				tiles[x][y]=rooms[0].getTile(x, y);
				spawns[x][y]=rooms[0].getSpawnData(x, y);
			}	
		}
	}
	public void init() {
		Room r = rooms[0];
		r.open();
		/*for(Door d:r.getDoors()) {
			Entity.getEntityManager().addEntity(d);
		}
		for(TowerSpawn t:r.getTowerLocs()) {
			Entity.getEntityManager().addEntity(t);
		}
		for(Breakable b:r.getBreakables()) {
			Entity.getEntityManager().addEntity(b);
			System.out.println("breakable");
		}*/
	}

	// this method draws everything to the screen
	public void render(Graphics g, Camera camera) {
		for(int y=-TILESIZE;y<200/TILESIZE+2;y++) {
			for(int x=-TILESIZE;x<333/TILESIZE+2;x++) {
				//System.out.println(x+camera.getxOffset()/TILESIZE+", "+ y+camera.getyOffset()/TILESIZE);
				g.drawImage(PICS[getTile(x+camera.getxOffset()/TILESIZE, y+camera.getyOffset()/TILESIZE) -1], x*TILESIZE-camera.getxOffset()%TILESIZE,
						y*TILESIZE-camera.getyOffset()%TILESIZE, null);
			}
		}
	}
	/**
	 * 
	 * @param startRoom
	 * @param midRooms
	 * @param endRooms
	 * @return - whether or not the level generation was successful. 
	 * 
	 */
	private boolean generateFloor(RoomTemplate startRoom, RoomTemplate[]midRooms, RoomTemplate[] endRooms, RoomTemplate[] shops) {
		//arrays to hold the rooms and their locations
		ArrayList<Room> rooms= new ArrayList<Room>();
		ArrayList<Rectangle> bounds= new ArrayList<Rectangle>();
		Rectangle floorBounds = new Rectangle(0,0,width,height);
		ArrayList<RoomTemplate> usedRooms = new ArrayList<RoomTemplate>();
		
		tiles=new int[width][height];
		spawns=new int[width][height];
		
		//making all the tiles start as walls
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				tiles[x][y]=44;
				
			}	
		}
		
		//the x and y where the rooms will be added
		int x=width/2-startRoom.getWidth()/2;
		int y=height/2-startRoom.getHeight()/2;
		
		
		Room validRoom=new Room(startRoom,levelID,x,y);
		
		
		for (int room = 1; room <=size; room++) {//adding rooms until it is the right size
			
			// -- adding the room if we know that it is actually able to go there --
			
			rooms.add(validRoom);
			bounds.add(new Rectangle(x,y,validRoom.getWidth(),validRoom.getHeight()));
			//looping through the rooms tiles and spawn data to add them all to the floors data
			for(int i=0;i<validRoom.getWidth();i++) {
				for(int j=0;j<validRoom.getHeight();j++) {
					tiles[x+i][y+j]=validRoom.getTile(i, j);
					spawns[x+i][y+j]=validRoom.getSpawnData(i, j);
				}
			}
			
			
			//changing where the next room will be placed based on the previous rooms exit direction, and size
			
			switch (validRoom.getExit()) {
			case 'u':
				
				x+=validRoom.getExitLoc();
				break;
			case 'r':
				x+=validRoom.getWidth();
				y+=validRoom.getExitLoc();
				break;
			case 'l':
				
				y+=validRoom.getExitLoc();
				break;
			case 'd':
				y+=validRoom.getHeight();
				x+=validRoom.getExitLoc();
				break;
				
			default:
				System.out.println(validRoom.getExit()+" isnt an exit");
					
			}
			
			// -- finding a room that can be added onto it --

			ArrayList<RoomTemplate> possibleRooms;
			if(room==size-1) {
				//setting the possible rooms to be the ending rooms if it is placing the last room
				System.out.println("making the end room");
				possibleRooms=new ArrayList<RoomTemplate>(Arrays.asList(endRooms));
			}else {
				possibleRooms=new ArrayList<RoomTemplate>(Arrays.asList(midRooms));
				
			}
			
			boolean roomFound=false;

			while(possibleRooms.size()>0&&!roomFound) {
				int checkIndex=ThreadLocalRandom.current().nextInt(0, possibleRooms.size());
				int checkX=x, checkY=y;
				RoomTemplate checkRoom = possibleRooms.get(checkIndex);
				switch (checkRoom.getEntrance()) {
				
				case 'u':
					checkX-=checkRoom.getEntranceLoc();
					checkY-=checkRoom.getHeight();
					break;
				case 'r':
					checkY-=checkRoom.getEntranceLoc();
					break;
				case 'l':
					checkY-=checkRoom.getEntranceLoc();
					checkX-=checkRoom.getWidth();
					break;
				case 'd':
					checkX-=checkRoom.getEntranceLoc();
					break;
				}
				//checking if the room can actually be placed
				Rectangle checkBounds=new Rectangle(checkX,checkY,checkRoom.getWidth(),checkRoom.getHeight());
				roomFound=isValidRoom(bounds,usedRooms,checkRoom, checkBounds, floorBounds, validRoom.getExit());
				
				
				//removing the room from the possible rooms if it is unable to be placed
				if(!roomFound) {
					possibleRooms.remove(checkIndex);
					
				}else if (room<size){
					x=checkX;
					y=checkY;
					usedRooms.add(checkRoom);
					
					Room newRoom = new Room(checkRoom,levelID,x,y);
					validRoom.addConnectedRoom(newRoom);
					validRoom=newRoom;
				}
			}
			if(possibleRooms.size()<=0) {
				return false;
			}
			
		}
		
		//generating the shop
		Room shop = generateShop(shops, rooms.get(0), bounds, floorBounds);
		System.out.println("adding the shop");
		if(shop==null) {
			return false;
		}
		rooms.get(0).addConnectedRoom(shop);
		rooms.add(shop);
		bounds.add(new Rectangle(shop.getX(),shop.getY(),shop.getWidth(),shop.getHeight()));
		System.out.println("coord:"+shop.getX()+", "+shop.getY()+" width: "+shop.getWidth()+" height: "+shop.getHeight());
			
		for(int i=0;i<shop.getWidth();i++) {
			for(int j=0;j<shop.getHeight();j++) {
				tiles[shop.getX()+i][shop.getY()+j]=shop.getTile(i, j);
				spawns[shop.getX()+i][shop.getY()+j]=shop.getSpawnData(i, j);
			}
		}
		
		
		
		
		
		//making the rooms and their bounds into regular arrays, and adding them to the level 
		this.rooms=new Room[rooms.size()];
		this.rooms=(Room[])rooms.toArray(this.rooms);
		roomBounds=new Rectangle[bounds.size()];
		roomBounds=(Rectangle[])bounds.toArray(roomBounds);		
		return true;
	}
	private Room generateShop(RoomTemplate[] shops, Room startRoom,ArrayList<Rectangle> bounds,Rectangle floorBounds) {
		ArrayList<RoomTemplate> possibleRooms=new ArrayList<RoomTemplate>(Arrays.asList(shops));
		boolean roomFound=false;
		
		int x=startRoom.getX();
		int y=startRoom.getY();
		
		switch (startRoom.getEntrance()) {
		
		case 'u':
			x-=startRoom.getEntranceLoc();
			y-=startRoom.getHeight();
			break;
		case 'r':
			y+=startRoom.getEntranceLoc();
			break;
		case 'l':
			y+=startRoom.getEntranceLoc();
			x+=startRoom.getWidth();
			break;
		case 'd':
			x+=startRoom.getEntranceLoc();
			break;
		}	
		while(possibleRooms.size()>0&&!roomFound) {
			int checkIndex=ThreadLocalRandom.current().nextInt(0, possibleRooms.size());
			int checkX=x, checkY=y;
			RoomTemplate checkRoom = possibleRooms.get(checkIndex);
			switch (checkRoom.getEntrance()) {
			
			case 'u':
				checkX-=checkRoom.getEntranceLoc();
				checkY-=checkRoom.getHeight();
				break;
			case 'r':
				checkY-=checkRoom.getEntranceLoc();
				checkX-=checkRoom.getWidth();
				break;
			case 'l':
				checkY-=checkRoom.getEntranceLoc();
				
				break;
			case 'd':
				checkX-=checkRoom.getEntranceLoc();
				checkY-=checkRoom.getHeight();
				break;
			}
			//checking if the room can actually be placed
			Rectangle checkBounds=new Rectangle(checkX,checkY,checkRoom.getWidth(),checkRoom.getHeight());
			roomFound=isValidRoom(bounds,new ArrayList<RoomTemplate>(),checkRoom, checkBounds, floorBounds, startRoom.getEntrance());
			
			
			//removing the room from the possible rooms if it is unable to be placed
			if(!roomFound) {
				possibleRooms.remove(checkIndex);
			}else {			
				Room newRoom = new Room(checkRoom,levelID,checkX,checkY);
				return newRoom;
			}
		}
		return null;	
	}
	
	private boolean isValidRoom(ArrayList<Rectangle> bounds,ArrayList<RoomTemplate> usedRooms, RoomTemplate checkRoom, Rectangle checkBounds, 
			Rectangle floorBounds, char validExit) {
		if(checkRoom.getEntrance()!=validExit) {
			return false;
		}
		for(Rectangle r:bounds) {
			if(checkBounds.intersects(r)) {
				return false;
			}
		}
		if(!floorBounds.contains(checkBounds)) {
			return false;
		}
		if(usedRooms.contains(checkRoom)) {
			return false;
		}
		return true;
	}

	private RoomTemplate[] loadAllRooms(String path) {		
		ArrayList<RoomTemplate> rooms = new ArrayList<RoomTemplate>();// an arraylist to hold all the rooms
		final File folder = new File(path);
		
		try {
			for(File file:folder.listFiles()) {
				try {
					JSONObject object=(JSONObject)(new JSONParser().parse(new FileReader(file.getPath())));
					rooms.add(new RoomTemplate(object));
					System.out.println("loading room at "+file.getPath());
				} catch (IOException |ParseException e) {
					System.out.println(file.getPath()+" could not be loaded. Make sure it is a .json file");
				}
			}
		} catch (NullPointerException e) {
			System.out.println(folder.getPath()+" is not a folder");
			e.printStackTrace();
		}
		
		return rooms.toArray(new RoomTemplate[0]);// returning the rooms
	}
	
	private RoomTemplate loadRoomTemplate(String path){
		RoomTemplate room=null;// the room it will return
		try {
			JSONObject file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
			room=new RoomTemplate(file);
			
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
			e.printStackTrace();
			
		}
		
		return room;// returning the rooms
	}
	

	// this lets you get what tile is at a specific x y (in tiles) so you can tell
	// if it is a wall or whatever
	public int getTile(int x, int y) {
		int result;
		boolean inVisibleRoom=false;
		for(int i=0;i<rooms.length;i++) {
			Rectangle r = roomBounds[i];
			if(rooms[i].isOpened()&&r.contains(x,y)) {
				inVisibleRoom=true;
				break;
			}
		}
		if(!inVisibleRoom) {
			return 44;
		}
		
		
		try {
			result= tiles[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {// if the floor isnt there then it returns the background tile
			return  44;// 44 is the tile id for the empty background tile

		}
		if(result>PICS.length){
			System.out.println("tile "+result+" is out of bounds");
			return 34;//a tile that is pretty obvious if it is in the worng spot
		}
		return result;
		
	}
	
	public int getSpawnData(int x, int y) {
		try {
			return spawns[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {// if the floor isnt there then it returns the background tile
			return 0;// 44 is the tile id for the empty background tile

		}
	}

	/**
	 * @param x - x coordinate in tiles
	 * @param y - y coordinate in tiles
	 * @return - true if the tile at x,y is a wall and false if it is not
	 */
	public boolean checkWall(int x, int y) {

		int tile = getTile(x, y);
		for (int i = 0; i < WALLS.length; i++) {
			if (WALLS[i] == tile) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPit(int x, int y) {
		int tile = getTile(x, y);
		for (int i = 0; i < PITS.length; i++) {
			if (PITS[i] == tile) {
				return true;
			}
		}
		return false;
	}

	// if you need a specific room it can return it
	public Room getRoom(int x, int y) {
		for(int i=0;i<rooms.length;i++) {
			if(roomBounds[i].contains(x, y)) {
				return rooms[i];
			}
		}
		return null;
		

	}
	public void unlockNextRoom() {
		rooms[0].open();
	}
	public Room getLastRoom() {
		return rooms[0].getLastRoom();
	}
	public Room[] getRooms() {
		return rooms;
	}
	public int getSize() {
		return size;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public int getEndRoomX() {
		return endRoomX;
	}

	public int getEndRoomY() {
		return endRoomY;
	}
	public ArrayList<Door> getDoors(){
		ArrayList<Door> list = new ArrayList<>();
		for(Room r:rooms) {
			for(Door d:r.getDoors()) {
				list.add(d);
			}
		}
		return list;
	}
	
	public ArrayList<TowerSpawn> getTowerLocs(){
		ArrayList<TowerSpawn> list = new ArrayList<>();
		for(Room r:rooms) {
			for(TowerSpawn t:r.getTowerLocs()) {
				list.add(t);
			}
		}
		return list;
	}
	
	public ArrayList<Entity> getFloorEntities() {
		ArrayList<Entity> list = new ArrayList<>();
		for(Room r:rooms) {
			for(Door d:r.getDoors()) {
				list.add(d);
			}
			for(TowerSpawn t:r.getTowerLocs()) {
				list.add(t);
			}
		}
		return list;
	}
}
