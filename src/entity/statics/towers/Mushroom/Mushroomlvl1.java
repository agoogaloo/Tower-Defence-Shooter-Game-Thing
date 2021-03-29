package entity.statics.towers.Mushroom;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.StatusEffect;
import entity.statics.towers.Tower;
import graphics.Animation;
import graphics.Assets;

public class Mushroomlvl1 extends Tower{
	public Mushroomlvl1(int x, int y) {
		super(x, y, 75, 75, new Animation(new BufferedImage[] {Assets.mushroom},600), 15);
		price=3;
		damage=5;
		sellValue=2;
		infoText="buying cost $"+price+"\n\na mushroom that poisons \nall enemies that enter it's \nsmall range";
		buyIcon=Assets.towerIcons[9];
		upgradeIcon=Assets.towerIcons[10];
	}
	@Override
	public Tower createNew(int x, int y) {
		return new Mushroomlvl1(x, y);
	}
	
	@Override
	public void update() {
		if (shotDelay<=0) { //If attack is true and it's been 60 frames since last shot, shoot again
			shotDelay = reloadTime; //Ensures the tower can't rapidly shoot
			for(Entity e:entityManager.getEntities()) { //Check each entity to see if it's intersecting the tower's range
				if(towerRange.intersects(e.getBounds().getX(), e.getBounds().getY(),
						e.getBounds().getWidth(), e.getBounds().getHeight())&&e instanceof Enemy) {
					e.giveStatusEffect(StatusEffect.POISON, 10, 120);
					
				}
			}
		}
		shotDelay-=1; //Counts up for every frame, towers can only shoot every 60 frames
		
	}
	
	@Override
	public int upgrade(char leftRight, int money) {
		return 0;
	}

	@Override
	public String hover(char leftRight) {
		return "coming sometime";
	}
}
