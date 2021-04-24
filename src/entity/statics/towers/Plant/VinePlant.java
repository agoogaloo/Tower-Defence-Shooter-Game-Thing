package entity.statics.towers.Plant;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class VinePlant extends Tower{
	private Vine[] vines;
	private boolean waveCounted=false;
	
	public VinePlant(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.vinePlant,6), 60);
		height/=2;
		price=3;
		sellValue=1;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=20;
		infoText="an unfinished mess";	
		waveCounted=entityManager.getSpawner().waveComplete();
		
	}
	@Override
	public void init() {
		vines = new Vine[] {
			new Vine(x+bounds.width/2,y,'d','u'),
			new Vine(x+bounds.width/2,y+bounds.height,'u','d'),
			new Vine(x+width,y+bounds.height/2,'l','r'),
			new Vine(x,y+bounds.height/2,'r','l')
		};		
		
		for(Vine i:vines) {
			entityManager.addEntity(i);
			i.grow();
		}
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new VinePlant(x+width*2, y+bounds.height*2);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if(entityManager.getSpawner().waveComplete()&&!killed) {
			if(!waveCounted) {
				waveCounted=true;
				for(Vine i:vines){
					i.grow();
				}
			}
			for(Vine i:vines){
				if(i.isKilled()) {
					i.grow();
				}
			}
		}else if(!entityManager.getSpawner().waveComplete()) {
			waveCounted=false;
		}
			
	}	

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
		super.destroy();
		for(Vine i:vines) {
			i.destroy();
		}
	}

	@Override
	public String select(char leftRight) {
		return "water the plant to help it grow its vines";
	}
}
