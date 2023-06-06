package entities;
import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Pirate extends Enemy{

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Pirate(float x, float y) {
        super(x, y, PIRATE_WIDTH, PIRATE_HEIGHT, PIRATE);
        initializeHitbox(x, y, (int)(35 * Game.scale), (int)(30 * Game.scale));
        initializeAttackBox();
    }

    private void initializeAttackBox()
    {
        attackBox = new Rectangle2D.Float(x, y, (int) (70 * Game.scale), (int) (40 * Game.scale));
        attackBoxOffsetX = (int) (Game.scale * 30);
    }

    public void update(int[][] lvlData, Player player)
    {
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox()
    {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }
    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player))
                    {
                        turnTowardsPlayer(player);
                    }
                    if (isPlayerCloseForAttack(player))
                    {
                        newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                    {
                        attackChecked = false;
                    }
                    if (aniIndex == 2 && !attackChecked)
                    {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case HIT:

                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset)
    {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX()
    {
        if (direction == RIGHT)
        {
            return width;
        }
        else {
            return 0;
        }
    }

    public int flipWidth()
    {
        if (direction == RIGHT)
        {
            return -1;
        }
        else {
            return 1;
        }
    }
}

