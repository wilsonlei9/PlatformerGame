package Inputs;

import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
                System.out.println("w");
                break;
            case KeyEvent.VK_A:
                System.out.println("a");
                break;
            case KeyEvent.VK_S:
                System.out.println("s");
                break;
            case KeyEvent.VK_D:
                System.out.println("d");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
