package entity.mobs.player.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.player.Player;
import entity.statics.Core;
import states.GameState;

public class MiniMap {
	//the image that will be drawn onto the screen holding all the data
	final int scale=3, updateDelay=20;
	int curentDelay=0;
	BufferedImage map= new BufferedImage(GameState.getFloor().ROOMSIZE*GameState.getFloor().getSize()/scale*2,
			GameState.getFloor().ROOMSIZE*GameState.getFloor().getSize()/scale,BufferedImage.TYPE_4BYTE_ABGR);
	
	private ArrayList<Point> openedRooms = new ArrayList<Point>();
	private Point currentRoom=new Point(0,0);
	
	public void update(ArrayList<Entity> entities, int x, int y) {
		
		currentRoom.setLocation(x/(GameState.getFloor().ROOMSIZE*16), y/(GameState.getFloor().ROOMSIZE*16));
		if(!openedRooms.contains(currentRoom)) {
			openedRooms.add(new Point(currentRoom.x, currentRoom.y));
		}
		
		if(curentDelay>=updateDelay) {
			drawMap(entities);
			curentDelay=0;
		}
		curentDelay++;
	}
	
	private void drawMap(ArrayList<Entity> entities) {
		//clearing the map
		map= new BufferedImage(GameState.getFloor().ROOMSIZE*GameState.getFloor().getSize()/scale*2,
				GameState.getFloor().ROOMSIZE*GameState.getFloor().getSize()/scale,BufferedImage.TYPE_4BYTE_ABGR);
		
		//drawing the rooms
		updateMap();
		
		//drawing the enemies
		for (Entity e: entities) {
			if(e instanceof Enemy) {
				map.setRGB(e.getX()/(16*scale), e.getY()/(16*scale), Color.RED.getRGB());
			}else if(e instanceof Player) {
				map.setRGB(e.getX()/(16*scale), e.getY()/(16*scale), new Color(53,179,53).getRGB());
			}else if(e instanceof Core) {
				map.setRGB(e.getX()/(16*scale), e.getY()/(16*scale), new Color(57,51,204).getRGB());
			}
		}		
	}
	
	private void updateMap() {
		for(int x=0;x<map.getWidth();x++) {//looping throught the width of the minimap
			for(int y=0;y<map.getHeight();y++) {//looping throught the height of the map
				if(!GameState.getFloor().checkwall(x*scale, y*scale)) {
					map.setRGB(x, y,new Color(1,1,26).getRGB());//colouring the pixel if it is a part of the room
				}
				int roomX=(x*scale)/GameState.getFloor().ROOMSIZE;
				int roomY=(y*scale)/GameState.getFloor().ROOMSIZE;
				
				//if(openedRooms.contains(new Point(roomX,roomY))) {
				//	map.setRGB(x, y,new Color(1,1,26).getRGB());//colouring the pixel if it is a part of the room
			//	}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(map,333-map.getWidth(),195-map.getHeight(), null);
	}
	
	public void resetMap() {
		openedRooms.clear();
	}
}