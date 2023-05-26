package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private BufferedImage[] pistolAnimation;
    private int aniTick;
    private int aniIndex;
    private int runIndex;
    private int aniSpeed;
    private int playerAction = IDLE;
    private boolean moving;
    private boolean attacking;
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y)
    {
        super(x, y);
        loadAnimations();
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
            g.drawImage(idleAnimation[aniIndex], (int)x, (int)y, 72, 90, null);
        }
        else if (playerAction == RUNNING)
        {
            g.drawImage(runningAnimation[runIndex], (int)x, (int)y, 70, 90, null);
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
        else {
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

    }
    private void setAnimation()
    {
        if (moving) {
            playerAction = RUNNING;
        }
        else if (attacking)
        {

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
        if (left && !right)
        {
            x -= playerSpeed;
            moving = true;
        }
        else if (right && !left)
        {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down)
        {
            y -= playerSpeed;
            moving = true;
        }
        else if (down && !up)
        {
            y += playerSpeed;
            moving = true;
        }
    }

    private void loadAnimations()
    {
        InputStream is = getClass().getResourceAsStream("/Monkey D Luffy.png");
        try {
            BufferedImage img = ImageIO.read(is);

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

            pistolAnimation = new BufferedImage[21];
            for (int i = 0; i < pistolAnimation.length; i++)
            {
                if (i < 14)
                {
                    pistolAnimation[i] = img.getSubimage(i * 48, 22, 48, 60);
                }
                if (i >= 14)
                {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}

