package entity.mobs.enemy;

public class RedEnemy extends Enemy{

	public RedEnemy(int x, int y, char direction) {
		super(x, y, direction);
		width=21; //The width of the enemy
		height=25; //The height of the enemy
		health=10;
		speed=1; //The speed which the enemy travels, higher nubmer resuts in higher speeds
		//damage=1; //The amount of damage the enemy will do if it collides with the player
		reloadTime = 60;
	}
}
