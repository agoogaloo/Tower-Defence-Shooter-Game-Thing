package entity.statics.towers.Plant;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import entity.Entity;
import entity.RenderLayer;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Vine extends Tower{

	private char startDir, endDir;
	private boolean grown=false;
	Vine nextVine;

	public Vine(int x, int y,char startDir, char endDir) {
		friendly=true;
		
		this.width=10;
		this.height=10;
		this.x=x-width/2;
		this.y=y-height/2;
		
		this.towerRange= new Ellipse2D.Float(this.x, this.y, width,height);
		updateBounds();
		damage=1;
		this.startDir = startDir;
		this.endDir = endDir;
		health=10;
		layer=RenderLayer.BACK;
		
		if((startDir=='u'&&endDir=='d')||(startDir=='d'&&endDir=='u')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[0]});	
		}else if((startDir=='l'&&endDir=='r')||(startDir=='r'&&endDir=='l')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[1]});	
		}else if((startDir=='d'&&endDir=='r')||(startDir=='r'&&endDir=='d')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[2]});
		}else if((startDir=='d'&&endDir=='l')||(startDir=='l'&&endDir=='d')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[3]});
		}else if((startDir=='u'&&endDir=='l')||(startDir=='l'&&endDir=='u')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[4]});
		}else if((startDir=='r'&&endDir=='u')||(startDir=='u'&&endDir=='r')) {
			animation=new Animation(new BufferedImage[] {Assets.vines[5]});
		}else {
			animation=new Animation(new BufferedImage[] {Assets.vines[0]});
			System.out.println("a vine was created and but its startDir is "
			+startDir+" and its endDir is "+endDir+" breaking everything");
		}
		
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Vine(x,y,startDir,endDir);
	}
	public void grow() {
		if(killed) {
			killed=false;
			return;
		}
		if(grown) {
			nextVine.grow();
			return;
		}
		grown=true;
		char nextEndDir;
		//this is probably a dumb way of getting a random direction but whatever
		char[] dirs = new char[3];
		int i=0;
		for(char j:new char[] {'u','r','d','l'}) {
			if(j!=getOppositeDir(endDir)) {
				dirs[i]=j;
				i++;
			}
		}
		nextEndDir=dirs[ThreadLocalRandom.current().nextInt(3)];
		
		switch (endDir) {
		case 'u':
			nextVine =new Vine(x+width/2,y-height/2,'d',nextEndDir);
			break;
		case 'r':
			nextVine = new Vine(x+width*3/2,y+height/2,'l',nextEndDir);
			break;
		case 'd':
			nextVine = new Vine(x+width/2,y+height*3/2,'u',nextEndDir);
			break;
		case 'l':
			nextVine = new Vine(x-width/2,y+height/2,'r',nextEndDir);
			break;
		}
		for(Entity j:entityManager.getEntities()) {
			if(j instanceof Tower &&j.getBounds().intersects(nextVine.bounds)) {
				nextVine.destroy();
			
			}
		}
		if(GameState.getFloor().checkwall((nextVine.x+width/2)/16, (nextVine.y+height/2)/16)) {
			nextVine.destroy();
		}
		
		entityManager.addEntity(nextVine);
		
	}

	@Override
	public void update() {
		
		
		
		
		if(health<=0) {
			if(nextVine!=null&& !nextVine.killed) {
				health+=nextVine.health;
				nextVine.health=0;
			}else {
				killed=true;
			}			
		}
		if(grown&&nextVine.isKilled()) {
			grown=false;
		}
	}
	

	
	@Override
	public int getDamage() {
		health--;
		return damage; 
	}

	@Override
	public int upgrade(char leftRight, int money) {
		
		return 0;
	}
	@Override
	public void destroy() {
		super.destroy();
		if(nextVine!=null) {
			nextVine.destroy();
		}
	}

	@Override
	public String select(char leftRight) {
		return "a vine that damages enemies. \nselling it can let it regrow \nin a different direction";
	}
}
