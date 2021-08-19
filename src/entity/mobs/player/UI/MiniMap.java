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
	private final int scale=2, updateDelay=30;
	private int curentDelay=0;
	private BufferedImage map= new BufferedImage(GameState.getFloor().getWidth()/scale,
			GameState.getFloor().getHeight()/scale,BufferedImage.TYPE_4BYTE_ABGR);
	
	private ArrayList<Point> openedRooms = new ArrayList<Point>();
	private Point currentRoom=new Point(0,0);
	
	public void update(ArrayList<Entity> entities, int x, int y) {
		
		//currentRoom.setLocation(x/(GameState.getFloor().getRoomSize()*16), y/(GameState.getFloor().getRoomSize()*16));
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
		map= new BufferedImage(GameState.getFloor().getWidth()/scale,
				GameState.getFloor().getHeight()/scale,BufferedImage.TYPE_4BYTE_ABGR);
		
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
		for(int x=0;x<map.getWidth();x++) {//looping though the width of the minimap
			for(int y=0;y<map.getHeight();y++) {//looping through the height of the map
				int roomX=0;//(x*scale)/GameState.getFloor().getRoomSize();//what room the pixel is in
				int roomY=0;//(y*scale)/GameState.getFloor().getRoomSize();
				
				//drawing the a room pixel if it is not a wall in a room that has been visited
				if(openedRooms.contains(new Point(roomX,roomY))&&!GameState.getFloor().checkwall(x*scale, y*scale)) {
					map.setRGB(x, y,new Color(1,1,26).getRGB());//colouring the pixel if it is a part of the room
				}
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