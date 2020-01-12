package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.mobs.Enemy;
import entity.mobs.Player;
import graphics.Camera;

/**
 * by: Kevin Tea and Matthew Milum
 *
 */
public class EntityManager {
	/*
	 * this class lets us hold all the entities like the player, enemies, and bullets
	 * in one place so that we know they are all being updated and rendered
	 */
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	// an arrayList that holds all the entities in the game
	Player player;// creating a player

	public void init() {
		// this init method needs to be seperate from the constructor so that player can
		// add the core to the array if it was added in the constructor the
		// entityManager
		// wouldn't be created yet and would throw an error so the init method is called
		// after it is created.
		player = new Player(1747, 1440);
		entities.add(player);// adding the player to the arraylist so it will be updated and rendered
		entities.add(new Enemy(1750, 800, 'd'));
	}

	// this method updates all the entitys in the entities arrayList
	public void update() {
		for (int i = 0; i < entities.size(); i++) { // Loop through arrayList to update
			// everything needs to loop like this so that entities can be added in update
			// methods
			entities.get(i).update();
		}
	}

	// same as update but rendering things instead
	public void render(Graphics g, Camera camera) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, camera);// rendering all the entities
		}
	}

	public void addEntity(Entity e) { // allows us to add to the entity manager
		entities.add(e);
	}

	public ArrayList<Entity> getEntities() { // Accessor method for the entities
		return entities;
	}

	public Player getPlayer() {
		return player;
	}
}
