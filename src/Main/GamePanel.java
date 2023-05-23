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
    private boolean isRunning;

    public GamePanel()
    {
        aniSpeed = 25;
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
            isRunning = true;
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


    public void changeX(int value)
    {
        this.xDelta += value;
        repaint();
    }

    public void changeY(int value)
    {
        this.yDelta += value;
    }

    public void setRectPosition(int x, int y)
    {
        this.xDelta = x;
        this.yDelta = y;
    }

    private void updateAnimationTick()
    {
        if (isRunning)
        {
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

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        updateAnimationTick();
        g.drawImage(runningAnimation[runIndex], (int)xDelta, (int)yDelta, 70, 90, null);
    }


}
