package levels;

import Main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    protected Game game;
    private BufferedImage[] levelSprite;
    private Levels levelOne;

    public LevelManager(Game game)
    {
        this.game = game;
        //levelSprite = LoadSave.getSprite(LoadSave.LEVEL_SPRITE);
        importOutsideSprites();
        levelOne = new Levels(LoadSave.getLevelData());
    }

    private void importOutsideSprites()
    {
        BufferedImage img = LoadSave.getSprite(LoadSave.LEVEL_SPRITE);
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                int index = (i * 12) + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw (Graphics g, int lvlOffset)
    {

        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++)
        {
            for (int j = 0; j < Game.TILES_IN_WIDTH; j++)
            {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * j - lvlOffset, Game.TILES_SIZE * i, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update()
    {

    }

    public Levels getLevel()
    {
        return levelOne;
    }
}
