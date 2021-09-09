package entity.statics.towers.Plant;

import java.util.concurrent.ThreadLocalRandom;

import entity.statics.towers.Tower;
import entity.statics.towers.TowerSpawn;
import graphics.Animation;
import graphics.Assets;

public class VinePlant extends Tower{
	private Vine[] vines;
	private boolean waveCounted=false;
	private int colour;
	
	public VinePlant(int x, int y) {
		this(x,y,null);
	}
	public VinePlant(int x, int y, TowerSpawn spawn) {
		super(x, y, 115, 115, new Animation(Assets.vinePlant[0]), 9999,spawn);
		colour=ThreadLocalRandom.current().nextInt(0,Assets.vinePlant.length);
		upgradeIcon=Assets.towerIcons[9];
		animation= new Animation(Assets.vinePlant[colour]);
		height/=2;
		price=4;
		sellValue=3;
		damage=5;
		infoText="water cost: "+price+"\n\n a plant that grows long \nvines which damage enemies";	
		waveCounted=entityManager.getSpawner().waveComplete();
		
	}
	
	@Override
	public void init() {
		super.init();
		vines = new Vine[] {
			new Vine(x+bounds.width/2,y+5,'d','u',colour),
			new Vine(x+bounds.width/2,y+bounds.height-5,'u','d',colour),
			new Vine(x+width-5,y+bounds.height/2,'l','r',colour),
			new Vine(x+5,y+bounds.height/2,'r','l',colour)
		};		
		
		for(Vine i:vines) {
			entityManager.addEntity(i);
			i.grow();
		}
		
	}
	@Override
	public Tower createNew(int x, int y,TowerSpawn spawn) {
		return new VinePlant(x+width*2, y+bounds.height*2,spawn);
	}
	@Override
	public void update() {
		super.update();
		if(entityManager.getSpawner().waveComplete()&&!killed) {
			for(Vine i:vines){
				if(i.isKilled()) {
					i.grow();
				}
			}
			if(!waveCounted) {
				waveCounted=true;
				for(Vine i:vines){
					i.grow();
				}
			}
			
		}else if(!entityManager.getSpawner().waveComplete()) {
			waveCounted=false;
		}
			
	}	
	@Override
	protected void shoot() {}//making it not shoot anything

	@Override
	public int upgrade(char leftRight, int money) {
		if(money>=1) {
			for(Vine i:vines) {
				i.grow();
			}
			return 1;
		}
		return 0;
		
	}
	@Override
	public void destroy() {
		for(Vine i:vines) {
			i.destroy();
		}
		super.destroy();
		
	}

	@Override
	public String select(char leftRight) {
		return "cost: $1\n\nwater the plant to make its \nvines longer";
	}
}
