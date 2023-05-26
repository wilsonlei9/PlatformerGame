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
    private int runIndex = 5;
    private Game game;

    public GamePanel(Game game)
    {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize()
    {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void updateGame()
    {

    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        game.render(g);

    }

    public Game getGame()
    {
        return game;
    }
}
