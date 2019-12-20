package floors;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/*
 * by: Matthew Milum
 */

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
	}

	private Room[][] generateFloor() {
		Room[][] floor = new Room[size*2][size+1];
		Room validRoom=POSSIBLEROOMS[2];
		Room checkRoom=POSSIBLEROOMS[0];
		int x=size, y=size-1;
		for(int i=0;i<size;i++){
			floor[x][y]=validRoom;
			switch (validRoom.getExit()){
			case 'u': 
				y--;
				break;
			case'r': 
				x++;
				break;
			case 'l':
				x--;
				break;
			}
			do{
				checkRoom=POSSIBLEROOMS[ThreadLocalRandom.current().nextInt(0, POSSIBLEROOMS.length)];
				System.out.println("checkRoom entrance="+checkRoom.getEntrance()+" previous room exit="+validRoom.getExit());
			}while(checkRoom.getEntrance()!=validRoom.getExit());
			
			validRoom=checkRoom;
		}
		return floor;
	}

	private Room[] loadAllRooms(String path, int amount) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i < amount; i++) {
			rooms.add(new Room(path + (i + 1) + ".txt", ROOMSIZE));
		}
		return rooms.toArray(new Room[0]);
	}

	public void test() {
		System.out.println(getTile(0,0));
		for(int y=0;y<size*ROOMSIZE;y++) {
			for(int x=0;x<size*ROOMSIZE*2;x++) {
				if(x%ROOMSIZE==0) {
					System.out.print(" ");
				}
				System.out.print(getTile(x,y)+",");
			}
			System.out.print("\n");
		}
			
	}
	
	public int getTile(int x,int y){
		int roomX=(int)Math.floor(x/ROOMSIZE),roomY=(int)Math.floor(y/ROOMSIZE);
		
		int result;
		try {
			result=getRoom(roomX,roomY).getTile(x-roomX*ROOMSIZE, y-roomY*ROOMSIZE);
			
		} catch (NullPointerException e) {
			result=0;
			
		}
		
		return result;
		
	}
	public Room getRoom(int x, int y){
		return rooms[x][y];
	}
}
