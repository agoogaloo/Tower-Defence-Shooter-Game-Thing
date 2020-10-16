package entity.mobs.enemy;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import floors.Room;
import states.GameState;

public class EnemySpawner {
	private final int WAVEDELAY=300,WAVESPERROOM=3;//how long it waits to start the wave
	private int waveDelay=-100, enemyDelay=0, heliDelay=0;
	private int difficulty=3, roomsWaves=0, totalWaves=0;;//dificulty and how many waves have happened in this room
	
	private ArrayList<Enemy> enemiesToAdd=new ArrayList<Enemy>();
	private ArrayList<Point> openedRooms=new ArrayList<Point>();//all rooms that have been unlocked/"beaten"
	private HeliBot heliBot=new HeliBot(4000, 4000);
	protected Ellipse2D.Float heliSpawnRange=new Ellipse2D.Float(0,0,450,450); // the range that will start spawinging the heli
	
	public void update() {
		int roomX,roomY;//the room the player is in
		Point currentRoom;//a point representing the room
		
		if(waveComplete()&&waveDelay>=WAVEDELAY) {
			
			waveDelay=0;
			roomX=(Entity.getEntityManager().getPlayer().getX()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			roomY=(Entity.getEntityManager().getPlayer().getY()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			currentRoom=new Point(
					roomX, roomY);
			if(!openedRooms.contains(currentRoom)){//checking if the player is in a new room
				newWave(roomX,roomY,difficulty);
				roomsWaves++;
				totalWaves++;
				difficulty++;
				if(roomsWaves%WAVESPERROOM==0) {
					openedRooms.add(currentRoom);
					GameState.getFloor().getRoom(roomX,roomY).unlock();
					roomsWaves=0;
				}
			}
			
		}
		if(enemyDelay>120-difficulty*4) {
			enemyDelay=0;
			Entity.getEntityManager().addEntity(enemiesToAdd.get(0));
			enemiesToAdd.remove(0);
		}
		
		if(enemiesToAdd.size()>0) {
			enemyDelay++;//adding to the enemy delay timer if there are still enemies to add
		}
		
		if(waveComplete()){//doing things when there is no wave
		waveDelay++;//adding to the delay between waves timer
		heliDelay=0;//reseting heli timer 
		}
		//doing heli code
		spawnHeli();
	}
	public void newWave(int roomX,int roomY, int enemies) {
		System.out.println("\n\nSTARTING WAVE "+totalWaves+"\nroomWaves: "+roomsWaves+"\n");
		Room room=GameState.getFloor().getRoom(roomX, roomY);
		int spawnX=roomX*room.ROOMSIZE*room.TILESIZE;
		int spawnY=roomY*room.ROOMSIZE*room.TILESIZE;
		char direction='d';
		switch(room.getExit()) {
		case 'u':
			direction='d';
			spawnX+=(room.ROOMSIZE*room.TILESIZE)/2;
			spawnY-=30;
			break;
		case 'd':
			direction='u';
			spawnX+=(room.ROOMSIZE*room.TILESIZE)/2;
			spawnY+=(room.ROOMSIZE*room.TILESIZE)+30;
			break;
		case 'l':
			direction='r';
			spawnY+=(room.ROOMSIZE*room.TILESIZE)/2;
			spawnX-=30;
			break;
		case 'r':
			direction='l';
			spawnX+=(room.ROOMSIZE*room.TILESIZE)+30;
			spawnY+=(room.ROOMSIZE*room.TILESIZE)/2;
			break;
		}
		for (int i=0;i<enemies;i++) {
			enemiesToAdd.add(randomEnemy(spawnX, spawnY,direction));
		}
	}
	
	public static boolean waveComplete() {
		for(Entity e:Entity.getEntityManager().getEntities()) {
			if(e instanceof Enemy&&!(e instanceof HeliBot)) {
				return false;
			}
		}
		return true;
	}
	
	private void spawnHeli() {
		heliSpawnRange.x=Entity.getEntityManager().getPlayer().getX()-heliSpawnRange.width/2;
		heliSpawnRange.y=Entity.getEntityManager().getPlayer().getY()-heliSpawnRange.height/2;
		if(heliDelay<=600) {
			heliBot.destroy();
			if(heliDelay<=0) {
				heliDelay=0;
			}
		}
		for(Entity e:Entity.getEntityManager().getEntities()) {
			if(heliSpawnRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
					e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy&&!(e instanceof HeliBot)) {
				heliDelay-=4;
				return;
			}
		}
		heliDelay+=1;
		if(heliBot.isKilled()&&heliDelay>=600) {
			heliBot=new HeliBot((int)heliSpawnRange.x, (int)heliSpawnRange.y);
			Entity.getEntityManager().addEntity(heliBot);
		}
	}
	
	private Enemy randomEnemy(int x, int y, char direction) {
		switch(ThreadLocalRandom.current().nextInt(0,5)) {
		case 0:
			return new RedEnemy(x, y, direction);
		case 1:
			return new BlueEnemy(x, y, direction);
		case 2:
			return new GreenEnemy(x, y, direction);
		case 3:
			return new YellowEnemy(x, y, direction);
		case 4:
			return new HamburgerBot(x-10,y,direction);
		default:
			System.out.println("random enemy number out of range so a normal one was made");
			return new RedEnemy(x, y, direction);		
		}
	}
	
}
