package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.spawner.EnemySpawner;
import entity.mobs.player.Player;
import entity.statics.Factory;
import graphics.Camera;
import states.GameState;
import states.console.ConsoleState;

//@author Matthew (Did most of the logic and everything in this class)
//@author Kevin (did the remove entity and getKilled, along with player getKilled )
public class EntityManager {
	/*
	 * this class lets us hold all the entities like the player, enemies, and bullets
	 * in one place so that we know they are all being updated and rendered
	 */
	// an arrayList that holds all the entities in the game
  
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	protected ArrayList<Entity> solids = new ArrayList<Entity>();
	private int kills=0;
	Player player;// creating a player
	Factory factory;//the robot factory at the end of the level
	EnemySpawner spawner=new EnemySpawner(false);//the thing that controls when/where enemies spawn

	public void reset(boolean deletePlayer) {

  	// this initializes/resets the entity manager and is seperate from the constructor so
		// the player can add the core to the array if it was added in the constructor the
		// entityManager wouldn't be created yet and would throw an error so the init method is called
		// after it is created.
				
		if(deletePlayer) player=null;
		entities.clear();//removing any entities that still exist
		solids.clear();//clearing the solids
		if(player==null) {
			//if the player doesnt exist yet then it will make a new player
			player=new Player(0,0);
			kills=0;
		}
		
		//player.reset(200,200);
		player.createCore();//making the core
		
		entities.add(player);// adding the player and factory to the arraylist so it will be updated and rendered
		//entities.add(factory);
		
		spawner=new EnemySpawner(GameState.canHaveEnemies());
	}

	// this method updates all the entitys in the entities arrayList and removes the dead ones
	public void update() {
		spawner.update();
		
		
		for (int i = 0; i < entities.size(); i++) { // Loop through arraylist to update
			// everything needs to loop like this so that entities can be added in update
			// methods
			entities.get(i).update();
		}
		//this needs to be done after everything stops moving so that it will be 
		//consistent no matter which entity is updated 1st
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).damage();//dealing damage to everything that is overlapping
		}
		//removing all the corpses from the game this is done after damage is dealt so if things touch
		//each other on the same frame they will both take damage
		for (int i = entities.size()-1; i >=0 ; i--) {
			//looping backwards so things arent skipped when other entities are deleted 
			if(entities.get(i).isKilled()){
				if(entities.get(i) instanceof Enemy){ //killing the player
					kills++;
				}
				if(!(entities.get(i) instanceof Player)){ //killing the player
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
			}
		}
		for (int i = solids.size()-1; i >=0 ; i--) {
			//looping backwards so things arent skipped when other entities are deleted 
			if(!solids.get(i).isSolid()){
				solids.remove(i); //If an other entity besides the player gets hit remove that entity
			}
		}
	}
	
	// same as update but rendering things instead
	public void render(Graphics g, Camera camera) {
		//sorting the entitys by their location on the y axis so that things at the back will be behind things close to the screen
		Collections.sort(entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				if(e1.getLayer()==e2.getLayer()) {
					return (e1.getY()+e1.getHeight())-(e2.getY()+e2.getHeight());
				}
				if(e1.getLayer()==RenderLayer.FRONT||e2.getLayer()==RenderLayer.BACK) {
					return 1;
				}
				return -1;
			}
		});
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, camera);// rendering all the entities
			if(ConsoleState.isShowHitBoxen()) {
				entities.get(i).drawHitBox(g, camera);
			}
		}
		spawner.render(g, camera);
	}

	//getters/setters
	public void addEntity(Entity e) { // allows us to add to the entity manager
		if(e!=null) {
			if(e.isSolid()) {
				solids.add(e);
			}
			entities.add(e);
		}
	}
	
	public void addSolid(Entity e) {
		if(e!=null) {
			solids.add(e);
		}
	}

	public ArrayList<Entity> getEntities() { //lets us check what entities exist and thier state
		return entities;
	}
	
	public ArrayList<Entity> getSolids() {
		return solids;
	}
	
	public Player getPlayer() {
		return player;
	}
	public EnemySpawner getSpawner() {
		return spawner;
	}
	public int getKills() {
		return kills;
	}
}
