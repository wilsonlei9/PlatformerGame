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

    public GamePanel()
    {
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg()
    {
        InputStream is = getClass().getResourceAsStream("/download.png");
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

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }


}
