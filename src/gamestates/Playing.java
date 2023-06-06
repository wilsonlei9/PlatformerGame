package gamestates;

import Main.Game;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import ui.GameOver;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.getLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage backgroundImg;
    private GameOver gameOverOverlay;
    private boolean gameOver;

    public Playing(Game game)
    {
        super(game);
        initializeClasses();
        backgroundImg = LoadSave.getSprite(LoadSave.BACKGROUND);
    }

    private void initializeClasses()
    {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (70 * Game.scale), (int) (90 * Game.scale), this);
        player.loadLvlData(levelManager.getLevel().getLvlData());
        gameOverOverlay = new GameOver(this);
    }

    public void windowFocusLost()
    {
        player.resetDirectionBooleans();
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void update()
    {
        levelManager.update();
        player.update();
        enemyManager.update(levelManager.getLevel().getLvlData(), player);
        checkCloseToBorder();
    }
    // moves player if approaches border
    private void checkCloseToBorder()
    {
        int playerX = (int) player.getHitbox().x;
        int difference = playerX - xLvlOffset;

        if (difference > rightBorder)
        {
            xLvlOffset += difference - rightBorder;
        }
        else if (difference < leftBorder)
        {
            xLvlOffset += difference - leftBorder;
        }
        // makes sure lvlOffset does not get too high or too low
        if (xLvlOffset > maxLvlOffsetX)
        {
            xLvlOffset = maxLvlOffsetX;
        }
        else if (xLvlOffset < 0)
        {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        if (gameOver)
        {
            gameOverOverlay.draw(g);
        }
    }

    public void resetAll()
    {

    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox)
    {
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (!gameOver)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                player.setAttack(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
        }
    }
}
