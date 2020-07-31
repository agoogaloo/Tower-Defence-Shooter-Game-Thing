package graphics.particles;

import java.awt.Graphics;
import java.util.ArrayList;

import graphics.Camera;

public class ParticleManager {
	private ArrayList<Particle> topParticles=new ArrayList<Particle>();
	private ArrayList<Particle> bottomParticles=new ArrayList<Particle>();
	
	public void addParticle(Particle p, boolean topLayer) {
		//adding the particle to the right list depending on whether it should go above or below the entities
		if(topLayer) {
			topParticles.add(p);
		}else {
			bottomParticles.add(p);
		}
	}
	
	public void update() {
		//updating the particles that are above the entities
		for(int i=topParticles.size()-1;i>=0;i--) {
			topParticles.get(i).update();
			if(topParticles.get(i).isRemove()) {
				topParticles.remove(i);
			}
		}
		//updating particles that are below the entity layer
		for(int i=bottomParticles.size()-1;i>=0;i--) {
			bottomParticles.get(i).update();
			if(bottomParticles.get(i).isRemove()) {
				bottomParticles.remove(i);
			}
		}
	}
	
	//drawing the particles that should be above the entities
	public void renderTop(Graphics g, Camera camera) {
		for(Particle i:topParticles) {
			i.render(g, camera);
		}
	}
	//rendering the particles that should go below the enities
	public void renderBottom(Graphics g, Camera camera) {
		for(Particle i:bottomParticles) {
			i.render(g, camera);
		}
	}

}
