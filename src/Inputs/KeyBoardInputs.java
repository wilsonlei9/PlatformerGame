package Inputs;

import Main.GamePanel;
import Main.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Main.GamePanel;
import static utils.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                gamePanel.setMoving(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                gamePanel.setMoving(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                gamePanel.setMoving(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                gamePanel.setMoving(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                gamePanel.setMoving(false);
            case KeyEvent.VK_A:
                gamePanel.setMoving(false);
            case KeyEvent.VK_S:
                gamePanel.setMoving(false);
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;
        }
    }
}
