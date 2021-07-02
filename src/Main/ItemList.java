package Main;

import java.awt.image.BufferedImage;

import entity.EntityManager;
import entity.mobs.player.guns.Beam;
import entity.mobs.player.guns.Gun;
import entity.mobs.player.guns.Pistol;
import entity.mobs.player.guns.Sniper;
import entity.statics.towers.EmptyTowerSlot;
import entity.statics.towers.Tower;
import entity.statics.towers.Plant.Plantlvl1;
import entity.statics.towers.laser.LaserTowerlvl1;
import entity.statics.towers.wizard.WizardTowerlvl1;

public class ItemList {
	private static Tower[] towers = new Tower[] {
			new EmptyTowerSlot(), new WizardTowerlvl1(0, 0), new LaserTowerlvl1(0, 0,'r'), new Plantlvl1(0, 0)
	};
	private static Gun[] guns = new Gun[] {
			new Pistol(null),new Beam(null), new Sniper(null)
	};
	public final static int TOWERS_LEN=towers.length;
	public final static int GUNS_LEN=towers.length;
	public final static int EMTPTY=0,WIZARD=1,LASER=2,PLANT=3;
	public final static int PISTOL=TOWERS_LEN, BEAM=1+TOWERS_LEN, SNIPER=2+TOWERS_LEN;
	
	
	
	public static Tower newTower(int x, int y, int id) {
		return towers[id].createNew(x, y);
	}
	public static Gun newGun(EntityManager manager, int id) {
		return guns[id-TOWERS_LEN].createNew(manager);
	}
	public static Tower getTower(int id) {
		return towers[id];
	}
	public static Gun getGun(int id) {
		return guns[id-TOWERS_LEN];
	}
	public static boolean isTower(int id) {
		if(id<TOWERS_LEN&&id>=0) {
			return true;
		}
		return false;

	}
	public static boolean isGun(int id) {
		if(id>=TOWERS_LEN&&id<GUNS_LEN+TOWERS_LEN) {
			return true;
		}
		return false;

	}
	
	public static BufferedImage getIcon(int id) {
		if(isGun(id)) {
			return getGun(id).getIcon();
		}
		if(isTower(id)) {
			return getTower(id).getAnimation().getCurrentFrame();
		}
		System.err.println("there is no item with id "+id);
		return null;
				
	}
	
	
}
