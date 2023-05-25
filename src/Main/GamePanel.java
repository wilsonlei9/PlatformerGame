package Main;

import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private BufferedImage img;
    private BufferedImage[] idleAnimation;
    private BufferedImage[] runningAnimation;
    private int aniTick;
    private int aniIndex;
    private int runIndex;
    private int aniSpeed;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving;

    public GamePanel()
    {
        runIndex = 5;
        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations()
    {
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
    }

    private void importImg()
    {
        InputStream is = getClass().getResourceAsStream("/Monkey D Luffy.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setPanelSize()
    {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
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

    private void updatePosition()
    {
        if (moving)
        {
            switch(playerDirection)
            {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void updateGame()
    {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (playerAction == IDLE)
        {
            g.drawImage(idleAnimation[aniIndex], (int)xDelta, (int)yDelta, 72, 90, null);
        }
        else if (playerAction == RUNNING)
        {
            g.drawImage(runningAnimation[runIndex], (int)xDelta, (int)yDelta, 70, 90, null);
        }
    }


}
