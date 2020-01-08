package floors;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.statics.Core;
import graphics.Camera;

/*
 * by: Matthew Milum
 */

public class Floor {
	// declaring variables
	private Room[][] rooms;
	private int size;// how many rooms big the floor is

	// constants
	private final int TILESIZE = 16, ROOMSIZE = 20, SCREENWIDTH, SCREENHEIGHT;
	private final Room[] POSSIBLEROOMS = loadAllRooms("res/room", 7);
	private final BufferedImage[] PICS;// the tileset it uses to render itself
	

	public Floor(int size, int screenWidth, int screenHeight, BufferedImage[] pics) {
		// initializing variables
		
		this.size = size;
		PICS = pics;
		SCREENHEIGHT = screenHeight;
		SCREENWIDTH = screenWidth;
		rooms = new Room[size * 2][size];
		
		// there are no down rooms so that it wont loop on itself which means that the
		// tallest the floor will be it however many rooms it has
		rooms = generateFloor();// generating a random floor layout
	}
	// this method draws everything to the screen
	public void render(Graphics g, Camera camera) {
		
		
		for (int y = 0; y < size * ROOMSIZE; y++) {// looping though all the tiles
			for (int x = 0; x < size * ROOMSIZE * 2; x++) {
				//these will be whatever place the tile is being rendered at
				int drawX=x * TILESIZE-camera.getxOffset(), drawY=y * TILESIZE-camera.getyOffset();
				if(drawX>=-TILESIZE&&drawX<=SCREENWIDTH&&//checking if it would actually be renderd in the screen
						drawY>-TILESIZE&&drawY<=SCREENHEIGHT) {
					g.drawImage(PICS[getTile(x, y) - 1], drawX, drawY, null);
					// drawing the proper tile in the proper place
				}
			}
		}

	}

	// creates a random floor that is held in a 2d array of rooms
	private Room[][] generateFloor() {
		// declaring variables
		Room[][] floor = new Room[size * 2][size + 1];
		Room validRoom = POSSIBLEROOMS[0];
		Room checkRoom = POSSIBLEROOMS[0];
		int x = size, y = size - 1;// making the starting room the bottom middle room

		for (int i = 0; i < size; i++) {// looping until it has created a floor with the proper size
			floor[x][y] = validRoom;// adding the rooms to the floor in the right place
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
				checkRoom = POSSIBLEROOMS[ThreadLocalRandom.current().nextInt(0, POSSIBLEROOMS.length)];
				// setting check room to a random room
				System.out.println(
						"checkRoom entrance=" + checkRoom.getEntrance() + " previous room exit=" + validRoom.getExit());
				// IF THIS LINE IS BEING SPAMMED IN THE CONSOLE AND THE PROGRAM ISN'T RUNNING IT
				// IS MISSING A ROOM AND CANT FIND ONE THAT WILL LINE UP
			} while (checkRoom.getEntrance() != validRoom.getExit());
			// looping until a room i found that will line up with the previous room

			validRoom = checkRoom;// checkRoom is now confirmed to line up so it can be added to the list the next loop
		}
		return floor;// returning the array
	}

	// loads all the .txt files that hold the different possible rooms
	private Room[] loadAllRooms(String path, int amount) {
		ArrayList<Room> rooms = new ArrayList<Room>();// an araylist to hold all the rooms

		for (int i = 0; i < amount; i++) {// looping though however many rooms there are supposed to be
			rooms.add(new Room(path + (i + 1) + ".txt", ROOMSIZE));// adding the room to the arraylist
		}
		return rooms.toArray(new Room[0]);// returning the rooms
	}

	// this lets you get what tile is at a specific x y (in tiles) so you can tell
	// if it is a wall or whatever
	public int getTile(int x, int y) {
		// finding the x y of the room the tile is in
		int roomX = (int) Math.floor(x / ROOMSIZE), roomY = (int) Math.floor(y / ROOMSIZE);
		int result;

		try {// if there isnt actually a room at the location it will throw an error so this
				// catches it
			result = getRoom(roomX, roomY).getTile(x - roomX * ROOMSIZE, y - roomY * ROOMSIZE);
			// returning the proper tile from the proper room

		} catch (NullPointerException e) {// if the floor isnt there then it returns the background tile
			result = 29;// 29 is the tile id for the empty background tile

		}
		return result;// Returning the tile
	}

	// if you need a specific room it can return it
	public Room getRoom(int x, int y) {
		return rooms[x][y];
	}
}
