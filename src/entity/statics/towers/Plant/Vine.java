package entity.statics.towers.Plant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.statics.Statics;
import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class Vine extends Statics{

	private char startDir, endDir;
	private boolean grown=false, waveCounted;
	int wavesToGrow=2;
	private Animation anim;
	Vine nextVine;
	
	public Vine(int x, int y,char startDir, char endDir) {
		friendly=true;
		this.x=x;
		this.y=y;
		this.width=10;
		this.height=10;
		updateBounds();
		damage=1;
		this.startDir = startDir;
		this.endDir = endDir;
		health=10;
		waveCounted=!entityManager.getSpawner().waveComplete();
		if(endDir=='l'||endDir=='r') {
			anim=new Animation(new BufferedImage[] {Assets.vines[1]});
		}else {
			anim=new Animation(new BufferedImage[] {Assets.vines[0]});
		}
	}

	@Override
	public void update() {
		
		if(entityManager.getSpawner().waveComplete()&&!grown) {
			if(!waveCounted) {
				waveCounted=true;
				wavesToGrow--;
			}
			if(wavesToGrow==0) {
				grown=true;
				switch (endDir) {
				case 'u':
					nextVine =new Vine(x,y-height,startDir,endDir);
					break;
				case 'r':
					nextVine = new Vine(x+width,y,startDir,endDir);
					break;
				case 'd':
					nextVine = new Vine(x,y+height,startDir,endDir);
					break;
				case 'l':
					nextVine = new Vine(x-width,y,startDir,endDir);
					break;
				}
				entityManager.addEntity(nextVine);
			}
		}else {
			waveCounted=false;
		}
		
		if(health<=0) {
			if(nextVine!=null) {
				health+=nextVine.health/1.5;
				nextVine.health=0;
			}else {
				killed=true;
			}
			
			
		}
	}
	

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(anim.getCurrentFrame(),x - camera.getxOffset(), y - camera.getyOffset(), null);
		
	}
	@Override
	public int getDamage() {
		health--;
		return super.getDamage();
	}
	

}
