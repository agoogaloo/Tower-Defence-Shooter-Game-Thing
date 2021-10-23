package entity.statics.breakables;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import graphics.Assets;

public class Floor1Breakable extends Breakable{
	
	protected BufferedImage pic;
	
	public Floor1Breakable(int x, int y) {
		super(Assets.lv1Breakables[ThreadLocalRandom.current().nextInt(Assets.lv1Breakables.length)],x,y);
	}
}
