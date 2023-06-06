package entities;
import Main.Game;

import java.awt.geom.Rectangle2D;

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
    protected int tileY;
    protected float attackRange = Game.TILES_SIZE - 15;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initializeHitbox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void updateAnimationTick()
    {
        if (enemyState == IDLE)
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

        if (enemyState == ATTACK)
        {
            aniTick++;
            if (aniTick > aniSpeed)
            {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= 3)
                {
                    aniIndex = 0;
                    enemyState = IDLE;
                }
                else if(enemyState == HIT)
                {
                    enemyState = IDLE;
                }
                else if (enemyState == DEAD)
                {
                    active = false;
                }
            }
        }
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (canMove(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPos(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (direction == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (isFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            direction = RIGHT;
        else
            direction = LEFT;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY)
        {
            if (isPlayerInRange(player)) {
                if (noObstacles(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackRange * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackRange;
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int damage)
    {
        currentHealth -= damage;
        if (currentHealth <= 0)
        {
            newState(DEAD);
        }
        else {
            newState(HIT);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player)
    {
        if (attackBox.intersects(player.hitbox))
        {
            player.changeHealth(-getEnemyDamage(enemyType));
        }
        attackChecked = true;

    }

    protected void changeWalkDir() {
        if (direction == LEFT)
            direction = RIGHT;
        else
            direction = LEFT;

    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean getActive()
    {
        return active;
    }
}
