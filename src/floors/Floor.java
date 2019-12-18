package floors;

import java.util.ArrayList;

public class Floor {
	
	Room[][] rooms;
	int[][] tiles;
	int size;
	final int TILESIZE=16, ROOMSIZE=10;
	final Room[] POSSIBLEROOMS=loadAllRooms("res/room", 2);
	
	public Floor(int size) {
		this.size=size;
		rooms=new Room[size*2][size];
		
	}
	
	private Room[] loadAllRooms(String path, int amount) {
		ArrayList<Room> rooms=new ArrayList<Room>();
		for(int i=0;i<amount;i++) {
			rooms.add(new Room(path+(i+1)+".txt",ROOMSIZE));
		}
		
		
		return rooms.toArray(new Room[0]);
		
	}
	
	public void test() {
		System.out.println("room 1"+POSSIBLEROOMS[0].getEntrance());
		System.out.println("room 2"+POSSIBLEROOMS[1].getEntrance());

	}

}
