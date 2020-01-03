package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.mobs.Player;
import graphics.Assets;
/**
 * @author Kevin Tea
 *
 */
public class EntityManager {
	Assets assets= new Assets();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public EntityManager() {
		entities.add(new Player(assets.getPlayer()));
	}
	
	public void update(){
		for (int i=0;i<entities.size();i++){ //Loop through arraylist to update 
			//everything needs to loop like this so that entities can be added in update methods
			entities.get(i).update();
//			if(i.getKilled()){ 
//				if(i.instanceOf(Player)){ //If player gets hit reset level
//					level.reset();
//				}else{
//				entities.remove(i); //If an other entity besides the player gets hit remove that entity
//				}
//			}
		}
	}
	public void render(Graphics g){
		for (Entity i: entities){
			i.render(g);
		}
	}
	public void addEntity(Entity e){ //allows us to add to the entity manager
		entities.add(e);
	}
	public ArrayList<Entity> getEntities(){ //Accessor method for the entities
		return entities;
	}
}
