package entity.mobs.enemy.spawner;

import java.awt.Graphics;
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
	
	private int spawnX=0, spawnY=0;
	
	private boolean buttonSpawns =true, waveJustFinished=false;
	
	private ArrayList<Enemy> enemiesToAdd=new ArrayList<Enemy>();
	
	private HeliBot heliBot=new HeliBot(0, 0);
	protected Ellipse2D.Float heliSpawnRange=new Ellipse2D.Float(0,0,400,400); // the range that will start spawning the heli
	private boolean useHeli=true;
	
	private SpawnButton button=new SpawnButton();
	
	public EnemySpawner(boolean buttonSpawns) {
		this.buttonSpawns=buttonSpawns;
		if(!buttonSpawns) button.remove(); 
	}
	public void update() {
		//updating the button that starts the next wave
	
		button.update(spawnX,spawnY);
		
		if(waveComplete()&&button.isClicked()) {
			setSpawnLoc();
			newWave(difficulty);
			button.remove();
			waveJustFinished=false;
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
			heliDelay=0;//reseting heli time 	
			setSpawnLoc();
			if(buttonSpawns) {
				button.create();//reshowing the spawn button
			}
			if(roomsWaves%(WAVESPERROOM+1)==0) {
				GameState.getFloor().unlockNextRoom();
				roomsWaves=1;
			}
			if(!waveJustFinished) {
				waveJustFinished=true;
				GameState.getFloor().endWave();
			}
			
		}
		
		
		if(useHeli) {
			//doing heli stuff
			spawnHeli();
		}
		
	}
	public void render(Graphics g, Camera camera) {
		button.render(g, camera);
	}
	private void setSpawnLoc() {
		Room room=GameState.getFloor().getLastRoom();
		spawnX=room.getX()*room.TILESIZE;
		spawnY=room.getY()*room.TILESIZE;
		
		
		switch(room.getExit()) {
		case 'u':
			spawnX+=room.getExitLoc()*room.TILESIZE;
			break;
		case 'd':
			spawnX+=(room.getExitLoc()*room.TILESIZE);
			spawnY+=(room.getHeight()*room.TILESIZE);
			break;
		case 'l':
			spawnY+=room.getExitLoc()*room.TILESIZE;
			break;
		case 'r':
			spawnX+=(room.getWidth()*room.TILESIZE);
			spawnY+=room.getExitLoc()*room.TILESIZE;
			break;
		}
	}
	
	public void newWave(int enemies) {
		System.out.println("\n\nSTARTING WAVE "+totalWaves+"\nroomWaves: "+roomsWaves+"\n");
		
		Room room=GameState.getFloor().getLastRoom();
		int spawnX=this.spawnX;
		int spawnY=this.spawnY;
		int direction=Enemy.DOWN;
		
		switch(room.getExit()) {
		case 'u':
			direction=Enemy.DOWN;
			spawnY-=30;
			break;
		case 'd':
			direction=Enemy.UP;
			spawnY+=30;
			break;
		case 'l':
			direction=Enemy.RIGHT;
			spawnX-=30;
			break;
		case 'r':
			direction=Enemy.LEFT;
			spawnX+=+30;
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
		ArrayList<Entity> entities = Entity.getEntityManager().getEntities();
		for (int i = 0; i < entities.size(); i++) {
			if(entities.get(i) instanceof Enemy&&!(entities.get(i) instanceof HeliBot)) {
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
		for(int i=0;i<Entity.getEntityManager().getEntities().size();i++) {
			if(heliSpawnRange.intersects(Entity.getEntityManager().getEntities().get(i).getBounds().getX(), 
					Entity.getEntityManager().getEntities().get(i).getBounds().getY(),
					Entity.getEntityManager().getEntities().get(i).getBounds().getWidth(), 
					Entity.getEntityManager().getEntities().get(i).getBounds().getHeight())&&
					Entity.getEntityManager().getEntities().get(i) instanceof Enemy&&!(Entity.getEntityManager().getEntities().get(i) instanceof HeliBot)) {
				heliDelay-=4;
				return;
			}
		}
		heliDelay+=1;
		if(heliBot.isKilled()&&heliDelay>=600) {
			if(heliSpawnRange.x>0&&heliSpawnRange.y>0) {
				heliBot=new HeliBot((int)heliSpawnRange.x, (int)heliSpawnRange.y);
			} else{
				heliBot=new HeliBot((int)(heliSpawnRange.x+heliSpawnRange.width), (int)(heliSpawnRange.y+heliSpawnRange.width));
			}
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
	public void setUseHeli(boolean useHeli) {
		this.useHeli = useHeli;
	}
	
}
