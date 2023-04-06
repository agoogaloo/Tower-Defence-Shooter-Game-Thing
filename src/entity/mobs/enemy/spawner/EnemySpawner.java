package entity.mobs.enemy.spawner;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.HeliBot;
import floors.Room;
import graphics.Camera;
import states.GameState;

public class EnemySpawner {
	private final int[] ENEMIES = new int[]{EnemyList.RED,EnemyList.GREEN,EnemyList.YELLOW,
		EnemyList.HAMBURGER, EnemyList.TANK, EnemyList.BABYBOT};
	private final int[] BULLETDIFF = new int[]{2,1,3,3,2,1};
	private final int[] TOWERDIFF = new int[]{2,3,1,2,3,1};
	
	private final int WAVESPERROOM=3;//how long many waves it takes to clear a room 
	private int enemyDelay=0, heliDelay=0;
	private int difficulty=4, roomsWaves=1, totalWaves=0;//dificulty and how many waves have happened in this room
	
	private int spawnX=0, spawnY=0;
	
	private boolean buttonSpawns =true, waveJustFinished=false;
	
	private ArrayList<Enemy> enemiesToAdd=new ArrayList<Enemy>();
	
	private HeliBot heliBot=new HeliBot(0, 0);
	// the range that will start spawning the heli
	protected Ellipse2D.Float heliSpawnRange=new Ellipse2D.Float(0,0,400,400); 
	private boolean useHeli=true;
	
	private SpawnButton button=new SpawnButton();
	
	public EnemySpawner(boolean buttonSpawns, int difficulty) {
		this.buttonSpawns=buttonSpawns;
		this.difficulty = difficulty;
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
		
		if(enemyDelay>145-difficulty*5) {
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
	
	public void newWave(int difficulty) {
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
		boolean focus = ThreadLocalRandom.current().nextBoolean();
		System.out.println("making wave with focus:"+focus);
		ArrayList<Integer> enemies = CreateEnemies(difficulty,focus);
		for (int i:enemies) {
			enemiesToAdd.add(EnemyList.newEnemy(i,spawnX+8, spawnY+8,direction));
		}
	}
	private ArrayList<Integer> CreateEnemies(int difficulty,boolean towerFocus){
		
		ArrayList<Integer> enemyIDs = new ArrayList<Integer>();
		int diffLenience = difficulty/2;
		boolean balanced = false;

		int[] focusDiffArr;
		int[] secondDiffArr;
		int focusDiff = 0 , secondDiff = 0;
		if (towerFocus){
			focusDiffArr = TOWERDIFF;
			secondDiffArr = BULLETDIFF;
		}else{
			focusDiffArr = BULLETDIFF;
			secondDiffArr = TOWERDIFF;
		}
		//adjusting the enemies until its balanced enough
		while (!balanced){
			//adding enemies until target dificulty is reached
			if(focusDiff<difficulty){
				int id = ThreadLocalRandom.current().nextInt(0,ENEMIES.length);
				enemyIDs.add(id);
				focusDiff+=focusDiffArr[id];
				secondDiff+=secondDiffArr[id];
			}else if(focusDiff>difficulty+diffLenience || secondDiff<difficulty-diffLenience){
				//getting the enemies that are too hard
				ArrayList<Integer> probEnemies = new ArrayList<Integer>();
				for (int i=0 ; i<enemyIDs.size();i++){
					if (focusDiffArr[enemyIDs.get(i)]>secondDiffArr[enemyIDs.get(i)
					]){
						probEnemies.add(i);
					}
				}
				if(probEnemies.size()>0){
					int remEnemy = ThreadLocalRandom.current().nextInt(0,probEnemies.size());
					focusDiff-=focusDiffArr[enemyIDs.get(remEnemy)];
					secondDiff-=secondDiffArr[enemyIDs.get(remEnemy)];
					enemyIDs.remove(remEnemy);
				}

			}else if(secondDiff>difficulty){
				//getting the enemies that are too hard
				ArrayList<Integer> probEnemies = new ArrayList<Integer>();
				for (int i=0 ; i<enemyIDs.size();i++){
					if (secondDiffArr[enemyIDs.get(i)]>focusDiffArr[enemyIDs.get(i)]){
						probEnemies.add(i);
					}
				}
				if(probEnemies.size()>0){
					int remEnemy = ThreadLocalRandom.current().nextInt(0,probEnemies.size());
					focusDiff-=focusDiffArr[enemyIDs.get(remEnemy)];
					secondDiff-=secondDiffArr[enemyIDs.get(remEnemy)];
					enemyIDs.remove(remEnemy);
				}
			}else{
				balanced = true;
			}

		}
		System.out.println("wave of difficulty "+difficulty+" enemies:"+enemyIDs.toString()+
		" focusdiff:"+focusDiff+" second diff:"+secondDiff);
		return enemyIDs;
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
				heliBot=new HeliBot((int)(heliSpawnRange.x+heliSpawnRange.width), 
				(int)(heliSpawnRange.y+heliSpawnRange.width));
			}
			Entity.getEntityManager().addEntity(heliBot);
		}
	}
	
	
	private Enemy randomEnemy(int x, int y, int direction) {
		int id = ThreadLocalRandom.current().nextInt(0,ENEMIES.length);
		return EnemyList.newEnemy(id, x, y, direction);

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
