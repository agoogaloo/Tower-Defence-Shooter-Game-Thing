package graphics.particles;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.Camera;

public class ParticleEffect {
	ArrayList<Particle> particles=new ArrayList<Particle>();
	
	public ParticleEffect(int x, int y, int size, int amount, int time) {
		for(int i=0;i<amount;i++) {
			particles.add(new Particle(x, y, size, 0.3,time));
		}
	}
	
	public void update() {
		for(Particle i:particles) {
			i.update();
		}
	}
	
	public void render(Graphics g, Camera camera) {
		for(Particle i:particles) {
			i.render(g, camera);
		}
	}
}
