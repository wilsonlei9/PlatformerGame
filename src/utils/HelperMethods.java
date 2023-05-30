package utils;

import Main.Game;

public class HelperMethods {
    public static boolean canMove(float x, float y, float width, float height, int[][] lvlData)
    {
        if (!isSolid(x, y, lvlData)) // top left
        {
            if (!isSolid(x + width, y + height, lvlData)) // bottom right
            {
                if (!isSolid(x + width, y, lvlData)) // top right
                {
                    if (!isSolid(x, y + height, lvlData)) // bottom left
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isSolid(float x, float y, int[][] lvlData)
    {
        if (x < 0 || x >= Game.GAME_WIDTH)
        {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT)
        {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];
        if (value >= 48 || value < 0 || value != 11)
        {
            return true;
        }
        return false;
    }

}
