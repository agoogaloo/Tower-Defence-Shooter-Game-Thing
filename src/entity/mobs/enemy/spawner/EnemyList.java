package entity.mobs.enemy.spawner;

import entity.mobs.enemy.BabyBot;
import entity.mobs.enemy.Enemy;
import entity.mobs.enemy.GreenEnemy;
import entity.mobs.enemy.HamburgerBot;
import entity.mobs.enemy.RedEnemy;
import entity.mobs.enemy.TankBot;
import entity.mobs.enemy.YellowEnemy;

public class EnemyList {
    private static Enemy[] enemies = new Enemy[]{
        new RedEnemy(0, 0, 0),
        new GreenEnemy(0, 0, 0),
        new YellowEnemy(0, 0, 0),
        new HamburgerBot(0, 0, 0),
        new TankBot(0, 0, 0),
        new BabyBot(0, 0, 0)
    };

    public static int RED=0, GREEN = 1, YELLOW = 2, HAMBURGER = 3, TANK = 4,BABYBOT = 5;

    public static Enemy newEnemy(int id, int x, int y, int direction){
        return enemies[id].createNew(x, y, direction);
    }

}
