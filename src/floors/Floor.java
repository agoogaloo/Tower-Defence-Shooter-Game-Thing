package floors;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	private Room[][] rooms;
	private int size;// how many rooms big the floor is
	private int endRoomX, endRoomY;

	// constants
	public final int TILESIZE = 16, SCREENWIDTH, SCREENHEIGHT;
	private final BufferedImage[] PICS;// the tileset it uses to render itself
	private final int[] WALLS = new int[] {36,37,38,39,40,43,44,45,46,47,50,51,52,64,65,66,
			69,70,71,72,73,76,77,78,79,80};
	private int roomSize=30;
	// it holds its own tileset so that it is easy if we want to have different
	// floor with different themes
	public Floor(String folder,int size, int screenWidth, int screenHeight, BufferedImage[] pics) {
		// initializing variables
		this.size = size;
		roomSize=30;
		PICS = pics;
		SCREENHEIGHT = screenHeight;//needs the screen width/height 
		SCREENWIDTH = screenWidth;//so it knows if tiles should be rendered or not
		rooms = new Room[size * 2][size];
		// there are no down rooms so that it wont loop on itself which means that the
		// tallest the floor will be it however many rooms it has
		Room[] startRooms= loadAllRooms("res/start rooms.json");
		rooms = generateFloor(startRooms[ThreadLocalRandom.current().nextInt(0, startRooms.length)],
				loadAllRooms("res/rooms.json"),loadAllRooms("res/end rooms.json"));// generating a random floor layout
		
		
	}
	
	/**
	 * this lets us make a floor out of one json file for things like the hub or tutorial area
	 * @param path
	 * @param screenWidth
	 * @param screenHeight
	 * @param pics
	 */
	public Floor(String path, int screenWidth, int screenHeight, BufferedImage[] pics) {
		// initializing variables
		this.size = 1;
		PICS = pics;
		roomSize=50;
		SCREENHEIGHT = screenHeight;//needs the screen width/height 
		SCREENWIDTH = screenWidth;//so it knows if tiles should be rendered or not
		rooms =new Room[][] {{loadRoom(path)}};
		System.out.println("spawns"+rooms[0][0].getSpawnData(25, 0));
		// there are no down rooms so that it wont loop on itself which means that the
		// tallest the floor will be it however many rooms it has
		
		
		
		
	}

	// this method draws everything to the screen
	public void render(Graphics g, Camera camera) {
		for(int y=-TILESIZE;y<200/TILESIZE+2;y++) {
			for(int x=-TILESIZE;x<333/TILESIZE+2;x++) {
				//System.out.println(x+camera.getxOffset()/TILESIZE+", "+ y+camera.getyOffset()/TILESIZE);
				g.drawImage(PICS[getTile(x+camera.getxOffset()/TILESIZE, y+camera.getyOffset()/TILESIZE) - 1], x*TILESIZE-camera.getxOffset()%TILESIZE,
						y*TILESIZE-camera.getyOffset()%TILESIZE, null);
			}
		}
		/*
		for (int y = 0; y < size * ROOMSIZE; y++) {// looping though all the tiles
			for (int x = 0; x < size * ROOMSIZE * 2; x++) {
				// these will be whatever place the tile is being rendered at
				int drawX = x * TILESIZE - camera.getxOffset(), drawY = y * TILESIZE - camera.getyOffset();
				if (drawX >= -TILESIZE && drawX <= SCREENWIDTH && // checking if it would actually be renderd in the
																	// screen
						drawY > -TILESIZE && drawY <= SCREENHEIGHT) {
					g.drawImage(PICS[getTile(x, y) - 1], drawX, drawY, null);
					// drawing the proper tile in the proper place
				}
			}
		}*/

	}

	private Room[][] generateFloor(Room startRoom, Room[]midRooms, Room[] endRooms) {
		// declaring variables
		Room[][] floor = new Room[size * 2][size + 1];
		Room validRoom = startRoom;// making the 1st room the start room, which only has an exit
		Room checkRoom = midRooms[0];
		int x = size, y = size - 1;// making the starting room the bottom middle room

		for (int i = 0; i < size; i++) {// looping until it has created a floor with the proper size
			//validRoom.unlock();//this can be uncommented to unlock all the rooms in a floor to test stuff easily
			floor[x][y] = new Room(validRoom);// adding the rooms to the floor in the right place
			endRoomX=x;
			endRoomY=y;
			switch (validRoom.getExit()) {
			// changing where the next room will be placed based on the previous rooms exit
			case 'u':
				y--;
				break;
			case 'r':
				x++;
				break;
			case 'l':
				x--;
				break;
			}

			do {
				if(i==size-2) {
					checkRoom = endRooms[ThreadLocalRandom.current().nextInt(0, endRooms.length)];
					System.out.println("making the last room");
				}else {
					checkRoom = midRooms[ThreadLocalRandom.current().nextInt(0, midRooms.length)];
				}
				// setting check room to a random room
				System.out.println(
						"checkRoom entrance=" + checkRoom.getEntrance() + " previous room exit=" + validRoom.getExit());
				// IF THIS LINE IS BEING SPAMMED IN THE CONSOLE AND THE PROGRAM ISN'T RUNNING IT
				// IS MISSING A ROOM AND CANT FIND ONE THAT WILL LINE UP
			} while (checkRoom.getEntrance() != validRoom.getExit());
			// looping until a room i found that will line up with the previous room

			validRoom = checkRoom;
			// checkRoom is now confirmed to line up so it can be added to the list the next loop
		}
		return floor;// returning the array
	}

	private Room[] loadAllRooms(String path) {
		ArrayList<Room> rooms = new ArrayList<Room>();// an arraylist to hold all the rooms
		try {
			JSONObject file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
			JSONArray layers=(JSONArray)(file.get("layers"));
			for(int i=0;i<layers.size();i++) {
				JSONObject data=(JSONObject)(layers.get(i));
				rooms.add(new Room(data));
			}
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
			e.printStackTrace();
			
		}
		
		return rooms.toArray(new Room[0]);// returning the rooms
	}
	
	private Room loadRoom(String path){
		Room room=null;// the room it will return
		try {
			JSONObject file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
			JSONArray layers=(JSONArray)(file.get("layers"));
			JSONObject data=(JSONObject)(layers.get(0));
			room=new Room(file);
			
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
			e.printStackTrace();
			
		}
		
		return room;// returning the rooms
	}

	// this lets you get what tile is at a specific x y (in tiles) so you can tell
	// if it is a wall or whatever
	public int getTile(int x, int y) {
		// finding the x y of the room the tile is in
		int roomX = (int) Math.floor(x / roomSize), roomY = (int) Math.floor(y / roomSize);
		int result;

		try {// if there isnt actually a room at the location it will throw an error so this
				// catches it
			result = getRoom(roomX, roomY).getTile(x - roomX * roomSize, y - roomY * roomSize);
			// returning the proper tile from the proper room

		} catch (NullPointerException e) {// if the floor isnt there then it returns the background tile
			result = 44;// 44 is the tile id for the empty background tile

		}
		return result;// Returning the tile
	}

	public boolean checkwall(int x, int y) {

		int tile = getTile(x, y);
		for (int i = 0; i < WALLS.length; i++) {
			if (WALLS[i] == tile) {
				return true;
			}
		}
		return false;
	}

	// if you need a specific room it can return it
	public Room getRoom(int x, int y) {
		try {
			return rooms[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

	}
	public int getSize() {
		return size;
	}
	
	public int getRoomSize() {
		return roomSize;
	}

	public int getEndRoomX() {
		return endRoomX;
	}

	public int getEndRoomY() {
		return endRoomY;
	}
	
}
