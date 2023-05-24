package utils;


public class Constants {

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

        public static int getSpriteAmount(int playerAction)
        {
            switch(playerAction)
            {
                case RUNNING:
                case JUMP:
                    return 6;
                case IDLE:
                    return 4;
                case FALLING:
                    return 5;
                case GROUND:
                    return 1;
                case ATTACK_1:
                    return 21;
                case ATTACK_2:
                case ATTACK_3:
                    return 11;
                default:
                    return 1;
            }

        }
    }

}
