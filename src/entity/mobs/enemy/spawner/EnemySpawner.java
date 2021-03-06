package entity.mobs.enemy.spawner;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.GreenEnemy;
import entity.mobs.enemy.HamburgerBot;
import entity.mobs.enemy.HeliBot;
import entity.mobs.enemy.RedEnemy;
import entity.mobs.enemy.TankBot;
import entity.mobs.enemy.YellowEnemy;
import floors.Room;
import graphics.Camera;
import states.GameState;

public class EnemySpawner {
	private final int WAVESPERROOM=3;//how long many waves it takes to clear a room 
	private int enemyDelay=0, heliDelay=0;
	private int difficulty=3, roomsWaves=1, totalWaves=0;//dificulty and how many waves have happened in this room
	
	private boolean buttonSpawns =true;
	
	private ArrayList<Enemy> enemiesToAdd=new ArrayList<Enemy>();
	private ArrayList<Point> visitedRooms=new ArrayList<Point>();//all rooms that have been unlocked/"beaten"
	private Point lastRoom=new Point(-1,-1);
	
	private HeliBot heliBot=new HeliBot(4000, 4000);
	protected Ellipse2D.Float heliSpawnRange=new Ellipse2D.Float(0,0,400,400); // the range that will start spawinging the heli
	
	private SpawnButton button=new SpawnButton();
	
	public EnemySpawner(boolean buttonSpawns) {
		this.buttonSpawns=buttonSpawns;
		if(!buttonSpawns) button.remove(); 
	}
	public void update() {
		
		//getting what room the player is currently standing in (used to determine where to spawn enemies)
		int roomX=(Entity.getEntityManager().getPlayer().getX()/GameState.getFloor().
				TILESIZE)/GameState.getFloor().getRoomSize();//the x of the room (in rooms not pixels)
		
		int roomY=(Entity.getEntityManager().getPlayer().getY()/GameState.getFloor().
				TILESIZE)/GameState.getFloor().getRoomSize();//the y of the room (in rooms not pixels)
		
		Point currentRoom=new Point(roomX, roomY);
		
		
		if(!visitedRooms.contains(currentRoom)){//checking if the player is in a new room
			lastRoom=currentRoom;
			visitedRooms.add(currentRoom);
			if(buttonSpawns)
				button.create();
		}
		//updating the button that starts the next wave
		button.update(lastRoom.x,lastRoom.y,GameState.getFloor().getRoom(lastRoom.x,lastRoom.y).getExit());
		
		if(waveComplete()&&button.isClicked()) {
			newWave(lastRoom.x,lastRoom.y,difficulty);
			button.remove();
			roomsWaves++;
			totalWaves++;
			difficulty++;
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
			heliDelay=0;//reseting heli time {			
			if(roomsWaves%(WAVESPERROOM+1)==0) {
				GameState.getFloor().getRoom(lastRoom.x,lastRoom.y).unlock();
				roomsWaves=1;
			}
			if(roomsWaves!=1&&buttonSpawns) {
				button.create();
			}
		}
		
		
		
		//doing heli stuff
		spawnHeli();
		
	}
	public void render(Graphics g, Camera camera) {
		button.render(g, camera);
	}
	
	public void newWave(int roomX,int roomY, int enemies) {
		System.out.println("\n\nSTARTING WAVE "+totalWaves+"\nroomWaves: "+roomsWaves+"\n");
		Room room=GameState.getFloor().getRoom(roomX, roomY);
		int spawnX=roomX*room.getWidth()*room.TILESIZE;
		int spawnY=roomY*room.getHeight()*room.TILESIZE;
		int direction=Enemy.DOWN;
		switch(room.getExit()) {
		case 'u':
			direction=Enemy.DOWN;
			spawnX+=(room.getWidth()*room.TILESIZE)/2;
			spawnY-=30;
			break;
		case 'd':
			direction=Enemy.UP;
			spawnX+=(room.getWidth()*room.TILESIZE)/2;
			spawnY+=(room.getHeight()*room.TILESIZE)+30;
			break;
		case 'l':
			direction=Enemy.RIGHT;
			spawnY+=(room.getHeight()*room.TILESIZE)/2;
			spawnX-=30;
			break;
		case 'r':
			direction=Enemy.LEFT;
			spawnX+=(room.getWidth()*room.TILESIZE)+30;
			spawnY+=(room.getHeight()*room.TILESIZE)/2;
			break;
		}
		for (int i=0;i<enemies;i++) {
			enemiesToAdd.add(randomEnemy(spawnX+8, spawnY+8,direction));
		}
	}
	
	public boolean waveComplete() {
		if(enemiesToAdd.size()>0) {
			return false;
		}
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
	
	
	private Enemy randomEnemy(int x, int y, int direction) {
		switch(ThreadLocalRandom.current().nextInt(0,5)) {
		case 0:
			return new RedEnemy(x, y, direction);
		case 1:
			return new TankBot(x, y, direction);
		case 2:
			return new GreenEnemy(x, y, direction);
		case 3:
			return new YellowEnemy(x, y, direction);
		case 4:
			return new HamburgerBot(x,y,direction);
		default:
			System.out.println("random enemy number out of range so a normal one was made");
			return new RedEnemy(x, y, direction);		
		}
	}
	
	public void setButtonLocation(int x, int y) {
		button.move(x, y);
	}
	public void setButtonSpawns(boolean buttonSpawns) {
		this.buttonSpawns=buttonSpawns;
	}
	
}
