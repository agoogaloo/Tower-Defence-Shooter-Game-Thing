package entity.statics.towers.Plant;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
	private int colourIndex;
	Vine nextVine;
	Animation grownAnim, ungrownAnim;
	

	public Vine(int x, int y,char startDir, char endDir, int colourIndex) {
		friendly=true;
		
		this.width=10;
		this.height=10;
		this.x=x-width/2;
		this.y=y-height/2;
		this.colourIndex=colourIndex;
		
		this.towerRange= new Ellipse2D.Float(this.x, this.y, width,height);
		updateBounds();
		damage=1;
		this.startDir = startDir;
		this.endDir = endDir;
		health=10;
		layer=RenderLayer.BACK;
		
		int picIndex=0;
		if((startDir=='u'&&endDir=='d')||(startDir=='d'&&endDir=='u')) {
			picIndex=0;
		}else if((startDir=='l'&&endDir=='r')||(startDir=='r'&&endDir=='l')) {
			picIndex=1;
		}else if((startDir=='d'&&endDir=='r')||(startDir=='r'&&endDir=='d')) {
			picIndex=2;
		}else if((startDir=='d'&&endDir=='l')||(startDir=='l'&&endDir=='d')) {
			picIndex=3;
		}else if((startDir=='u'&&endDir=='l')||(startDir=='l'&&endDir=='u')) {
			picIndex=4;
		}else if((startDir=='r'&&endDir=='u')||(startDir=='u'&&endDir=='r')) {
			picIndex=5;
		}else {
			picIndex=0;
			System.out.println("a vine was created and but its startDir is "
			+startDir+" and its endDir is "+endDir+" breaking everything");
		}
		picIndex*=2;
		picIndex+=ThreadLocalRandom.current().nextInt(0, 2);
		grownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][picIndex]});	
		
		if(startDir=='d') {
			ungrownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][12]});
		}else if(startDir=='l') {
			ungrownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][13]});
		}else if(startDir=='u') {
			ungrownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][14]});
		}else if(startDir=='r') {
			ungrownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][15]});
		}else {
			ungrownAnim=new Animation(new BufferedImage[] {Assets.vines[colourIndex][12]});
			System.out.println("a vine was created and but its startDir is "+startDir+" and is breaking everything");
		}
		
		animation=ungrownAnim;
		
		
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Vine(x,y,startDir,endDir,colourIndex);
	}
	public void grow() {
		if(killed) {
			killed=false;
			health=10;
			entityManager.addEntity(this);
			
			return;
		}
		if(grown) {
			nextVine.grow();
			return;
		}
		
		grown=true;
		animation=grownAnim;
		
		char nextEndDir;
		ArrayList<Character> dirs =new ArrayList<Character>(Arrays.asList('u','r','d','l'));
		for(int i=4; i>=1;i--) {
			int dirIndex=ThreadLocalRandom.current().nextInt(i);
			nextEndDir= dirs.get(dirIndex);
			
			boolean canGrow=true;
			if(nextEndDir ==getOppositeDir(endDir)) {
				canGrow=false;	
			}else {
				nextVine=nextVine(nextEndDir);
				if(GameState.getFloor().checkwall((nextVine.x+width/2)/16,(nextVine.y+height/2)/16)) {
					canGrow=false; 
				}
			}
			
			if(canGrow) {
				break;
			}
			dirs.remove(dirIndex);			
		}
		
		if(nextVine!=null) {
			entityManager.addEntity(nextVine);
		}
		
	}
	
	private Vine nextVine(char dir) {
		Vine vine;
		switch (endDir) {
		case 'u':
			vine =new Vine(x+width/2,y-height/2,'d',dir,colourIndex);
			break;
		case 'r':
			vine = new Vine(x+width*3/2,y+height/2,'l',dir,colourIndex);
			break;
		case 'd':
			vine = new Vine(x+width/2,y+height*3/2,'u',dir,colourIndex);
			break;
		case 'l':
			vine = new Vine(x-width/2,y+height/2,'r',dir,colourIndex);
			break;
			
		default: 
			System.out.println("tried to grow the next vine but "+dir+" isn't a direction");
			return null;
		}
		return vine;
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
			animation=ungrownAnim;
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
		return "a vine that damages enemies \nthat touch it.";
	}
}
