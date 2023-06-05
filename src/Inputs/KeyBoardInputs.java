package Inputs;

import Main.GamePanel;
import Main.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Main.GamePanel;
import gamestates.Gamestate;

import static utils.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public boolean upPressed,downPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(Gamestate.state)
        {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch(Gamestate.state)
        {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
