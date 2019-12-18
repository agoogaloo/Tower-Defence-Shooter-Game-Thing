package floors;

import java.util.ArrayList;

public class Floor {

	private Room[][] rooms;
	private int[][] tiles;
	private int size;
	private final int TILESIZE = 16, ROOMSIZE = 10;//should actually be 20 but i was just testing stuff
	private final Room[] POSSIBLEROOMS = loadAllRooms("res/room", 2);
	

	public Floor(int size) {
		this.size = size;
		rooms = new Room[size * 2][size];
		

	}
	private int[][] rommsToInt(Room[][] roomList){
		int[][] tilesList=new int[size*ROOMSIZE*2][size*ROOMSIZE];
		for(int ry=0;ry<size;ry++){
			for(int rx=0;rx<size*2;rx++){
				for(int ty=0;ty<ROOMSIZE;ty++){
					for(int tx=0;tx<ROOMSIZE;tx++){					
						tilesList[(rx*ROOMSIZE)+tx][(ry*ROOMSIZE)+ty]=rooms[rx][ry].getTile(tx,ty);
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
		System.out.println("room 1" + POSSIBLEROOMS[0].getEntrance());
		System.out.println("room 2" + POSSIBLEROOMS[1].getEntrance());

	}

}
