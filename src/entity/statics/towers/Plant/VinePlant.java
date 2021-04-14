package entity.statics.towers.Plant;

import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class VinePlant extends Tower{
	public VinePlant(int x, int y) {
		super(x, y, 115, 115, new Animation(Assets.vinePlant,6), 60);
		price=3;
		sellValue=1;
		damage=5;
		statusEffect=StatusEffect.STUN;
		statusLength=20;
		infoText="not good yet";
		
		
		
	}
	@Override
	public void init() {
		entityManager.addEntity(new Vine(x+8,y+2,'d','u'));
		entityManager.addEntity(new Vine(x+8,y+18,'u','d'));
		entityManager.addEntity(new Vine(x+18,y+8,'l','r'));
		entityManager.addEntity(new Vine(x+2,y+8,'r','l'));
		
	}
	@Override
	public Tower createNew(int x, int y) {
		return new VinePlant(x+width*2, y+height*2);
	}
	

	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		return "not completed";
	}
}
