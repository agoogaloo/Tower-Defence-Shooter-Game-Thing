package entity.mobs.enemy;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import floors.Room;
import states.GameState;

public class EnemySpawner {
	private final int WAVEDELAY=300;
	private int waveDelay=0;
	private int enemyDelay=0;
	private int difficulty=3;
	private ArrayList<Enemy> enemiesToAdd=new ArrayList<Enemy>();
	private ArrayList<Integer> openedRoomsX=new ArrayList<Integer>();
	private ArrayList<Integer> openedRoomsY=new ArrayList<Integer>();
	public void update() {
		if(waveComplete()&&waveDelay>=WAVEDELAY) {
			waveDelay=0;
			int roomX=(Entity.getEntityManager().getPlayer().getX()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			int roomY=(Entity.getEntityManager().getPlayer().getY()/GameState.getFloor().
					TILESIZE)/GameState.getFloor().ROOMSIZE;
			
			if(!openedRoomsX.contains(roomX)||!openedRoomsY.contains(roomY)) {//checking if the player is in a new room
				newWave(roomX,roomY,difficulty);
				difficulty++;
				openedRoomsX.add(roomX);
				openedRoomsY.add(roomY);
			}
			
		}
		if(enemyDelay>120-difficulty*4) {
			enemyDelay=0;
			Entity.getEntityManager().addEntity(enemiesToAdd.get(0));
			enemiesToAdd.remove(0);
		}
		if(enemiesToAdd.size()>0) {
			enemyDelay++;
		}
		if(waveComplete()){
		waveDelay++;
		}
	}
	public void newWave(int roomX,int roomY, int enemies) {
		System.out.println("\n\nSTARTING WAVE "+String.valueOf(difficulty-2)+"\n\n");
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
			if(e instanceof Enemy) {
				return false;
			}
		}
		return true;
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
