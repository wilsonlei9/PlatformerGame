package entities;

import Main.Game;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;
import static utils.HelperMethods.*;

public class Player extends Entity{
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] pistolAnimation;
    private int aniTick; // animation tick
    private int aniIndex; // animation index
    private int runIndex; // separate index for running animation, because sprite sheet was designed weird
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

    public Player(float x, float y, int width, int height)
    {
        super(x, y, width, height);
        loadAnimations();
        initializeHitbox(x, y,  25* Game.scale, 32 * Game.scale);
    }

    public void update()
    {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g)
    {
        if (playerAction == IDLE)
        {
            g.drawImage(idleAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 72, 90, null);
            drawHitbox(g);
        }

        else if (playerAction == RUNNING)
        {
            g.drawImage(runningAnimation[runIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 70, 90, null);
            drawHitbox(g);
        }

        else if (playerAction == ATTACK_1)
        {
            if (aniIndex == 0)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 51, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 1)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 51, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 2)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 52, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 3)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 39, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 4)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 48, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 5)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 45, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 6)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 45, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 7)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 48, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 8)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 48, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 9)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 45, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 10)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 58, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 11)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 63, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 12)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 61, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 13)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 66, 90, null);
                drawHitbox(g);
            }
            if (aniIndex == 14)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 90, 90, null);
                hitbox.width = 90;
                drawHitbox(g);
            }
            if (aniIndex == 15)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 96, 90, null);
                hitbox.width = 96;
                drawHitbox(g);
            }
            if (aniIndex == 16)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 106, 90, null);
                hitbox.width = 106;
                drawHitbox(g);
            }
            if (aniIndex == 17)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 130, 90, null);
                hitbox.width = 130;
                drawHitbox(g);
            }
            if (aniIndex == 18)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 144, 90, null);
                hitbox.width = 144;
                drawHitbox(g);
            }
            if (aniIndex == 19)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 168, 90, null);
                hitbox.width = 19;
                drawHitbox(g);
            }
            if (aniIndex == 20)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 178, 90, null);
                hitbox.width = 158;
                drawHitbox(g);
            }
            if (aniIndex == 21)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 48, 90, null);
                hitbox.width = 48;
                drawHitbox(g);
            }
            if (aniIndex == 22)
            {
                g.drawImage(pistolAnimation[aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), 48, 90, null);
                drawHitbox(g);
            }
        }
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
            aniSpeed = 10;
            aniTick++;
            if (aniTick >= aniSpeed)
            {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= pistolAnimation.length)
                {
                    aniIndex = 0;
                    attacking = false;
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
        }
        if (right)
        {
            xSpeed += playerSpeed;
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
                hitbox.y = getEntityYPos(hitbox, airSpeed);
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

            pistolAnimation = new BufferedImage[22];
            for (int i = 0; i < pistolAnimation.length; i++) {
                if (i == 0)
                {
                    pistolAnimation[i] = img.getSubimage(8, n, 34, 60);
                }
                if (i == 1)
                {
                    pistolAnimation[i] = img.getSubimage(57, n, 34, 60);
                }
                if (i == 2)
                {
                    pistolAnimation[i] = img.getSubimage(109, n, 35, 60);
                }
                if (i == 3)
                {
                    pistolAnimation[i] = img.getSubimage(157, n, 26, 60);
                }
                if (i == 4)
                {
                    pistolAnimation[i] = img.getSubimage(198, n, 32, 60);
                }
                if (i == 5)
                {
                    pistolAnimation[i] = img.getSubimage(239, n, 30, 60);
                }
                if (i == 6)
                {
                    pistolAnimation[i] = img.getSubimage(239, n, 30, 60);
                }
                if (i == 7)
                {
                    pistolAnimation[i] = img.getSubimage(343, n, 32, 60);
                }
                if (i == 8)
                {
                    pistolAnimation[i] = img.getSubimage(390, n, 32, 60);
                }
                if (i == 9)
                {
                    pistolAnimation[i] = img.getSubimage(431, n, 30, 60);
                }
                if (i == 10)
                {
                    pistolAnimation[i] = img.getSubimage(477, n, 39, 60);
                }
                if (i == 11)
                {
                    pistolAnimation[i] = img.getSubimage(528, n, 42, 60);
                }
                if (i == 12)
                {
                    pistolAnimation[i] = img.getSubimage(579, n, 41, 60);
                }
                if (i == 13)
                {
                    pistolAnimation[i] = img.getSubimage(640, n, 44, 60);
                }
                if (i == 14)
                {
                    pistolAnimation[i] = img.getSubimage(690, n, 60, 60);
                }
                if (i == 15)
                {
                    pistolAnimation[i] = img.getSubimage(754, n, 64, 60);
                }
                if (i == 16)
                {
                    pistolAnimation[i] = img.getSubimage(830, n, 71, 60);
                }
                if (i == 17)
                {
                    pistolAnimation[i] = img.getSubimage(23, m, 87, 60);
                }
                if (i == 18)
                {
                    pistolAnimation[i] = img.getSubimage(121, m, 96, 60);
                }
                if (i == 19)
                {
                    pistolAnimation[i] = img.getSubimage(225, m, 112, 60);
                }
                if (i == 20)
                {
                    pistolAnimation[i] = img.getSubimage(345, m, 119, 60);
                }
                if (i == 21)
                {
                    pistolAnimation[i] = img.getSubimage(474, m, 32, 60);
                }
                if (i == 22)
                {
                    pistolAnimation[i] = img.getSubimage(519, m, 32, 60);
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

