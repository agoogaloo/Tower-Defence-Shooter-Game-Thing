package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.mobs.Player;
import graphics.Assets;
import graphics.Camera;
/**
 * by: Kevin Tea and Matthew Milum
 *
 */
public class EntityManager {
	Assets assets= new Assets();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	Player player=new Player(assets.getPlayer());
	
	public EntityManager() {
		entities.add(player);
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
	public void render(Graphics g, Camera camera){
		for (Entity i: entities){
			i.render(g, camera);
		}
	}
	public void addEntity(Entity e){ //allows us to add to the entity manager
		entities.add(e);
	}
	public ArrayList<Entity> getEntities(){ //Accessor method for the entities
		return entities;
	}
	public Player getPlayer(){
		return player;
	}
}
