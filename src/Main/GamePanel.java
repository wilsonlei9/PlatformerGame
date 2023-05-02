package Main;

import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int xDelta = 0;
    private int yDelta = 0;
    public GamePanel()
    {
        mouseInputs = new MouseInputs();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.fillRect(100 + xDelta, 100 + yDelta, 200, 50);
    }
}
