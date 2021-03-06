package entity.mobs.enemy;

public class TutorialEnemy extends Enemy {

	public TutorialEnemy(int x, int y, int direction, double speed, int reloadTime) {
		this(x,y,direction,speed,reloadTime,1);
		
	}
	public TutorialEnemy(int x, int y, int direction, double speed, int reloadTime, int health) {
		super(x, y, direction);
		
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		setDefaultBounds();
		
		damage=0; //The amount of damage the enemy will do if it collides with the player
		this.reloadTime=reloadTime;
		this.speed=speed;
		this.health=health;
	}

}
