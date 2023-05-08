package Main;

import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100;
    private float yDelta = 100;
    private float xDir = 0.03f;
    private float yDir = 0.03f;
    private int frames;
    private long lastCheck;
    private Color color = new Color(0, 0, 0);
    private Random random;
    public GamePanel()
    {
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
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

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta, (int)yDelta, 200, 50);
        frames ++;
        if (System.currentTimeMillis() - lastCheck >= 1000)
        {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
        repaint();
    }

    public void updateRectangle()
    {
        xDelta += xDir;
        if (xDelta > 400 || xDelta < 0)
        {
            xDir *= -1;
        }
        yDelta += yDir;
        if (yDelta > 400 || yDelta < 0)
        {
            yDir *= -1;
        }
    }

    private Color randomColor()
    {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
