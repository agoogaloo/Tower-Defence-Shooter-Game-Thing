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
		//tiles=roomsToInt(rooms);

	}

	private Room[][] generateFloor() {
		Random random=new Random();
		Room[][] floor = new Room[size*2][size];
		Room validRoom=POSSIBLEROOMS[1];
		Room checkRoom=POSSIBLEROOMS[0];
		int x=size, y=size-1;
		for(int i=0;i<size;i++){
			floor[x][y]=validRoom;
			do{
				checkRoom=POSSIBLEROOMS[1];
				System.out.println("chechRoom e="+checkRoom.getEntrance()+" previous room exit="+validRoom.getExit());
			}while(checkRoom.getEntrance()!=validRoom.getExit());
			
			validRoom=checkRoom;
		}
		return floor;
	}

	private int[][] roomsToInt(Room[][] roomList) {
		int[][] tilesList = new int[size * ROOMSIZE * 2][size * ROOMSIZE];
		System.out.println(roomList[size][size-1].getTile(3, 3));
		for (int ry = 0; ry < (size-1); ry++) {
			for (int rx = 0; rx < (size-1) * 2; rx++) {
				for (int ty = 0; ty < ROOMSIZE; ty++) {
					for (int tx = 0; tx < ROOMSIZE; tx++) {
						try {
							tilesList[(rx*ROOMSIZE)+tx][(ry*ROOMSIZE)+ty]=rooms[rx][ry].getTile(tx,ty);
							
						} catch (NullPointerException e) {
							tilesList[(rx*ROOMSIZE)+tx][(ry*ROOMSIZE)+ty]=1;
							
						}
						
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
				System.out.print(getTile(x,y));
			}
		}
			System.out.print("\n");
	}
	
	public int getTile(int x,int y){
		int roomX=(int)Math.floor(x/ROOMSIZE),roomY=(int)Math.floor(y/ROOMSIZE);
		return getRoom(roomX,roomY).getTile(roomX-x, roomY-y);

		
	}
	public Room getRoom(int x, int y){
		return rooms[x][y];
	}
}
