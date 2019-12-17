package entity;

import java.awt.Graphics;
import java.util.ArrayList;
/**
 * @author Kevin Tea
 *
 */
public class EntityManager {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void update(){
		for (Entity i:entities){ //Loop through arraylist checking for what got hit
			i.update();
//			if(i.getKilled()){ 
//				if(i.instanceOf(Player)){ //If player gets hit reset level
//					level.reset();
//				}else{
//				entities.remove(i); //If an other entity besides the player gets hit remove that entity
//				}
//			}
		}
	}
	public void render(){
		for (Entity i: entities){
			
			i.render(Graphics g);
		}
	}
	
}
