package entity;

import java.util.ArrayList;
import entity.mobs.Enemy;
import entity.mobs.Player;
import java.awt.Graphics;
import graphics.Camera;

//@author Matthew (Did most of the logic and everything in this class)
//@author Kevin (did the remove entity and getKilled, along with player getKilled )
public class EntityManager {
	/*
	 * this class lets us hold all the entities like the player, enemies, and bullets
	 * in one place so that we know they are all being updated and rendered
	 */
	// an arrayList that holds all the entities in the game
  
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	Player player;// creating a player

	public void init() {
		// this init method needs to be seperate from the constructor so that player can
		// add the core to the array if it was added in the constructor the
		// entityManager wouldn't be created yet and would throw an error so the init method is called
		// after it is created.

		player=new Player(1747, 1440);	
		entities.add(new Enemy(1750,1000,'d'));
		entities.add(new Enemy(1750,1200,'d'));
		entities.add(player);// adding the player to the arraylist so it will be updated and rendered
	}

	// this method updates all the entitys in the entities arrayList and removes the dead ones
	public void update() {

		System.out.println("");
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
		for (int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isKilled()){ 
				if(entities.get(i) instanceof Player){ //If player gets hit reset level
					System.out.println("Player has died");
				}else{
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
			}
		}
	}

	// same as update but rendering things instead
	public void render(Graphics g, Camera camera) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, camera);// rendering all the entities
		}
	}

	//getters/setters
	public void addEntity(Entity e) { // allows us to add to the entity manager
		entities.add(e);
	}

	public ArrayList<Entity> getEntities() { //lets us check what entities exist and thier state
		return entities;
	}

	public Player getPlayer() {
		return player;
	}
}
