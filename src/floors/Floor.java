package floors;

import java.util.ArrayList;
import java.util.Random;

public class Floor {

	private Room[][] rooms;
	private int[][] tiles;
	private int size;
	private final int TILESIZE = 16, ROOMSIZE = 10;// should actually be 20 but
													// i was just testing stuff
	private final Room[] POSSIBLEROOMS = loadAllRooms("res/room", 7);

	public Floor(int size) {
		this.size = size;
		rooms = new Room[size * 2][size];
		rooms=generateFloor();
		//tiles=roomsToInt(rooms);

	}

	private Room[][] generateFloor() {
		Random random=new Random();
		Room[][] floor = new Room[size*2][size];
		Room validRoom=POSSIBLEROOMS[0];
		Room checkRoom=POSSIBLEROOMS[0];
		int x=size, y=size-1;
		for(int i=0;i<size;i++){
			floor[x][y]=validRoom;
			do{
				checkRoom=POSSIBLEROOMS[0];
				System.out.println("chechRoom e="+checkRoom.getEntrance()+" previous room exit="+validRoom.getExit());
			}while(checkRoom.getEntrance()!=validRoom.getExit());
			
			validRoom=checkRoom;
		}
		return floor;
	}

	private int[][] roomsToInt(Room[][] roomList) {
		int[][] tilesList = new int[size * ROOMSIZE * 2][size * ROOMSIZE];
		for (int ry = 0; ry < 2; ry++) {
			for (int rx = 0; rx < 2 * 2; rx++) {
				for (int ty = 0; ty < ROOMSIZE; ty++) {
					for (int tx = 0; tx < ROOMSIZE; tx++) {
						tilesList[(rx * ROOMSIZE) + tx][(ry * ROOMSIZE) + ty] = rooms[rx][ry].getTile(tx, ty);
						System.out.println(rooms[rx][ry].getTile(tx, ty));
					}
				}
			}
		}
		return tilesList;

	}

	private Room[] loadAllRooms(String path, int amount) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i < amount; i++) {
			rooms.add(new Room(path + (i + 1) + ".txt", ROOMSIZE));
		}
		return rooms.toArray(new Room[0]);
	}

	public void test() {
		for(int y=0;y<size*ROOMSIZE;y++) {
			for(int x=0;x<size*2*ROOMSIZE;x++) {
				System.out.print(tiles[x][y]);
			}
			System.out.print("\n");
		}

	}

}
