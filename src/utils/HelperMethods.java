package utils;

import Main.Game;

import java.awt.geom.Rectangle2D;

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

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed)
    {
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) //right
        {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1; // the hitbox of the player on the right side of a tile
        }
        else { // left
            return currentTile * Game.TILES_SIZE; // the hitbox of the player on the left side of a tile
        }
    }

    public static float getEntityYPos(Rectangle2D.Float hitbox, float airSpeed)
    {
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) // falling
        {
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1; //hitbox of the player on the floor
        }
        else { // jumping
            return currentTile * Game.TILES_SIZE; // hitbox of the player hitting the roof
        }
    }
}
