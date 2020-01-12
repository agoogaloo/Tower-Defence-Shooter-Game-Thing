package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.mobs.Bullet;
import entity.mobs.Enemy;
import entity.mobs.Player;
import graphics.Assets;
import graphics.Camera;

/**
 * by: Kevin Tea and Matthew Milum
 *
 */
public class EntityManager {
	
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	Player player;// creating a player
	Enemy enemy;
	public void init() {

		player=new Player(1747,1520,Assets.player[0].getWidth(),Assets.player[0].getHeight());	
		entities.add(player);// adding the player to the arraylist so it will be updated and rendered
		entities.add(new Enemy(1750,800,'d',Assets.enemy[0].getWidth(),Assets.enemy[0].getHeight()));
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) { // Loop through arraylist to update
			// everything needs to loop like this so that entities can be added in update
			// methods
			entities.get(i).update();
			if(entities.get(i).getKilled()){ 
				if(entities.get(i) instanceof Player){ //If player gets hit reset level
					System.out.println("Player has died");
				}else{
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
			}
		}
	}

	public void render(Graphics g, Camera camera){
		for (int i=0;i<entities.size();i++){
			entities.get(i).render(g, camera);//rendering all the entities
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
	
	public Enemy getEnemy() {
		return enemy;
	}

}
