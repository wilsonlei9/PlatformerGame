package entities;

import Main.Game;
import gamestates.Playing;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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

        pirateRunning = new BufferedImage[6];
        for (int i = 0; i < pirateRunning.length; i++)
        {
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(21, 103, 36, 46);
            }
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(85, 103, 36, 46);
            }
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(146, 103, 36, 46);
            }
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(209, 103, 36, 46);
            }
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(269, 103, 36, 46);
            }
            if (i == 0)
            {
                pirateRunning[i] = temp.getSubimage(321, 103, 36, 46);
            }
        }

        pirateAttack = new BufferedImage[3];
        for (int i = 0; i < pirateAttack.length; i++)
        {
            if (i == 0)
            {
                pirateAttack[i] = temp.getSubimage(18, 173, 42, 40);
            }
            if (i == 1)
            {
                pirateAttack[i] = temp.getSubimage(81, 173, 46, 37);
            }
            if (i == 2)
            {
                pirateAttack[i] = temp.getSubimage(131, 173, 70, 40);
            }
        }

        pirateDeath = new BufferedImage[3];
        for (int i = 0; i < pirateDeath.length; i++)
        {
            if (i == 0)
            {
                pirateDeath[i] = temp.getSubimage(20, 305, 39, 49);
            }
            if (i == 1)
            {
                pirateDeath[i] = temp.getSubimage(74, 305, 52, 47);
            }
            if (i == 2)
            {
                pirateDeath[i] = temp.getSubimage(135, 305, 57, 49);
            }
        }
    }

    public void update(int[][] lvlData, Player player)
    {
        for (Pirate p : pirate)
        {
            if (p.getActive() == true)
            {
                p.update(lvlData, player);
            }
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
            if(p.getActive() == true)
            {
                if (p.getEnemyState() == RUNNING)
                {
                    g.drawImage(pirateIdle[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, PIRATE_WIDTH * p.flipWidth(), PIRATE_HEIGHT, null);
                   // p.drawHitbox(g, lvlOffset);
                }


                if (p.getEnemyState() == ATTACK)
                {
                    if (p.aniIndex == 0)
                    {
                        g.drawImage(pirateAttack[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (42 * Game.enemyScale) * p.flipWidth(), (int) (40 * Game.enemyScale), null);
                       // p.drawHitbox(g, lvlOffset);
                    }
                    if (p.aniIndex == 1)
                    {
                        g.drawImage(pirateAttack[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (46 * Game.enemyScale) * p.flipWidth(), (int) (40 * Game.enemyScale), null);
                       // p.drawHitbox(g, lvlOffset);
                    }
                    if (p.aniIndex == 2)
                    {
                        g.drawImage(pirateAttack[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (70 * Game.enemyScale) * p.flipWidth(), (int) (40 * Game.enemyScale), null);
                       // p.drawHitbox(g, lvlOffset);
                    }
                }

                if (p.getEnemyState() == DEAD)
                {
                    if (p.aniIndex == 0)
                    {
                        g.drawImage(pirateDeath[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (39 * Game.enemyScale) * p.flipWidth(), (int) (49 * Game.enemyScale), null);
                        //p.drawHitbox(g, lvlOffset);
                    }
                    if (p.aniIndex == 1)
                    {
                        g.drawImage(pirateDeath[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (52 * Game.enemyScale) * p.flipWidth(), (int) (49 * Game.enemyScale), null);
                       // p.drawHitbox(g, lvlOffset);
                    }
                    if (p.aniIndex == 2)
                    {
                        g.drawImage(pirateDeath[p.getAniIndex()], (int) p.getHitbox().x - lvlOffset + p.flipX(), (int) p.getHitbox().y, (int) (57 * Game.enemyScale) * p.flipWidth(), (int) (49 * Game.enemyScale), null);
                       // p.drawHitbox(g, lvlOffset);
                    }
                }
            }
        }
    }

        public void checkEnemyHit(Rectangle2D.Float attackBox)
        {
            for (Pirate p : pirate)
            {
                if (p.getActive() == true)
                {
                    if (attackBox.intersects(p.getHitbox()))
                    {
                        p.hurt(10);
                        return;
                    }
                }
            }
        }
    }

