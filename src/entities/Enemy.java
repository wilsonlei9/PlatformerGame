package entities;
import Main.Game;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;
public abstract class Enemy extends Entity {
    protected int aniIndex;
    protected int enemyState;
    protected int enemyType;
    protected int aniTick;
    protected int aniSpeed = 40;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.scale;
    protected float walkSpeed = 0.35f * Game.scale;
    protected int direction = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initializeHitbox(x, y, width, height);
    }

    protected void updateAnimationTick()
    {
        aniTick++;
        if (aniTick > aniSpeed)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 3)
            {
                aniIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData)
    {
        updateMove(lvlData);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            if (!isEntityOnFloor(hitbox, lvlData))
            {
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir) {
            if (canMove(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = getEntityYPos(hitbox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;

                    if (direction == LEFT)
                    {
                        xSpeed = -walkSpeed;
                    }
                    else
                    {
                        xSpeed = walkSpeed;
                    }
                    if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                    {
                        if (isFloor(hitbox, xSpeed, lvlData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeDirection();

                    break;
            }
        }

    }

    protected void changeDirection()
    {
        if (direction == LEFT)
        {
            direction = RIGHT;
        }
        else {
            direction = LEFT;
        }
    }

    public int getAniIndex()
    {
        return aniIndex;
    }

    public int getEnemyState()
    {
        return enemyState;
    }

}
