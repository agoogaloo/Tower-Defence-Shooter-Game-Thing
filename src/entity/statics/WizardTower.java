package entity.statics;

import java.awt.Graphics;

import graphics.Animation;
import graphics.Assets;
import graphics.Camera;

public class WizardTower extends Tower{
	Animation animation = new Animation(Assets.wizardTower,6);
	public WizardTower(int x, int y) {
		super(x, y);

	}
	public void Render(Graphics g, Camera camera) {
		g.drawImage(animation.getCurrentFrame(), x-camera.getxOffset(), y-camera.getyOffset(), null);
	}

}
