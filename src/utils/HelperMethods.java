package utils;

import Main.Game;

import java.awt.geom.Rectangle2D;
import java.sql.SQLOutput;

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

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        if (x == 6 || x == 12)
            return false;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
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

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData)
    {
        // check the pixel below bottom left and bottom right of hitbox
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
        {
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData)
    {
        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, y, lvlData))
            {
                return false;
            }
            if (!isTileSolid(xStart + i, y + 1, lvlData))
            {
                return false;
            }

        }

        return true;
    }

    public static boolean noObstacles(int[][] lvlData, Rectangle2D.Float playerHitbox, Rectangle2D.Float enemyHitbox, int yTile) {
        int firstXTile = (int) (playerHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (enemyHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);

    }
}
