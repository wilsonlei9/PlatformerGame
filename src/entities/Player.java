package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;

public class Player extends Entity{
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private int aniTick;
    private int aniIndex;
    private int runIndex;
    private int aniSpeed;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving;

    public Player(float x, float y)
    {
        super(x, y);
        loadAnimations();
    }

    public void update()
    {
        updateAnimationTick();
        setAnimation();
        updatePosition();
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

    public void setDirection(int direction)
    {
        this.playerDirection = direction;
    }

    public void setMoving(boolean moving)
    {
        this.moving = moving;
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
                }
            }
        }

    }
    private void setAnimation()
    {
        if (moving) {
            playerAction = RUNNING;
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
        if (moving)
        {
            switch(playerDirection)
            {
                case LEFT:
                    x -= 5;
                    break;
                case UP:
                    y -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case DOWN:
                    y += 5;
                    break;
            }
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

