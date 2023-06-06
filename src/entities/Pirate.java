package entities;
import Main.Game;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Pirate extends Enemy{

    public Pirate(float x, float y) {
        super(x, y, PIRATE_WIDTH, PIRATE_HEIGHT, PIRATE);
        initializeHitbox(x, y, (int)(35 * Game.scale), (int)(30 * Game.scale));
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();

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
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                    {
                        newState(ATTACK);
                    }

                    move(lvlData);
                    break;
            }
        }

    }
    }

