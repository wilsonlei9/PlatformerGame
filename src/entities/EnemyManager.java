package entities;

import gamestates.Playing;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] pirateIdle;
    private BufferedImage[] pirateRunning;
    private BufferedImage[] pirateAttack;
    private BufferedImage[] pirateDeath;;
    private ArrayList<Pirate> pirate = new ArrayList<>();

    public EnemyManager(Playing playing)
    {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies()
    {
        pirate = LoadSave.getPirates();
    }

    private void loadEnemyImgs()
    {
        pirateIdle = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSprite(LoadSave.ENEMY_SPRITE);
        for (int i = 0; i < pirateIdle.length; i++)
        {
            if (i == 0)
            {
                pirateIdle[i] = temp.getSubimage(17, 37, PIRATE_WIDTH_DEFAULT, PIRATE_HEIGHT_DEFAULT);
            }
            if (i == 1)
            {
                pirateIdle[i] = temp.getSubimage(79, 37, PIRATE_WIDTH_DEFAULT, PIRATE_HEIGHT_DEFAULT);
            }
            if (i == 2)
            {
                pirateIdle[i] = temp.getSubimage(142, 37, PIRATE_WIDTH_DEFAULT, PIRATE_HEIGHT_DEFAULT);
            }
        }
    }

    public void update(int[][] lvlData)
    {
        for (Pirate p : pirate)
        {
            p.update(lvlData);
        }
    }



    public void draw(Graphics g, int lvlOffset)
    {
        drawPirate(g, lvlOffset);

    }

    private void drawPirate(Graphics g, int lvlOffset)
    {
        for (Pirate p : pirate)
        {
            g.drawImage(pirateIdle[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset, (int) p.getHitbox().y, PIRATE_WIDTH, PIRATE_HEIGHT, null);
            p.drawHitbox(g, lvlOffset);
        }
    }
}
