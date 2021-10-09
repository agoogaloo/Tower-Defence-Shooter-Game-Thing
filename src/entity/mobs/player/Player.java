package entity.mobs.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import entity.Entity;
import entity.mobs.Mobs;
import entity.mobs.pickups.ItemList;
import entity.mobs.pickups.guns.Gun;
import entity.mobs.player.UI.MiniMap;
import entity.mobs.player.UI.PlayerUI;
import entity.mobs.player.UI.TowerPlacer;
import entity.statics.Core;
import entity.statics.hitBox.CircleBox;
import entity.statics.towers.Tower;
import graphics.Camera;
import graphics.ImageUtils;
import graphics.particles.InstantEffect;
import graphics.particles.movers.Straight;
import graphics.particles.movers.spawnPattern.Point;
import graphics.particles.shapes.OvalParticle;
import graphics.particles.shapes.colourers.Timed;
import states.GameState;
import states.State;
import states.TowerPickup;

//@author Matthew (did all of player movement, the player class, anything related to core)
//@author Kevin (did animation, shoot method, shot delay, anything related to tower, rendering)

public class Player extends Mobs {
	//declaring variables
	
	private int dustDelay=0; 
	private int money=10,invincibility=0;
	private int[] towers = {ItemList.WIZARD,ItemList.EMPTY,ItemList.EMPTY,ItemList.EMPTY};
	private ArrayList<Gun> guns;
	private int gun=0;
	
	
	private Camera camera; //Camera needed so it can follow player
	private Core core; //Core is related to player, as core effects player health
	
	private PlayerUI ui;
	private TowerPlacer towerPlacer;
	private MiniMap miniMap;
	private Animator animator=new Animator();
	
	private CircleBox spinHitBox;
	private int spinDamage=20;
	private double initXSpeed, initYSpeed;
	
	private boolean ghostMode=false;//toggles the ghost mode cheat that lets you go through walls
	
	private BufferedImage currentPic;//the current image of the player
	
	private char direction='d'; //Sets player's direction to down by default at the start
	
	/**
	 * constructor for the player that you control
	 * @param x - x location in pixels
	 * @param y - y location in pixels
	 */
	public Player(int x, int y) {
		width = 6; //The specific width of the player
		height = 13; //The specific height of the player
		speed = 3; //The speed which the player moves at, higher the value the faster the speed
		health = 6;  //The amount of health the player has, when health hits 0 the player "dies"
		damage=0; // The amount of damage the player will do when it runs into an enemy
		friendly=true; //its "team" so that it enemies will deal damage to you but you wont damage other things on your "team"
		camera=GameState.getCamera(); //The camera will follow the player
		guns=new ArrayList<Gun>(Arrays.asList(ItemList.newGun(entityManager, ItemList.PISTOL)));
		setLocation(x-width/2, y-height/2);
		
		
		
	}

	public void reset(int x, int y) {
		//this is called at the start of each floor so it can reset/set its ui/location
		setLocation(x+width/2, y+height/2);
		ui=new PlayerUI();
		towerPlacer=new TowerPlacer();
		miniMap=new MiniMap();		
		
	}
	
	@Override
	public void update() {
		
		int moveKeys=0;
		
		if(!(animator.getCurrentAnimation()==PlayerAnimations.SPIN||animator.getCurrentAnimation()==PlayerAnimations.SPINEND)) {
			if (State.getInputs().isShoot()) { //If shoot in PlayerInput is triggered (by clicking) than it will call the shoot method
				guns.get(gun).shoot(x+width/2, y+height/2, State.getInputs().getMouseX()+camera.getxOffset(),
						State.getInputs().getMouseY()+camera.getyOffset());
			}
			if (State.getInputs().isUp()) {// if the up input is triggered than it will move the player up
				changeY -= speed;
				direction='u';
				moveKeys++;
			}
			if (State.getInputs().isDown()) {// moving other directions
				changeY += speed;
				direction='d';
				moveKeys++;
			}
			if (State.getInputs().isLeft()) {
				changeX -= speed;
				direction='l';
				moveKeys++;
			}
			if (State.getInputs().isRight()) {
				changeX += speed;
				direction='r';
				moveKeys++;
			}
		}
		
		if (State.getInputs().isNextGun()) {
			
			gun++;
			if(gun>=guns.size()) {
				gun=0;
			}
		}
		if (State.getInputs().isPrevGun()) {
			gun--;
			if(gun<0) {
				gun=guns.size()-1;
			}
		}
		
		if(moveKeys>=2) {
			changeX/=1.25;
			changeY/=1.25;
		}
		
		if(animator.getCurrentAnimation()==PlayerAnimations.SPIN||animator.getCurrentAnimation()==PlayerAnimations.SPINSTART) {
			changeX=initXSpeed;
			changeY=initYSpeed;
		}else if(animator.getCurrentAnimation()==PlayerAnimations.SPINEND) {
			initXSpeed*=0.8;
			initYSpeed*=0.8;
			changeX=initXSpeed;
			changeY=initYSpeed;
		}else {
			
			initXSpeed=changeX;
			initYSpeed=changeY;
		}
		
		health-=core.giveDamage(); //If Core takes damage apply the damage to the player's health, as player shares damage with core
		ui.update((int)Math.round(health), money,guns,gun);//updating ui with current health and money
		miniMap.update(entityManager.getEntities(), x, y);
		guns.get(gun).update();
		tower();
		
		
		if(changeX==0&&changeY==0) {
			currentPic=animator.update(direction, false,State.getInputs().isSpin());
			dustDelay=20;
		}else {
			currentPic=animator.update(direction, true,State.getInputs().isSpin());
			if(dustDelay>=15) {
				
				new InstantEffect(3, new Straight(new Point(x+7,y+12),0.5), 
					new OvalParticle(2, new Timed(15)), false);
				dustDelay=0;
			}
		}
		
		if(ghostMode) {
			trueX+=changeX;
			trueY+=changeY;
			x=(int)(trueX);
			y=(int)(trueY);
			changeX = 0;// resetting change x and y
			changeY = 0;
			updateBounds();
		}else {
			move(); //Updates movements, applied by the directional input keys. Also updates bounds and applies wall collision
		}
		spinAttack();
		dustDelay ++;
		invincibility--;
		GameState.newFloor(GameState.getFloor().getSpawnData((x+width/2)/16, (y+height/2)/16));
	}
	
	private void spinAttack() {
		if(spinHitBox!=null) {
			invincibility=2;
			spinHitBox.updateBounds(x-14, y-11);
			for(Entity i:spinHitBox.getCollisions()) {
				if(!i.isFriendly()&&i.hasCollisions()) {
					i.damage(spinDamage);
					
				}
				
			}
		}
		if(animator.getCurrentAnimation()==PlayerAnimations.SPIN&&spinHitBox==null) {
			entityManager.addEntity(spinHitBox=new CircleBox(x-14, y-11, 35,35,true));
			
			
		}
		if(animator.getCurrentAnimation()==PlayerAnimations.SPINEND&&spinHitBox!=null) {
			spinHitBox.destroy();
			spinHitBox=null;
		}
		
		
		
		
	}
	
	private void tower() { //Tower method to create a tower
		Tower tower=towerPlacer.update(money,towers,camera, entityManager.getEntities(), direction);
		int moneySpent=towerPlacer.getSpentMoney();
		entityManager.addEntity(tower);
		money-=moneySpent;
	}
	
	@Override
	public void damage() {
		if(invincibility<=0) {
			for (Entity e : entityCollide()) {//checking what is colliding with itself
				//checking which side the thing that touched it is on 
				//(making sure enemies only attack the player, player cant attack the core, etc.)
				if (e.isFriendly() != friendly) {
					health -= e.getDamage();//dealing however much damage that entity does
					
					if(e.getDamage()>0) {//making sure you actually take damage from the entity
						//shaking the screen so it feels like you actually got hit
						GameState.screenShake(0.75);
						currentPic=ImageUtils.fillPic(currentPic);
						invincibility=30;
						System.out.println("damaged by "+e.getClass().getSimpleName()+" for "+e.getDamage()+" damage");
					}
				}
			}
			if (health <= 0) {//if it has no more health left that it should be dead
				killed = true;
			}
		}
	}
	@Override
	public void render(Graphics g, Camera camera) { //Draws different player sprites depending on it's direction 
		g.drawImage(currentPic,x - camera.getxOffset()-animator.getxOffest(), y - camera.getyOffset()-animator.getyOffest(), null);
		
		towerPlacer.render(g, camera);
		
	}
	
	public void createCore() {
		createCore(x,y);
	}
	
	public void createCore(int x, int y) {
		if(core!=null) {//making sure there is a core to destroy so it wont throw an error
			core.destroy();//removing the old core
		}
		core=new Core(x,y); //makes the core where the paramiters tell it to
		entityManager.addEntity(core); //Adds the core to the entityManager so it can exist
	}
	
	public void giveMoney(int amount) {
		//lets us give the player money
		money+=amount;
	}
	public void giveItem(int id) {
		//if its a tower
		if(ItemList.isTower(id)) {
			new TowerPickup(id);
		}else if(ItemList.isGun(id)) {
			guns.add(ItemList.newGun(entityManager, id));
			gun=guns.size()-1;
		}
	}
	public void heal(int amount) {
		//lets other things heal the player
		health+=amount;
	}
	public void swapTower(int newTower, int location) {
		towers[location]=newTower;
	}
	public int[] getTowers() {
		return towers;
	}
	public ArrayList<Gun> getGuns() {
		return guns;
	}
	public int getMoney() {
		return money;
	}
	public char getDirection() {
		return direction;
	}
	public void toggleGhost() {
		ghostMode=!ghostMode;
	}
}
