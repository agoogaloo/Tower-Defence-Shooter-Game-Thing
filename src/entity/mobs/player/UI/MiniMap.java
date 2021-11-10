package entity.mobs.player.UI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.player.Player;
import entity.statics.Core;
import graphics.ImageUtils;
import graphics.UI.PicElement;
import states.GameState;

public class MiniMap {
	//the image that will be drawn onto the screen holding all the data
	private final int scale=2, updateDelay=30;
	private int curentDelay=0;
	
	private BufferedImage map= new BufferedImage(GameState.getFloor().getWidth()/scale,
			GameState.getFloor().getHeight()/scale,BufferedImage.TYPE_4BYTE_ABGR);
	private PicElement mapUI=new PicElement(333-map.getWidth(),195-map.getHeight(),map);
	
	public void update(ArrayList<Entity> entities, int x, int y) {				
		if(curentDelay>=updateDelay) {
			drawMap(entities);
			curentDelay=0;
			mapUI.update(map);
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
		
		for (int i=0;i<0;i++) {
			if(entities.get(i) instanceof Enemy) {
				map.setRGB(entities.get(i).getX()/(16*scale), entities.get(i).getY()/(16*scale), Color.RED.getRGB());
			}else if(entities.get(i) instanceof Player) {
				map.setRGB(entities.get(i).getX()/(16*scale), entities.get(i).getY()/(16*scale), new Color(53,179,53).getRGB());
			}else if(entities.get(i) instanceof Core) {
				map.setRGB(entities.get(i).getX()/(16*scale), entities.get(i).getY()/(16*scale), new Color(57,51,204).getRGB());
			}
		}	
	
		
		map=ImageUtils.crop(map,0);
		map=ImageUtils.outline(map,Color.WHITE);
		
		mapUI.move(330-map.getWidth(),197-map.getHeight());
	}
	
	private void updateMap() {
		for(int x=0;x<map.getWidth();x++) {//looping though the width of the minimap
			for(int y=0;y<map.getHeight();y++) {//looping through the height of the map
				//drawing the a room pixel if it is not a wall in a room that has been visited
				if(!GameState.getFloor().checkWall(x*scale, y*scale)) {
					map.setRGB(x, y,new Color(1,1,26).getRGB());//colouring the pixel if it is a part of the room
				}
			}
		}
	}
}