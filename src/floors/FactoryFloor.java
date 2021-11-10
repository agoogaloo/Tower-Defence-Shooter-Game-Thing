package floors;

import java.awt.image.BufferedImage;

import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.movers.spawnPattern.RectangleSpawner;
import graphics.particles.shapes.ShrinkOvalParticle;
import graphics.particles.shapes.colourers.Timed;

public class FactoryFloor extends Floor{
	private final int RIGHTSWITCH=6,LEFTSWITCH=7,UPSWITCH=13,DOWNSWITCH=14,UPTILE=18, RIGHTTILE=19,DOWNTILE=20, LEFTTILE=21;

	public FactoryFloor(String folder, int levelID, int size, int screenWidth, int screenHeight, BufferedImage[] pics) {
		super(folder, levelID, size, screenWidth, screenHeight, pics);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void endWave() {
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles[i].length;j++) {
				boolean switchedTile=false;
				switch(tiles[i][j]) {
				case RIGHTSWITCH:
					tiles[i][j]=LEFTSWITCH;
					spawns[i][j]=LEFTTILE;
					switchedTile=true;
					break;
				case LEFTSWITCH:
					tiles[i][j]=RIGHTSWITCH;
					spawns[i][j]=RIGHTTILE;
					switchedTile=true;
					break;
				case UPSWITCH:
					tiles[i][j]=DOWNSWITCH;
					spawns[i][j]=DOWNTILE;
					switchedTile=true;
					break;
				case DOWNSWITCH:
					tiles[i][j]=UPSWITCH;
					spawns[i][j]=UPTILE;
					switchedTile=true;
					break;					
				}
				if(switchedTile) {
					new InstantEffect(10, new Straight(new RectangleSpawner(i*TILESIZE,j*TILESIZE,TILESIZE),0.75), 
							new ShrinkOvalParticle(new Timed(15),5,0.25), false);
				}
			}
		}
	}

}
