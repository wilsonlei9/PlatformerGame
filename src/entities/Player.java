package entities;

import Main.Game;
import gamestates.Playing;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;
import static utils.HelperMethods.*;

public class Player extends Entity {
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] pistolAnimation;
    private int aniTick; // animation tick
    private int aniIndex; // animation index
    private int runIndex; // separate index for running animation
    private int aniSpeed; // how fast the animation is
    private int playerAction = IDLE;
    private boolean moving;
    private boolean attacking;
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOffset = 10 * Game.scale;
    private float yDrawOffset = 10 * Game.scale;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.scale;
    private float jumpSpeed = -2.25f * Game.scale;
    private float fallSpeedAfterCollision = 0.5f * Game.scale;
    private boolean inAir = false;

    private int maxHealth = 10;
    private int currentHealth = maxHealth;

    private int flipX = 0;
    private int flipWidth = 1;

    private Rectangle2D.Float attackBox;
    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initializeHitbox(x, y, 25 * Game.scale, 32 * Game.scale);
        initializeAttackBox();
    }

    private void initializeAttackBox()
    {
        attackBox = new Rectangle2D.Float((int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 168, 90);
    }

    public void update() {
        if (currentHealth <= 0)
        {
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePosition();
        if (attacking)
        {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack()
    {
        if (attackChecked || aniIndex != 1)
        {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    public void updateAttackBox()
    {
        if (right)
        {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.scale * 10);
        }
        else if (left)
        {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.scale * 10);
        }

        attackBox.y = hitbox.y + (Game.scale * 10);
    }

    public void render(Graphics g, int lvlOffset) {
        if (playerAction == IDLE) {
            if (flipWidth > 0)
            {
                g.drawImage(idleAnimation[aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 72, 90, null);
//                drawHitbox(g, lvlOffset);
                //drawAttackBox(g, lvlOffset);
            }
            if (flipWidth < 0)
            {
                g.drawImage(idleAnimation[aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX - 50, (int)(hitbox.y - yDrawOffset), 72 * flipWidth, 90, null);
               // drawHitbox(g, lvlOffset);
            }
        } else if (playerAction == RUNNING) {
            if (flipWidth > 0)
            {
                g.drawImage(runningAnimation[runIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), 70, 90, null);
                //drawHitbox(g, lvlOffset);
            }
            if (flipWidth < 0)
            {
                g.drawImage(runningAnimation[runIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX - 50, (int) (hitbox.y - yDrawOffset), 70 * flipWidth, 90, null);
               // drawHitbox(g, lvlOffset);
            }
        }

        else if (playerAction == ATTACK_1)
        {
//            if (aniIndex == 0)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 51, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 1)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 51, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 2)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 52, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 3)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 39, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 4)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 5)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 45, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 6)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 45, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 7)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 8)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 9)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 45, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 10)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 58, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 11)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 63, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 12)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 61, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 13)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 66, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
            if (aniIndex == 0)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 90, 90, null);
                hitbox.width = 90;
                //drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 1)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 96, 90, null);
                hitbox.width = 96;
               // drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 2)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 106, 90, null);
                hitbox.width = 106;
               // drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 3)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 130, 90, null);
                hitbox.width = 130;
              //  drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 4)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 144, 90, null);
                hitbox.width = 144;
               // drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 5)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 168, 90, null);
                hitbox.width = 190;
               // drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 6)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 178, 90, null);
                hitbox.width = 158;
               // drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 7)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
                hitbox.width = 48;
                //drawHitbox(g, lvlOffset);
            }
            if (aniIndex == 8)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
                hitbox.width = 48;
               // drawHitbox(g, lvlOffset);
            }
//            if (aniIndex == 21)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
//                hitbox.width = 48;
//                drawHitbox(g, lvlOffset);
//            }
//            if (aniIndex == 22)
//            {
//                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), 48, 90, null);
//                drawHitbox(g, lvlOffset);
//            }
    }
    }

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

    }

    private void updateAnimationTick()
    {
        if (playerAction == RUNNING)
        {
            aniSpeed = 20;
            aniTick++;
            if (aniTick >= aniSpeed)
            {
                aniTick = 0;
                runIndex++;
                if (runIndex >= runningAnimation.length)
                {
                    runIndex = 5;
                }
            }
        }
        else if (playerAction == IDLE)
        {
            aniSpeed = 40;
            aniTick++;
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= idleAnimation.length) {
                    aniIndex = 0;
                    attacking = false;
                }
            }
        }
        else if (playerAction == ATTACK_1)
        {
            aniSpeed = 15;
            aniTick++;
            if (aniTick >= aniSpeed)
            {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= pistolAnimation.length)
                {
                    aniIndex = 0;
                    attacking = false;
                    attackChecked = false;
                }
            }
        }

    }
    private void setAnimation()
    {
        if (moving) {
            playerAction = RUNNING;
        }
        else if (attacking)
        {
            playerAction = ATTACK_1;
        }
        else {
            playerAction = IDLE;
        }
    }

    public void updateGame()
    {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    private void updatePosition()
    {
        moving = false;
        if (jump)
        {
            jump();
        }
        if (!left && !right && !inAir)
        {
            return;
        }
        float xSpeed = 0;

        if (left)
        {
            xSpeed -= playerSpeed;
            flipX = width;
            flipWidth = -1;
        }
        if (right)
        {
            xSpeed += playerSpeed;
            flipX = 0;
            flipWidth = 1;
        }
        if (!inAir) // checks if sprite is on the floor
        {
            if (!isEntityOnFloor(hitbox, lvlData))
            {
                inAir = true; // if not on the floor, sprite is in the air
            }
        }
        if (inAir)
        {
            if (canMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData))
            {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else {
//                hitbox.y = getEntityYPos(hitbox, airSpeed);
                if (airSpeed > 0) // hitting the floor
                {
                    resetInAir();
                }
                else {
                    airSpeed = fallSpeedAfterCollision;
                    updateXPos(xSpeed);
                }
            }
        }
        else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void jump()
    {
        if (inAir)
        {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir()
    {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed)
    {
        if (canMove(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
        {
            hitbox.x += xSpeed;
        }
        else {
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value)
    {
        currentHealth += value;

        if (currentHealth <= 0)
        {
            currentHealth = 0;
        }
        else if (currentHealth >= maxHealth)
        {
            currentHealth = maxHealth;
        }
    }

    private void loadAnimations()
    {
        int n = 2032;
        int m = 2090;
            BufferedImage img = LoadSave.getSprite(LoadSave.PLAYER_SPRITE);

            idleAnimation = new BufferedImage[4];
            for (int i = 0; i < idleAnimation.length; i++)
            {
                idleAnimation[i] = img.getSubimage(i * 48, 0, 48, 60);
            }

            runningAnimation = new BufferedImage[11];
            for(int i = 5; i < runningAnimation.length; i++)
            {
                runningAnimation[i] = img.getSubimage(i * 47, 0, 47, 60);

            }

            pistolAnimation = new BufferedImage[8];
            for (int i = 0; i < pistolAnimation.length; i++) {
                if (i == 0)
                {
                    pistolAnimation[i] = img.getSubimage(690, n, 60, 60);
                }
                if (i == 1)
                {
                    pistolAnimation[i] = img.getSubimage(754, n, 64, 60);
                }
                if (i == 2)
                {
                    pistolAnimation[i] = img.getSubimage(830, n, 71, 60);
                }
                if (i == 3)
                {
                    pistolAnimation[i] = img.getSubimage(23, m, 87, 60);
                }
                if (i == 4)
                {
                    pistolAnimation[i] = img.getSubimage(121, m, 96, 60);
                }
                if (i == 5)
                {
                    pistolAnimation[i] = img.getSubimage(225, m, 112, 60);
                }
                if (i == 6)
                {
                    pistolAnimation[i] = img.getSubimage(345, m, 119, 60);
                }
                if (aniIndex == 7)
                {
                    pistolAnimation[i] = img.getSubimage(467, m, 48, 60);
                }
                if (aniIndex == 8)
                {
                    pistolAnimation[i] = img.getSubimage(513, m, 48, 60);
                }
            }
    }

    public void loadLvlData(int[][] lvlData)
    {
        this.lvlData = lvlData;
        if (isEntityOnFloor(hitbox, lvlData))
        {
            inAir = true;
        }

    }


    public void resetDirectionBooleans()
    {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttack(boolean attacking)
    {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump)
    {
        this.jump = jump;
    }
}

