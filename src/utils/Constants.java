package utils;


import Main.Game;

public class Constants {

    public static class EnemyConstants
    {
        public static final int PIRATE = 0;
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int PIRATE_WIDTH_DEFAULT = 40;
        public static final int PIRATE_HEIGHT_DEFAULT = 46;
        public static final int PIRATE_WIDTH = (int) (PIRATE_WIDTH_DEFAULT * Game.enemyScale);
        public static final int PIRATE_HEIGHT = (int) (PIRATE_HEIGHT_DEFAULT * Game.enemyScale);
        public static final int PIRATE_DRAWOFFSET_X = (int) (20 * Game.scale);
        public static final int PIRATE_DRAWOFFSET_Y = (int) (37 * Game.scale);

        public static int getSpriteAmount(int enemyType, int enemyState)
        {
            switch(enemyType)
            {
                case PIRATE:
                    switch(enemyState)
                    {
                        case IDLE:
                        case ATTACK:
                        case DEAD:
                            return 3;
                        case RUNNING:
                            return 6;
                    }
            }
            return 0;
        }
        public static int getMaxHealth(int enemyType)
        {
            switch(enemyType)
            {
                case PIRATE:
                    return 10;
                default:
                    return 1;
            }

        }

        public static int getEnemyDamage(int enemyType)
        {
            switch(enemyType)
            {
                case PIRATE:
                    return 15;
                default:
                    return 0;
            }
        }
    }

    public static class Directions
    {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants
    {
        public static final int RUNNING = 0;
        public static final int IDLE = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int ATTACK_1 = 5;
        public static final int ATTACK_2 = 6;
        public static final int ATTACK_3 = 7;

    }

}
